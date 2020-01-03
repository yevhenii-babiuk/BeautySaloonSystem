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
            "password = ?, " +
            "phone = ?, " +
            "first_name = ?, " +
            "last_name = ? " +
            "WHERE user_id = ?;";
    static final String DELETE_USER_QUERY = "DELETE FROM beauty_saloon_system.user WHERE user_id = ?;";
    static final String GET_USER_BY_EMAIL_AND_PASSWORD_QUERY = "SELECT * FROM beauty_saloon_system.user WHERE email = ? AND password = ?;";
    static final String GET_USER_ROLE_BY_USER_ID_QUERY = "SELECT role FROM beauty_saloon_system.user WHERE user_id = ?;";

    static final String GET_ALL_FEEDBACK_QUERY = "SELECT * FROM  beauty_saloon_system.user;";
    static final String GET_FEEDBACK_BY_MASTER_QUERY = "SELECT * FROM beauty_saloon_system.feedback AS fb \n" +
            "INNER JOIN beauty_saloon_system.slot AS sl ON fb.slot=sl.slot \n" +
            "INNER JOIN beauty_saloon_system.user AS us ON sl.master=us.user_id \n" +
            "WHERE us.last_name = ?" +
            "LIMIT ? OFFSET ?;";
    static final String GET_FEEDBACK_BY_SLOT_QUERY = "SELECT * FROM beauty_saloon_system.feedback WHERE slot = ?;";
    static final String GET_FEEDBACK_QUERY = "SELECT * FROM beauty_saloon_system.feedback WHERE feedback_id = ?;";
    static final String SAVE_FEEDBACK_QUERY = "INSERT INTO beauty_saloon_system.feedback (slot, text) VALUES (?, ?);";
    static final String UPDATE_FEEDBACK_INFO_QUERY = "UPDATE beauty_saloon_system.feedback SET " +
            "slot = ?," +
            "text = ?" +
            "WHERE feedback_id = ?;";
    static final String  DELETE_FEEDBACK_QUERY = "DELETE FROM beauty_saloon_system.feedback WHERE feedback_id = ?;";

    static final String GET_PROCEDURE_BY_NAME_QUERY = "SELECT * FROM beauty_saloon_system.procedure WHERE name = ?;";
    static final String GET_PROCEDURE_PRICE_BY_NAME_QUERY = "SELECT price FROM beauty_saloon_system.procedure WHERE name = ?;";
    static final String GET_PROCEDURE_DESCRIPTION_BY_NAME_QUERY = "SELECT description FROM beauty_saloon_system.procedure WHERE name = ?;";
    static final String GET_PROCEDURE_QUERY = "SELECT * FROM beauty_saloon_system.procedure WHERE procedure_id = ?;";
    static final String GET_ALL_PROCEDURES_QUERY = "SELECT * FROM beauty_saloon_system.procedure;";
    static final String SAVE_PROCEDURE_QUERY = "INSERT INTO beauty_saloon_system.procedure (name, description, price) VALUES (?, ?, ?);";
    static final String UPDATE_PROCEDURE_INFO_QUERY = "UPDATE beauty_saloon_system.procedure SET " +
            "name = ?," +
            "description = ?" +
            "price = ?" +
            "WHERE procedure_id = ?;";
    static final String  DELETE_PROCEDURE_QUERY = "DELETE FROM beauty_saloon_system.procedure WHERE procedure_id = ?;";

    static final String ALL_SLOTS_COUNT_QUERY_HEAD_PART = "SELECT COUNT(*) FROM beauty_saloon_system.slot";
    static final String ALL_SLOTS_QUERY_HEAD_PART = "SELECT * FROM beauty_saloon_system.slot";
    static final String ALL_SLOTS_QUERY_MASTER_PART = "master = ?";
    static final String ALL_SLOTS_QUERY_STATUS_PART = "status = 'FREE'";
    static final String ALL_SLOTS_QUERY_MIN_DATE_PART = "date >= ?";
    static final String ALL_SLOTS_QUERY_MAX_DATE_PART = "date <= ?";
    static final String ALL_SLOTS_QUERY_MIN_TIME_PART = "start_time >=";
    static final String ALL_SLOTS_QUERY_MAX_TIME_PART = "start_date <=";
    static final String ALL_SLOTS_QUERY_TAIL_PART = "ORDER BY date, start_time ASC LIMIT ? OFFSET ?;";
    static final String ALL_SLOTS_COUNT_QUERY_TAIL_PART = "ORDER BY date, start_time ASC;";

    static final String UPDATE_SLOT_STATUS_QUERY = "UPDATE beauty_saloon_system.slot SET " +
            "status = ? " +
            "WHERE slot_id = ?;";
    static final String GET_SLOT_QUERY = "SELECT * FROM beauty_saloon_system.slot WHERE slot_id = ?;";
    static final String GET_ALL_SLOT_QUERY = "SELECT * FROM beauty_saloon_system.slot;";
    static final String SAVE_SLOT_QUERY = "INSERT INTO beauty_saloon_system.slot (date, start_time, end_date, master, user, status, procedure) \n" +
            "VALUE (?, ?, ?, ?, ?, ?, ?);";
    static final String UPDATE_SLOT_INFO_QUERY = "UPDATE beauty_saloon_system.slot SET \n" +
            "date = ?, \n" +
            "start_time = ?, \n" +
            "end_date = ?, \n" +
            "master = ?, \n" +
            "user = ?, \n" +
            "status = ?, \n" +
            "procedure = ? \n" +
            "WHERE slot_id = ?;";
    static final String DELETE_SLOT_QUERY = "DELETE FROM beauty_saloon_system.slot WHERE slot_id = ?;";

    static final String GET_FULL_INFORMATION_ABOUT_SLOT_QUERY = "SELECT s.date AS slot_date,\n" +
            "s.start_time AS start_time,\n" +
            "s.end_time AS end_time,\n" +
            "u1.email AS master_email,\n" +
            "u1.phone AS master_phone,\n" +
            "u1.first_name AS master_first_name,\n" +
            "u1.last_name AS master_last_name,\n" +
            "u2.email AS user_email,\n" +
            "u2.phone AS user_phone,\n" +
            "u2.first_name AS user_first_name,\n" +
            "u2.last_name AS user_last_name,\n" +
            "pr.name AS procedure_name,\n" +
            "pr.description AS procedure_description,\n" +
            "pr.price AS procedure_price,\n" +
            "f.text AS feedback_text\n" +
            "FROM beauty_saloon_system.slot AS s\n" +
            "INNER JOIN beauty_saloon_system.user AS u1 ON u1.user_id=s.master\n" +
            "LEFT JOIN beauty_saloon_system.user AS u2 ON u2.user_id=s.user\n" +
            "INNER JOIN beauty_saloon_system.procedure AS pr ON s.procedure=pr.procedure_id\n" +
            "LEFT JOIN beauty_saloon_system.feedback AS f  ON s.slot_id=f.slot;";

}
