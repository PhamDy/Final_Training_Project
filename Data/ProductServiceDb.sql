CREATE TABLE IF NOT EXISTS `category` (
    `category_id` BIGINT AUTO_INCREMENT  PRIMARY KEY,
    `name` VARCHAR(100) NOT NULL UNIQUE,
    `updated_at` TIMESTAMP NULL,
    `created_by` VARCHAR(50) NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_by` VARCHAR(50) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `products` (
    `product_id` BIGINT AUTO_INCREMENT  PRIMARY KEY,
    `category_id` BIGINT NOT NULL,
    `product_name` VARCHAR(100) NOT NULL,
    `avatar` VARCHAR(100) NOT NULL,
    `img1` VARCHAR(100) NULL,
    `img2` VARCHAR(100) NULL,
    `img3` VARCHAR(100) NULL,
    `desc` TEXT NOT NULL,
    `price` DOUBLE NOT NULL,
    `status` ENUM('InStock', 'OutOfStock'),
    `discount` FLOAT NULL,
    `quantity` BIGINT NOT NULL,
    FOREIGN KEY (`category_id`) REFERENCES category(category_id),
    `updated_at` TIMESTAMP NULL,
    `created_by` VARCHAR(50) NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_by` VARCHAR(50) DEFAULT NULL
);

INSERT INTO `category` (`name`, `created_by`)
VALUE ('table', 'Admin'),
('chair', 'Admin'),
('decorations', 'Admin');

INSERT INTO `products` (`product_name`, category_id, `avatar`, `img1`,`img2`,`img3`,
								`desc`,`price`,`status`,`discount`, `quantity`,`created_by`)
								
VALUE ('Benson Chair', 2, 'https://wpbingosite.com/wordpress/doweco/wp-content/uploads/2018/10/360-1.jpg', 'https://wpbingosite.com/wordpress/doweco/wp-content/uploads/2018/10/360-2.jpg','https://wpbingosite.com/wordpress/doweco/wp-content/uploads/2018/10/360-3.jpg', 
'https://wpbingosite.com/wordpress/doweco/wp-content/uploads/2018/10/360-4.jpg', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.',
250.00, 'InStock', 23, 50, 'Admin'),

('Anodized Aluminium Table', 1, 'https://wpbingosite.com/wordpress/doweco/wp-content/uploads/2020/12/product-75.jpg', 'https://wpbingosite.com/wordpress/doweco/wp-content/uploads/2020/12/product-76.jpg', 'https://wpbingosite.com/wordpress/doweco/wp-content/uploads/2020/12/product-77.jpg', 
'https://wpbingosite.com/wordpress/doweco/wp-content/uploads/2020/12/product-78.jpg', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.',
25.00, 'InStock', 23, 50, 'Admin'),

('Design Frame – Eclisse', 3, 'https://wpbingosite.com/wordpress/doweco/wp-content/uploads/2020/12/product-17.jpg', 'https://wpbingosite.com/wordpress/doweco/wp-content/uploads/2020/12/product-18.jpg', 'https://wpbingosite.com/wordpress/doweco/wp-content/uploads/2020/12/product-19.jpg', 
'https://wpbingosite.com/wordpress/doweco/wp-content/uploads/2020/12/product-20.jpg', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.',
150.00, 'InStock', 0, 50, 'Admin');


SELECT p.product_id, p.product_name, p.category_id, p.avatar, p.price, p.status, p.discount FROM products p;


SELECT p.product_id, p.product_name, p.category_id, 
       p.avatar, p.price, p.status, p.discount, p.quantity 
FROM products p 
LIMIT 10 OFFSET 0;


