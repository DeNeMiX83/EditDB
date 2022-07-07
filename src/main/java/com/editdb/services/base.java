package com.editdb.services;

import com.editdb.Resources;
import com.editdb.db.models.QuotesTeacher;
import com.editdb.db.models.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

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
        if (user.is_admin()){
            return true;
        }

        Boolean is_subordinate = false;
        for (User people: user.getSubordinates())
            if (people.getId() == quote.getAuthorId()){
                is_subordinate = true;
                break;
            }
        if (is_subordinate){
            return true;
        }

        if (quote.getAuthorId() == user.getId()){
            return true;
        }

        for (Integer user_role: user.getRolesId()){
            for (Integer quote_role: quote.getRolesForAccess()){
                if (user_role.equals(quote_role)){
                    return true;
                }
            }
        }

        return false;
    }
}
