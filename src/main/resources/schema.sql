CREATE DATABASE IF NOT EXISTS `beauty_saloon_system`;

USE `beauty_saloon_system`;

CREATE TABLE IF NOT EXISTS `beauty_saloon_system`.`user` (
	`user_id` INT NOT NULL AUTO_INCREMENT,
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
`date` DATE NOT NULL,
`start_time` TIME NOT NULL,
`end_time` TIME NOT NULL,
`master` INT NOT NULL,
`user`INT,
`status` VARCHAR(20),
`procedure` INT NOT NULL,
`feedback_request` BOOL NOT NULL DEFAULT FALSE,
PRIMARY KEY(`slot_id`));

CREATE TABLE IF NOT EXISTS `procedure`(
`procedure_id` INT NOT NULL AUTO_INCREMENT,
`name` VARCHAR(150) NOT NULL UNIQUE,
`description` LONGTEXT,
`price` INT NOT NULL,
PRIMARY KEY (`procedure_id`));

ALTER TABLE `beauty_saloon_system`.`slot` 
ADD CONSTRAINT procedure_FK 
FOREIGN KEY (`procedure`) 
REFERENCES `beauty_saloon_system`.`procedure`(`procedure_id`);

ALTER TABLE `beauty_saloon_system`.`slot` 
ADD CONSTRAINT client_FK 
FOREIGN KEY (`user`) 
REFERENCES `beauty_saloon_system`.`user`(`user_id`);

ALTER TABLE `beauty_saloon_system`.`slot` 
ADD CONSTRAINT master_FK 
FOREIGN KEY (`master`) 
REFERENCES `beauty_saloon_system`.`user`(`user_id`);

ALTER TABLE `beauty_saloon_system`.`feedback` 
ADD CONSTRAINT slot_FK 
FOREIGN KEY (`slot`) 
REFERENCES `beauty_saloon_system`.`slot`(`slot_id`);