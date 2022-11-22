CREATE DATABASE `library`;
USE `library`;
CREATE TABLE `borrower`
(
    `student_code` varchar(128)  NOT NULL,
    `full_name`    varchar(128) NOT NULL,
    `class_name`   varchar(128)  NOT NULL,
    `phone_number` varchar(128)  NOT NULL,
    PRIMARY KEY (`student_code`)
);

CREATE TABLE `book`
(
    `code`     varchar(128),
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
    `book_code`        varchar(128),
    `borrower_id` varchar(128),
    `borrowed`     varchar(128) DEFAULT NULL,
    `returned`     varchar(128) DEFAULT NULL,
	FOREIGN KEY (`book_code`) REFERENCES `book` (`code`) on delete cascade,
    FOREIGN KEY (`borrower_id`) REFERENCES `borrower` (`student_code`),
    PRIMARY KEY (`id`)
);

CREATE TABLE `library_manager`
(
    `id`           int(11) AUTO_INCREMENT,
    `student_code` varchar(128)  NOT NULL,
    `full_name`    varchar(128) NOT NULL,
    `class_name`   varchar(128)  NOT NULL,
    `phone_number` varchar(128)  NOT NULL,
    `time_in`      varchar(128) DEFAULT NULL,
    `time_out`     varchar(128) DEFAULT NULL,
    PRIMARY KEY (`id`)
);


CREATE TABLE `accounts`
(
    `username` varchar(128),
    `password` varchar(128) not null,
    primary key (`username`)
);
INSERT INTO `accounts` (`username`, `password`)
VALUES ('admin', 'admin');
    
    
    
	