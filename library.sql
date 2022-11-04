CREATE DATABASE `library`;
USE `library`;
CREATE TABLE `book_borrower`
(
    `id`           int(11) AUTO_INCREMENT,
    `student_code` varchar(12)  NOT NULL,
    `full_name`    varchar(128) NOT NULL,
    `class_name`   varchar(16)  NOT NULL,
    `phone_number` varchar(12)  NOT NULL,
    `borrowed`     varchar(128) DEFAULT NULL,
    `returned`     varchar(128) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `book`
(
    `code`     varchar(12),
    `category` varchar(128) NOT NULL,
    `name`     varchar(128) NOT NULL,
    `author`   varchar(128) NOT NULL,
    `total`    int(11) NOT NULL,
    `remain`   int(11) NOT NULL,
    PRIMARY KEY (`code`)
);

CREATE TABLE `borrowed_book_detail`
(
    `id`               int(11) AUTO_INCREMENT,
    `book_code`        varchar(12),
    `book_borrower_id` int(11),
    `quantity`         int(11) NOT NULL,
    FOREIGN KEY (`book_code`) REFERENCES `book` (`code`),
    FOREIGN KEY (`book_borrower_id`) REFERENCES `book_borrower` (`id`),
    PRIMARY KEY (`id`)
);

CREATE TABLE `library_manager`
(
    `id`           int(11) AUTO_INCREMENT,
    `student_code` varchar(12)  NOT NULL,
    `full_name`    varchar(128) NOT NULL,
    `class_name`   varchar(16)  NOT NULL,
    `phone_number` varchar(12)  NOT NULL,
    `time_in`      varchar(128) DEFAULT NULL,
    `time_out`     varchar(128) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `accounts`
(
    `username` varchar(28),
    `password` varchar(28) not null,
    primary key (`username`)
);
INSERT INTO `accounts` (`username`, `password`)
VALUES ('admin', 'admin');
    
    
    
	