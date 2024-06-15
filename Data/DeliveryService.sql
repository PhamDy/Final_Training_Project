CREATE TABLE IF NOT EXISTS `delivery` (
    `delivery_id` BIGINT AUTO_INCREMENT  PRIMARY KEY,
    `name` VARCHAR(50) NOT NULL,
    `price` DOUBLE NOT NULL,
    `img` VARCHAR(50) NOT NULL,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_by` VARCHAR(50) NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_by` VARCHAR(50) DEFAULT NULL
);

INSERT INTO `delivery` (`name`,`price`, `img`, `created_by`)
VALUES ('Standard', 2.01 ,'https://i.postimg.cc/cJB3dch0/1.png' ,'Admin'),
		('Priority', 4.03 ,'https://i.postimg.cc/brsSHfhw/2.png' ,'Admin'),
		('Overnight', 6.05 ,'https://i.postimg.cc/fTS0VsCH/3.png' ,'Admin');