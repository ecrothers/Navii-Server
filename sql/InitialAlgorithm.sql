use naviDB;


-- itinerariestags, userid
Select itinerariestags.itineraryid, itinerariestags.tag, itineraries.totalcost, itineraries.startdate FROM itinerariestags 
	JOIN ( SELECT tag FROM itinerariestags WHERE tag IN ('church', 'Cheat')) AS filterTag ON itinerariestags.tag=filterTag.tag 
	JOIN ( SELECT itinerariespreferences.itineraryid FROM itinerariespreferences 
			JOIN (SELECT userspreferences.preference FROM userspreferences WHERE userspreferences.userid = 'naviCreator') AS uPrefFilter 
					ON itinerariespreferences.preference=uPrefFilter.preference) 
			AS filterPreference ON itinerariestags.itineraryid=filterPreference.itineraryid
	JOIN itineraries ON itinerariestags.itineraryid=itineraries.itineraryid ;