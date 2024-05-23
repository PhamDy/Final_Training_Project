CREATE TABLE IF NOT EXISTS `role` (
    `role_id` BIGINT AUTO_INCREMENT  PRIMARY KEY,
    `name` ENUM('ROLE_Admin', 'ROLE_Employee', 'ROLE_Customer'),
    `updated_at` TIMESTAMP NULL,
    `created_by` VARCHAR(50) NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_by` VARCHAR(50) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `users` (
    `user_id` BIGINT AUTO_INCREMENT  PRIMARY KEY,
   `email` VARCHAR(100) NOT NULL UNIQUE,
    `password` VARCHAR(100) NOT NULL,
    `otp` VARCHAR(5) NULL,
    `otp_generated_time` DATETIME,accountserviceDb
     `enabled` TINYINT(1) DEFAULT 0,
    `updated_at` TIMESTAMP NULL,
    `created_by` VARCHAR(50) NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_by` VARCHAR(50) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `user_role` (
    `user_id` BIGINT NOT NULL,
    `role_id` BIGINT NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES users(user_id),
    FOREIGN KEY (`role_id`) REFERENCES role(role_id),
    PRIMARY KEY(`user_id`, `role_id`)
);

INSERT INTO `role` (`name`, `created_by`)
VALUE ('ROLE_Admin', 'Admin'),
('ROLE_Employee', 'Admin'),
('ROLE_Customer', 'Admin');


