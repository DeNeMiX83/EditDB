package com.editdb.services;

import com.editdb.Resources;
import com.editdb.db.models.QuotesTeacher;
import com.editdb.db.models.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class base {
    public static String hashPass(String pass) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert md5 != null;
        byte[] bytes = md5.digest(pass.getBytes());
        StringBuilder hashPass = new StringBuilder();
        for (byte b : bytes) {
            hashPass.append(String.format("%02X", b));
        }
        return hashPass.toString();
    }

    public static Boolean checkAccessForQuote(QuotesTeacher quote) {
        User user = Resources.user;

        Boolean is_subordinate = false;
        for (User people: user.getSubordinates())
            if (people.getId() == quote.getAuthorId()){
                is_subordinate = true;
                break;
            }

        return !(quote == null || (
                quote.getAuthorId() != user.getId() && !(
                        user.is_admin() ||
                        is_subordinate)
                )
        );
    }
}
