#!/bin/bash

#
# Modified by JMtorii
# 

function usage() {
cat << EOF
usage: $0 options zip-file

Deploys a Docker zip bundle as an Elastic Beanstalk application.

OPTIONS:
   -h      Show this message
   -p      AWS credential profile
   -a      Application name
   -v      Application version
   -e      Application environment
   -s      AWS solution stack

EOF
echo "Message: $@"
exit 1
}

PROFILE="default"
APPNAME=
VERSION=
ENV=
SOLUTION_STACK="64bit Amazon Linux 2015.09 v2.0.7 running Docker 1.9.1"

while getopts “hp:a:v:e:s:” OPTION
do
     case $OPTION in
         h)
             usage
             exit 1
             ;;
         p)
             PROFILE=$OPTARG
             ;;
         a)
             APPNAME=$OPTARG
             ;;
         v)
             VERSION=$OPTARG
             ;;
         e)
             ENV=$OPTARG
             ;;
         s)
             SOLUTION_STACK=$OPTARG
             ;;
         ?)
             usage
             exit
             ;;
     esac
done

shift $(($OPTIND - 1))

FILE=$1
echo "Deploying bundle: $FILE"

if [[ -z $APPNAME ]]; then
     usage "You must provide an application name."
fi

if [[ -z $VERSION ]]; then
     usage "You must provide an application version."
fi

if [[ -z $ENV ]]; then
     usage "You must provide an application environment."
fi

if [[ -z $FILE ]]; then
     usage "You must provide a zip file."
fi

if [ ! -f $FILE ] ; then
	usage "$FILE is not a readable file."
fi

shift

function getEnvDescriptor() {
	aws --profile "$3" elasticbeanstalk describe-environments \
		--application-name "$1" \
		--environment-name "$2" | \
		jq -r '.Environments | .[]'
}

function getEnvironmentStatus() {
	local APPNAME="$1"
	local ENV="$2"
	local PROFILE="$3"
	ENV_DESCRIPTOR=$(getEnvDescriptor "$APPNAME" "$ENV" "$PROFILE")
	ENV_STATUS=$(echo "$ENV_DESCRIPTOR" | jq -r '.Status')
}


function createApplicationIfNotExists() {
	local NAME="$1"
	local PROFILE="$2"
	APP_DESCRIPTOR=$(aws --profile "$PROFILE" elasticbeanstalk describe-applications \
		--application-name "$NAME"  | \
		jq -r '.Applications | .[]')
	if [ -z "$APP_DESCRIPTOR" ] ; then
		echo INFO: creating application "$NAME"
		APP_DESCRIPTOR=$(aws --profile "$PROFILE" elasticbeanstalk create-application \
		--application-name "$NAME" )
	else
		echo INFO: application "$NAME" already exists
	fi
}

function createAppEnvironmentIfNotExists() {
	local NAME="$1"
	local ENVNAME="$2"
	local STACK="$3"
	local PROFILE="$4"
	createApplicationIfNotExists "$NAME" "$PROFILE"
	ENV_DESCRIPTOR=$(getEnvDescriptor "$NAME" "$ENVNAME" "$PROFILE")
	if [ -z "$ENV_DESCRIPTOR" ] ; then
		echo "INFO: Creating environment $ENVNAME for $NAME"
		ENV_DESCRIPTOR=$(aws --profile "$PROFILE" elasticbeanstalk create-environment \
		--application-name "$NAME" \
		--solution-stack "$STACK" \
		--environment-name "$ENVNAME" \
		--cname-prefix "$ENVNAME")
	else
		echo "INFO: environment $ENVNAME for $NAME already exists"
	fi
}
function determineS3BucketAndKey() {
	local FILE="$1"
	local VERSION="$2"
	local PROFILE="$3"
	S3BUCKET=$(aws --profile "$PROFILE" elasticbeanstalk create-storage-location | jq -r '.S3Bucket')
	S3KEY="$VERSION-$(basename $FILE)"
}
function uploadBinaryArtifact() {
	local APPNAME="$1"
	local FILE="$2"
	local VERSION="$3"
	local PROFILE="$4"
	determineS3BucketAndKey "$FILE" "$VERSION" "$PROFILE"
	local EXISTS=$(aws --profile "$PROFILE" s3 ls s3://$S3BUCKET/$S3KEY)
	if [ -z "$EXISTS" ] ; then
		echo "INFO: Uploading $FILE for $APPNAME, version $VERSION."
		aws --profile "$PROFILE" s3 cp $FILE s3://$S3BUCKET/$S3KEY
	else
		echo INFO: Version $VERSION of $FILE already uploaded.
	fi
}
function createApplicationVersionIfNotExists() {
	local APPNAME="$1"
	local VERSION="$2"
	local PROFILE="$3"
	APP_VERSION=$(
		aws --profile "$PROFILE" elasticbeanstalk describe-application-versions \
			--application-name "$APPNAME" \
			--version-label "$APPNAME-$VERSION" | \
		jq -r '.ApplicationVersions | .[]')
	if [ -z "$APP_VERSION" ] ; then
		echo INFO: Creating version $VERSION of application "$APPNAME"
		APP_VERSION=$(aws --profile "$PROFILE" elasticbeanstalk create-application-version \
			--application-name "$APPNAME" \
			--version-label "$APPNAME-$VERSION" \
			--source-bundle S3Bucket=$S3BUCKET,S3Key=$S3KEY \
			--auto-create-application)
	else
		echo INFO: Version $VERSION of "$APPNAME" already exists.
	fi
}
function busyWaitEnvironmentStatus() {
	local APPNAME="$1"
	local ENV="$2"
	local PROFILE="$3"
	local STATUS="${4:-Ready}"
	getEnvironmentStatus "$APPNAME" "$ENV" "$PROFILE"
	while [ "$ENV_STATUS" != "$STATUS" ] ; do 
		echo INFO: "$ENV" in status $ENV_STATUS, waiting to get to $STATUS..
		sleep 10
		getEnvironmentStatus "$APPNAME" "$ENV" "$PROFILE"
	done
}
function updateEnvironment() {
	local APPNAME="$1"
	local ENV="$2"
	local VERSION="$3"
	local PROFILE="$4"
	local ENV_DESCRIPTOR=$(getEnvDescriptor "$APPNAME" "$ENV" "$PROFILE")
	local ENV_VERSION=$(echo "$ENV_DESCRIPTOR" | jq -r '.VersionLabel')
	local ENV_STATUS=$(echo "$ENV_DESCRIPTOR" | jq -r '.Status')
	if [ "$ENV_VERSION" != "$APPNAME-$VERSION" ] ; then
		busyWaitEnvironmentStatus "$APPNAME" "$ENV" "$PROFILE"
		echo "INFO: Updating environment $ENV with version $VERSION of $APPNAME"
		ENV_DESCRIPTOR=$(aws --profile "$PROFILE" elasticbeanstalk update-environment \
			--environment-name "$ENV" \
			--version-label "$APPNAME-$VERSION")
		busyWaitEnvironmentStatus "$APPNAME" "$ENV" "$PROFILE"
		echo INFO: Version $VERSION of "$APPNAME" deployed in environment
		echo INFO: current status is $ENV_STATUS, goto http://$(echo $ENV_DESCRIPTOR | jq -r .CNAME)
	else
		echo INFO: Version $VERSION of "$APPNAME" already deployed in environment
		echo INFO:  current status is $ENV_STATUS, goto http://$(echo $ENV_DESCRIPTOR | jq -r .CNAME)
	fi
}
function checkEnvironment() {
	local APPNAME="$1"
	local ENVNAME="$2"
	local PROFILE="$3"
	local ENV_APP_NAME=$(aws --profile "$PROFILE" elasticbeanstalk describe-environments --environment-name "$ENVNAME" | \
				jq -r ' .Environments | .[] | .ApplicationName')
	echo "$ENV_APP_NAME"
	if [ -n "$ENV_APP_NAME" -a "$APPNAME" != "$ENV_APP_NAME" ] ; then
		echo ERROR: Environment name "$ENVNAME" already taken by application "$ENV_APP_NAME".
		exit 2
	fi
}

echo "Using AWS credential profile: $PROFILE"

checkEnvironment "$APPNAME" "$ENV" "$PROFILE"
createAppEnvironmentIfNotExists "$APPNAME" "$ENV" "$SOLUTION_STACK" "$PROFILE"
uploadBinaryArtifact "$APPNAME" "$FILE" "$VERSION" "$PROFILE"
createApplicationVersionIfNotExists "$APPNAME" "$VERSION" "$PROFILE"
updateEnvironment "$APPNAME" "$ENV" "$VERSION" "$PROFILE"