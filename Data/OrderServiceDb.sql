CREATE TABLE IF NOT EXISTS `orders` (
    `order_id` BIGINT AUTO_INCREMENT  PRIMARY KEY,
   `first_name` VARCHAR(100) NOT NULL,
    `last_name` VARCHAR(100) NOT NULL,
    `country` VARCHAR(100) NOT NULL,
    `city` VARCHAR(100) NOT NULL,
    `address` VARCHAR(500) NOT NULL,
    `optional` VARCHAR(200) NULL,
    `email` VARCHAR(100) NOT NULL,
    `phone` VARCHAR(100) NOT NULL,
    `note` TEXT NULL,
     `code` VARCHAR(100) NOT NULL,
    `total_price` DOUBLE NOT NULL,
   `user_id` BIGINT NOT NULL,
    `delivery_id` BIGINT NOT NULL,
    `billing_address_id` BIGINT NOT NULL,
    `status` ENUM('Success', 'Pending', 'Confirmed', 'Delivering', 'Cancel'),
    `updated_at` TIMESTAMP NULL,
    `created_by` VARCHAR(50) NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_by` VARCHAR(50) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `order_details` (
    `order_detail_id` BIGINT AUTO_INCREMENT  PRIMARY KEY,
    `quantity` INT NOT NULL,
    `price` DOUBLE NOT NULL,
     `discount` DOUBLE NOT NULL,
	`order_id` BIGINT NOT NULL,
   `product_id` BIGINT NOT NULL,
    FOREIGN KEY (`order_id`) REFERENCES orders(order_id),
    `updated_at` TIMESTAMP NULL,
    `created_by` VARCHAR(50) NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_by` VARCHAR(50) DEFAULT NULL
);
