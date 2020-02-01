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
    static final String GET_USERS_BY_ROLE_QUERY = "SELECT * FROM  beauty_saloon_system.user WHERE role = ?;";
    static final String ALL_USERS_QUERY_HEAD_PART = "SELECT DISTINCT * FROM beauty_saloon_system.user ";
    static final String ALL_USERS_COUNT_QUERY_HEAD_PART = "SELECT DISTINCT COUNT(*) FROM beauty_saloon_system.user ";
    static final String ALL_USERS_NAME_AND_SURNAME_PART = "(LOWER(CONCAT(first_name, ' ', last_name)) LIKE LOWER(?) \n" +
            "OR LOWER(CONCAT(last_name, ' ', first_name)) LIKE LOWER(?)) ";
    static final String ALL_USERS_QUERY_EMAIL_PART = "email LIKE ? ";
    static final String ALL_USERS_QUERY_PHONE_PART = "phone LIKE ? ";
    static final String ALL_USERS_QUERY_ROLE_PART = "role LIKE ? ";
    static final String ALL_USERS_PAGINATION_PART = "LIMIT ? OFFSET ?";

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

    static final String ALL_FEEDBACK_COUNT_QUERY_HEAD_PART = "SELECT COUNT(*) FROM `beauty_saloon_system`.`feedback` AS f";
    static final String ALL_FEEDBACK_QUERY_HEAD_PART = "SELECT `feedback_id`, `slot`, `text` FROM `beauty_saloon_system`.`feedback` AS f";
    static final String ALL_FEEDBACK_QUERY_MASTER_PART = "INNER JOIN `beauty_saloon_system`.`slot` AS `sl` ON `f`.`slot`=`sl`.`slot_id` AND `sl`.`master` = ?";
    static final String ALL_FEEDBACK_QUERY_PROCEDURE_PART = "INNER JOIN `beauty_saloon_system`.`slot` AS `sl_pr` ON `f`.`slot`=`sl_pr`.slot_id AND `sl`.`procedure` = ?";
    static final String ALL_FEEDBACK_COUNT_QUERY_TAIL_PART = "INNER JOIN `beauty_saloon_system`.`slot` AS `sl_or` ON `f`.`slot`=`sl_or`.`slot_id` \n" +
            "ORDER BY `sl_or`.`date`, `sl_or`.`start_time` DESC;";
    static final String ALL_FEEDBACK_QUERY_TAIL_PART = "INNER JOIN `beauty_saloon_system`.`slot` AS `sl_or` ON `f`.`slot`=`sl_or`.`slot_id` \n" +
            "ORDER BY `sl_or`.`date`, `sl_or`.`start_time` DESC LIMIT ? OFFSET ?;";

    static final String GET_PROCEDURE_BY_NAME_QUERY = "SELECT * FROM `beauty_saloon_system`.`procedure` \n" +
            "WHERE name_ukr = ?" +
            "OR name_en = ?" +
            "OR name_rus;";
    static final String GET_PROCEDURE_PRICE_BY_NAME_QUERY = "SELECT price FROM `beauty_saloon_system`.`procedure` " +
            "WHERE name_ukr = ?" +
            "OR name_en = ?" +
            "OR name_rus;";
    static final String GET_PROCEDURE_DESCRIPTION_BY_NAME_QUERY = "SELECT description_ukr, description_en, description_rus \n " +
            "FROM `beauty_saloon_system`.`procedure` \n" +
            "WHERE name_ukr = ?" +
            "OR name_en = ?" +
            "OR name_rus;";
    static final String GET_PROCEDURE_QUERY = "SELECT * FROM `beauty_saloon_system`.`procedure` WHERE procedure_id = ?;";
    static final String GET_ALL_PROCEDURES_QUERY = "SELECT * FROM `beauty_saloon_system`.`procedure`;";
    static final String SAVE_PROCEDURE_QUERY = "INSERT INTO `beauty_saloon_system`.`procedure` (name_ukr, description_ukr, name_en, description_en, name_rus, description_rus, price) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?);";
    static final String UPDATE_PROCEDURE_INFO_QUERY = "UPDATE `beauty_saloon_system`.`procedure` SET " +
            "name_ukr = ?, " +
            "description_ukr = ?, " +
            "name_en = ?," +
            "description_en = ?, " +
            "name_rus = ?, " +
            "description_rus = ?, " +
            "price = ? " +
            "WHERE procedure_id = ?;";
    static final String DELETE_PROCEDURE_QUERY = "DELETE FROM `beauty_saloon_system`.`procedure` WHERE procedure_id = ?;";
    static final String COUNT_ALL_PROCEDURE_QUERY = "SELECT COUNT(*) FROM `beauty_saloon_system`.`procedure`;";
    static final String GET_PROCEDURE_PARAMETRIZED_QUERY = "SELECT * FROM `beauty_saloon_system`.`procedure` LIMIT ? OFFSET ?;";

    static final String ALL_SLOTS_COUNT_QUERY_HEAD_PART = "SELECT COUNT(*) FROM `beauty_saloon_system`.`slot` ";
    static final String ALL_SLOTS_QUERY_HEAD_PART = "SELECT * FROM `beauty_saloon_system`.`slot` ";
    static final String ALL_SLOTS_QUERY_FEEDBACK_PART = "INNER JOIN `beauty_saloon_system`.`feedback`  ON `slot_id`=`feedback`.`slot`";
    static final String ALL_SLOTS_QUERY_MASTER_PART = "`master` = ?";
    static final String ALL_SLOTS_QUERY_STATUS_PART = "`status` = ?";
    static final String ALL_SLOTS_QUERY_PROCEDURE_PART = "`procedure` = ?";
    static final String ALL_SLOTS_QUERY_USER_PART = "`user` = ?";
    static final String ALL_SLOTS_QUERY_MIN_DATE_PART = "`date` >= ?";
    static final String ALL_SLOTS_QUERY_MAX_DATE_PART = "`date` <= ?";
    static final String ALL_SLOTS_QUERY_MIN_TIME_PART = "`start_time` >= ?";
    static final String ALL_SLOTS_QUERY_MAX_TIME_PART = "`start_time` <= ?";
    static final String ALL_SLOTS_QUERY_TAIL_PART = "ORDER BY `date` DESC, `start_time` ASC LIMIT ? OFFSET ?;";
    static final String ALL_SLOTS_COUNT_QUERY_TAIL_PART = "ORDER BY `date` DESC, `start_time` ASC;";

    static final String GET_SLOT_BY_USER_ID_QUERY = "SELECT * FROM `beauty_saloon_system`.`slot` WHERE `user` = ?;";
    static final String UPDATE_SLOT_STATUS_QUERY = "UPDATE `beauty_saloon_system`.`slot` SET " +
            "`status` = ?, " +
            "`user` = ? " +
            "WHERE `slot_id` = ?;";
    static final String GET_SLOT_QUERY = "SELECT * FROM `beauty_saloon_system`.`slot` WHERE `slot_id` = ?;";
    static final String GET_ALL_SLOT_QUERY = "SELECT * FROM `beauty_saloon_system`.`slot`;";
    static final String SAVE_SLOT_QUERY = "INSERT INTO beauty_saloon_system.slot (`date`, `start_time`, `end_time`, `master`, `user`, `status`, `procedure`) \n" +
            "VALUE (?, ?, ?, ?, ?, ?, ?);";
    static final String UPDATE_SLOT_INFO_QUERY = "UPDATE beauty_saloon_system.slot SET \n" +
            "`date` = ?, \n" +
            "`start_time` = ?, \n" +
            "`end_time` = ?, \n" +
            "`master` = ?, \n" +
            "`user` = ?, \n" +
            "`status` = ?, \n" +
            "`procedure` = ?, \n" +
            "`feedback_request` = ? \n" +
            "WHERE `slot_id` = ?;";
    static final String DELETE_SLOT_QUERY = "DELETE FROM `beauty_saloon_system`.`slot` WHERE `slot_id` = ?;";
    static final String UPDATE_SLOT_FEEDBACK_REQUEST_STATUS_QUERY = "UPDATE beauty_saloon_system.slot SET feedback_request = ? WHERE slot_id = ?;";
    static final String GET_SLOT_BY_FEEDBACK_REQUEST_STATUS_QUERY = "SELECT * FROM beauty_saloon_system.slot WHERE feedback_request = ?;";

    static final String GET_FULL_INFORMATION_ABOUT_SLOT_QUERY = "SELECT `s`.`slot_id` AS `slot_id`, \n" +
            "`s`.`master` AS `slot_master`,\n" +
            "`s`.`procedure` AS `slot_procedure`,\n" +
            "`s`.`date` AS `slot_date`,\n" +
            "`s`.`start_time` AS `start_time`,\n" +
            "`s`.`end_time` AS `end_time`,\n" +
            "`s`.`status` AS `status`,\n" +
            "`s`.`feedback_request` AS `feedback_request`, \n" +
            "`u1`.`email` AS `master_email`,\n" +
            "`u1`.`phone` AS `master_phone`,\n" +
            "`u1`.`first_name` AS `master_first_name`,\n" +
            "`u1`.`last_name` AS `master_last_name`,\n" +
            "`u2`.`email` AS `user_email`,\n" +
            "`u2`.`phone` AS `user_phone`,\n" +
            "`u2`.`first_name` AS `user_first_name`,\n" +
            "`u2`.`last_name` AS `user_last_name`,\n" +
            "`pr`.`name_ukr` AS `name_ukr`,\n" +
            "`pr`.`description_ukr` AS `description_ukr`,\n" +
            "`pr`.`name_rus` AS `name_rus`,\n" +
            "`pr`.`description_rus` AS `description_rus`,\n" +
            "`pr`.`name_en` AS `name_en`,\n" +
            "`pr`.`description_en` AS `description_en`,\n" +
            "`pr`.`price` AS `procedure_price`,\n" +
            "`f`.`text` AS `feedback_text`\n" +
            "FROM `beauty_saloon_system`.`slot` AS `s`\n" +
            "INNER JOIN `beauty_saloon_system`.`user` AS `u1` ON `u1`.`user_id`=`s`.`master`\n" +
            "LEFT JOIN `beauty_saloon_system`.`user` AS `u2` ON `u2`.`user_id`=`s`.`user`\n" +
            "INNER JOIN `beauty_saloon_system`.`procedure` AS pr ON `s`.`procedure`=`pr`.`procedure_id`\n" +
            "LEFT JOIN `beauty_saloon_system`.`feedback` AS `f`  ON `s`.`slot_id`=`f`.`slot` \n"+
            "WHERE `s`.`slot_id` = ?;";

}
