CREATE TABLE accounts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    full_name VARCHAR(100),
    phone VARCHAR(15) UNIQUE,
    address VARCHAR(255),
    role ENUM('CUSTOMER', 'STAFF', 'DIRECTOR') NOT NULL
);

CREATE TABLE categories (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    size VARCHAR(50),
    description TEXT,
    image VARCHAR(255),
    price DECIMAL(10,2) NOT NULL,
    publish_date DATE NULL,
    last_update_time DATETIME NULL,
    category_id INT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
);

CREATE TABLE cart (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES accounts(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('PENDING', 'SHIPPED', 'COMPLETED', 'CANCELLED') NOT NULL,
    FOREIGN KEY (user_id) REFERENCES accounts(id) ON DELETE CASCADE
);

CREATE TABLE order_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

INSERT INTO accounts (username, password, email, full_name, phone, address, role) VALUES
    ('admin', '123', 'admin@samsung.com', 'Admin Samsung', '0123456789', 'Hà Nội, Việt Nam', 'ADMIN'),
    ('user1', '1234', 'user1@gmail.com', 'Nguyễn Văn A', '0987654321', 'Hồ Chí Minh, Việt Nam', 'USER'),
    ('user2', '1234', 'user2@gmail.com', 'Trần Thị B', '0971122334', 'Đà Nẵng, Việt Nam', 'USER');

INSERT INTO categories (name) VALUES
    ('Điện thoại Samsung'),
    ('Máy tính bảng Samsung'),
    ('Đồng hồ Samsung'),
    ('Phụ kiện Samsung');

INSERT INTO products (name, quantity, size, description, image, price, publish_date, last_update_time, category_id) VALUES
    ('Samsung Galaxy S24 Ultra', 50, NULL, 'Flagship Samsung 2024', 's24_ultra.jpg', 29990000, NOW(), NOW(), 1),
    ('Samsung Galaxy S23', 100, NULL, 'Điện thoại Samsung cao cấp', 's23.jpg', 19990000, NOW(), NOW(), 1),
    ('Samsung Galaxy Tab S9', 30, '12.4 inch', 'Máy tính bảng flagship Samsung', 'tab_s9.jpg', 24990000, NOW(), NOW(), 2),
    ('Samsung Galaxy Tab A8', 80, '10.5 inch', 'Máy tính bảng giá rẻ', 'tab_a8.jpg', 7990000, NOW(), NOW(), 2),
    ('Samsung Galaxy Watch 6', 60, '44mm', 'Smartwatch cao cấp Samsung', 'watch6.jpg', 8990000, NOW(), NOW(), 3),
    ('Samsung Galaxy Watch 5', 40, '40mm', 'Smartwatch phổ thông', 'watch5.jpg', 5990000, NOW(), NOW(), 3),
    ('Samsung Buds 2 Pro', 120, NULL, 'Tai nghe không dây cao cấp', 'buds2pro.jpg', 4990000, NOW(), NOW(), 4),
    ('Ốp lưng Galaxy S24 Ultra', 200, NULL, 'Ốp lưng bảo vệ điện thoại', 'oplung_s24.jpg', 500000, NOW(), NOW(), 4);

INSERT INTO cart (user_id, product_id, quantity) VALUES
    (2, 1, 1), -- user1 mua Galaxy S24 Ultra
    (2, 6, 2), -- user1 mua Galaxy Watch 6
    (3, 3, 1), -- user2 mua Galaxy Tab S9
    (3, 8, 3); -- user2 mua Ốp lưng S24 Ultra

INSERT INTO orders (user_id, total_price, order_date, status) VALUES
    (2, 38970000, NOW(), 'PENDING'), -- user1 đặt hàng
    (3, 26990000, NOW(), 'SHIPPED'); -- user2 đặt hàng

INSERT INTO order_details (order_id, product_id, quantity, price) VALUES
    (1, 1, 1, 29990000),
    (1, 6, 2, 8990000),
    (2, 3, 1, 24990000),
(2, 8, 3, 500000);

SELECT * FROM accounts;
SELECT * FROM categories;
SELECT * FROM products;
SELECT * FROM cart;
SELECT * FROM orders;
SELECT * FROM order_details;