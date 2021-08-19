DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    `user_id`  bigint       NOT NULL AUTO_INCREMENT,
    `username` varchar(64)  NOT NULL UNIQUE,
    `email`    varchar(128) NOT NULL,
    `password` varchar(64)  NOT NULL,
    `role`     varchar(32),
    `karma`    int DEFAULT 0,
    PRIMARY KEY (`user_id`)
);

CREATE TABLE tasks
(
    `task_id`     bigint      NOT NULL AUTO_INCREMENT,
    `name`        varchar(64) NOT NULL,
    `bounty`      int,
    `is_complete` tinyint(1) DEFAULT 0,
    `date`        date DEFAULT CURRENT_DATE,
    `user_id` bigint NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES users (`user_id`) ON DELETE CASCADE,
    PRIMARY KEY (`task_id`)
);