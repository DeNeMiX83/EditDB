package com.editdb.db.models;

import com.editdb.Resources;
import com.editdb.db.DataBaseHahdler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;

public class QuotesTeacher {
    private int id;
    private String quote;
    private String teacher;
    private String subject;
    private String date;
    private int author_id;

    public QuotesTeacher(int id, String quote, String teacher, String subject, String date, int author_id) {
        this.id = id;
        this.quote = quote;
        this.teacher = teacher;
        this.subject = subject;
        this.date = date;
        this.author_id = author_id;
    }

    public void delete() {
        String insert = "DELETE FROM quotes_teacher " +
                "WHERE id = ?";

        try {
            PreparedStatement prSt = DataBaseHahdler.getDbConnection().prepareStatement(insert);
            prSt.setInt(1, id);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void update(HashMap<String, String> values) throws SQLIntegrityConstraintViolationException {
        String valuesStr = "";
        for (String key : values.keySet()) {
            valuesStr += key + " = ?,";
        }
        valuesStr = valuesStr.substring(0, valuesStr.length() - 1);
        String insert = "UPDATE quotes_teacher SET " +
                valuesStr + " WHERE id = ?";

        try {
            PreparedStatement prSt = DataBaseHahdler.getDbConnection().prepareStatement(insert);
            int n = 1;
            for (String key : values.keySet()) {
                prSt.setString(n, values.get(key));
                n++;
            }
            prSt.setInt(n, id);
            prSt.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw e;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        String quote = values.get("quote");
        String teacher = values.get("teacher");
        String subject = values.get("subject");
        String date = values.get("date");

        if (quote != null) this.setQuote(quote);
        if (teacher != null) this.setTeacher(teacher);
        if (subject != null) this.setSubject(subject);
        if (date != null) this.setDate(date);
    }

    public static QuotesTeacher create(String quotes, String teacher, String subject, String date) {
        String insert = "INSERT INTO quotes_teacher(quote, teacher, subject, date, author_id) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement prSt = DataBaseHahdler.getDbConnection().prepareStatement(insert);
            prSt.setString(1, quotes);
            prSt.setString(2, teacher);
            prSt.setString(3, subject);
            prSt.setString(4, date);
            prSt.setObject(5, Resources.user.getId());
            prSt.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            return null;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return get(quotes, teacher);
    }

    public static QuotesTeacher get(String quote, String teacher) {
        ResultSet resSet = null;

        String select = "SELECT * FROM quotes_teacher WHERE quote = ? and teacher = ?";

        try {
            PreparedStatement prSt = DataBaseHahdler.getDbConnection().prepareStatement(select);
            prSt.setString(1, quote);
            prSt.setString(2, teacher);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            assert resSet != null;
            resSet.next();
            return getCurrentFromResSet(resSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static QuotesTeacher getCurrentFromResSet(ResultSet resSet) {
        try {
            int id = resSet.getInt("id");
            String quote = resSet.getString("quote");
            String teacher = resSet.getString("teacher");
            String subject = resSet.getString("subject");
            String date = resSet.getString("date");
            int author_id = resSet.getInt("author_id");
            return new QuotesTeacher(id, quote, teacher, subject, date, author_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<QuotesTeacher> getAll() {
        ResultSet resSet = null;

        String select = "SELECT * FROM quotes_teacher";

        try {
            PreparedStatement prSt = DataBaseHahdler.getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<QuotesTeacher> arr = new ArrayList<>();
        try {
            while (resSet.next()) {
                arr.add(getCurrentFromResSet(resSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return arr;
    }

    public int getId() {
        return id;
    }

    public String getQuote() {
        return quote;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getSubject() {
        return subject;
    }

    public String getDate() {
        return date;
    }

    public int getAuthorId() {
        return author_id;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAuthor(int author_id) {
        this.author_id = author_id;
    }

}
