CREATE TABLE IF NOT EXISTS `carts` (
    `cart_id` BIGINT AUTO_INCREMENT  PRIMARY KEY,
   `user_id` BIGINT NOT NULL,
   `status` ENUM('Open', 'Close') DEFAULT 'Open',
    `updated_at` TIMESTAMP NULL,
    `created_by` VARCHAR(50) NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_by` VARCHAR(50) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `cart_item` (
    `cart_item_id` BIGINT AUTO_INCREMENT  PRIMARY KEY,
   `product_id` BIGINT NOT NULL,
   `cart_id` BIGINT NOT NULL,
   `price` DOUBLE NOT NULL,
   `quantity` INT NOT NULL,
   FOREIGN KEY (`cart_id`) REFERENCES carts(cart_id),
    `updated_at` TIMESTAMP NULL,
    `created_by` VARCHAR(50) NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_by` VARCHAR(50) DEFAULT NULL
);