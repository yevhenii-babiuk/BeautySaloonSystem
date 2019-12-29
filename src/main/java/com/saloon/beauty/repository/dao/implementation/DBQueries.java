package com.saloon.beauty.repository.dao.implementation;

/**
 * This class contains queries to a MySql Server
 * which are executed by DAOs.
 */
public class DBQueries {

    static final String GET_USER_QUERY = "SELECT * FROM beauty_saloon_system.user WHERE user_id = ?;";
    static final String GET_ALL_USERS_QUERY = "SELECT * FROM  beauty_saloon_system.user;";
    static final String SAVE_USER_QUERY = "INSERT INTO beauty_saloon_system.user (email, password, phone, first_name, last_name, role)" +
            " VALUES (?, ?, ?, ?, ?, ?);";
    static final String UPDATE_USER_INFO_QUERY = "UPDATE beauty_saloon_system.user SET " +
            "email = ?," +
            "phone = ?, " +
            "first_name = ?, " +
            "last_name = ? " +
            "WHERE user_id = ?;";
    static final String DELETE_USER_QUERY = "DELETE FROM beauty_saloon_system.user WHERE user_id = ?;";
    static final String GET_USER_BY_EMAIL_AND_PASSWORD_QUERY = "SELECT * FROM beauty_saloon_system.user WHERE email = ? AND password = ?;";
    static final String GET_USER_ROLE_BY_USER_ID_QUERY = "SELECT role FROM beauty_saloon_system.user WHERE user_id = ?;";

    static final String GET_FEEDBACK_BY_MASTER_QUERY = "SELECT * FROM beauty_saloon_system.feedback AS fb \n" +
            "INNER JOIN beauty_saloon_system.slot AS sl ON fb.slot=sl.slot \n" +
            "INNER JOIN beauty_saloon_system.user AS us ON sl.master=us.user_id \n" +
            "WHERE us.user_id = ?;";
}
