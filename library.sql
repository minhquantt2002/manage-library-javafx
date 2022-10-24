CREATE DATABASE `library`;
USE `library`;
CREATE TABLE `book_manager` (
	`id` int(11) AUTO_INCREMENT,
	`student_code` varchar(12) NOT NULL,
	`full_name` varchar(128) NOT NULL,
	`class_name` varchar(16) NOT NULL,
	`phone_number` varchar(12) NOT NULL,
	`borrowed` varchar(128) DEFAULT NULL,
	`returned` varchar(128) DEFAULT NULL,
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

CREATE TABLE `borrowing_book` (
	`id` int(11) auto_increment,
    `book_name` varchar(128) not null,
    `student_id` int(11),
    foreign key (`student_id`)  references book_manager(`id`) ,
    primary key(`id`)
);
    
    
    
	