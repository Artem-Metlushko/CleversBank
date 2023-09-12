package com.clever.bank.mapper;

import com.clever.bank.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MapperUser {
    public static User getUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getString(1));
        user.setFirstName(rs.getString(2));
        user.setLastName(rs.getString(3));
        user.setSurName(rs.getString(4));
        user.setAddress(rs.getString(5));
        user.setPhoneNumber(rs.getString(6));
        return user;
    }

}
