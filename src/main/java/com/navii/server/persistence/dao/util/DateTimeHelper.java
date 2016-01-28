package com.navii.server.persistence.dao.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Created by ecrothers on 2015-10-08.
 */
public class DateTimeHelper {
    public static Timestamp toDB(LocalDateTime time) {
        if (time == null) {
            return null;
        }
        return Timestamp.from(time.toInstant(ZoneOffset.UTC));
    }

    public static LocalDateTime fromDB(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
