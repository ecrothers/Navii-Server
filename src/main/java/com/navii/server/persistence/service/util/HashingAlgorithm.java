package com.navii.server.persistence.service.util;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ecrothers on 2016-07-24.
 */
public class HashingAlgorithm {
    public static String sha256(String hexString) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(DatatypeConverter.parseHexBinary(hexString));
        return DatatypeConverter.printHexBinary(md.digest());
    }

    public static String sha256(byte[] rawBytes) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(rawBytes);
        return DatatypeConverter.printHexBinary(md.digest());
    }
}
