CREATE TABLE IF NOT EXISTS `payment` (
    `payment_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `payment_method` VARCHAR(50) NOT NULL,
    `status` ENUM('Paid', 'Unpaid') NULL,
    `updated_at` TIMESTAMP NULL,
    `created_by` VARCHAR(50) NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_by` VARCHAR(50) DEFAULT NULL
);