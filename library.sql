CREATE DATABASE `library`;
USE `library`;
CREATE TABLE `book_borrower` (
	`id` int(11) AUTO_INCREMENT,
	`student_code` varchar(12) NOT NULL,
	`full_name` varchar(128) NOT NULL,
	`class_name` varchar(16) NOT NULL,
	`phone_number` varchar(12) NOT NULL,
	`borrowed` varchar(128) DEFAULT NULL,
	`returned` varchar(128) DEFAULT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `book` (
	`id` int(11) AUTO_INCREMENT,
    `name` varchar(128) NOT NULL,
    `category` varchar(128) NOT NULL,
    `author` varchar(128) NOT NULL,
    `available` int(11) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `borrowed_book_detail` (
	`id` int(11) AUTO_INCREMENT,
    `book_id` int(11),
    `book_borrower_id` int(11), 
    `quantity` int(11) NOT NULL,
    FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
    FOREIGN KEY (`book_borrower_id`) REFERENCES `book_borrower` (`id`),
    PRIMARY KEY (`id`)
);

CREATE TABLE `library_manager` (
	`id` int(11) AUTO_INCREMENT,
	`student_code` varchar(12) NOT NULL,
	`full_name` varchar(128) NOT NULL,
	`class_name` varchar(16) NOT NULL,
	`phone_number` varchar(12) NOT NULL,
	`time_in` varchar(128) DEFAULT NULL,
	`time_out` varchar(128) DEFAULT NULL,
	PRIMARY KEY (`id`)
);