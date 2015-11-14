package com.navii.server.s3;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by JMtorii on 15-10-13.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface S3DocumentType {
    String value();
}
