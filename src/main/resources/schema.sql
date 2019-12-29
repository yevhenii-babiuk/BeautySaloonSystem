CREATE DATABASE IF NOT EXISTS `beauty_saloon_system`;

USE `beauty_saloon_system`;

CREATE TABLE IF NOT EXISTS `user` (
	`user_id` bigint NOT NULL AUTO_INCREMENT,
	`email` varchar(255) NOT NULL UNIQUE,
	`password` varchar(100) NOT NULL,
	`phone` varchar(15) NOT NULL UNIQUE,
	`first_name` varchar(30) NOT NULL,
	`last_name` varchar(30) NOT NULL,
	`role` varchar(20) NOT NULL,
	PRIMARY KEY (`user_id`)
);

CREATE TABLE IF NOT EXISTS `feedback`(
`feedback_id` INT NOT NULL AUTO_INCREMENT,
`slot` INT NOT NULL UNIQUE,
`text` LONGTEXT,
PRIMARY KEY (`feedback_id`));

CREATE TABLE IF NOT EXISTS `slot`(
`slot_id` INT NOT NULL AUTO_INCREMENT,
`master` INT NOT NULL,
`user`INT,
`status` VARCHAR(20),
`procedure` INT NOT NULL,
PRIMARY KEY(`slot_id`));

CREATE TABLE IF NOT EXISTS `procedure`(
`procedure_id` INT NOT NULL AUTO_INCREMENT,
`name` VARCHAR(150) NOT NULL,
`description` LONGTEXT,
`price` INT,
PRIMARY KEY (`procedure_id`));