-- Create the bookstore database
CREATE DATABASE IF NOT EXISTS bookstore2;

-- Switch to the bookstore database
USE bookstore2;

-- Create a table for users
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    confirm_password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS sellers (
    seller_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    confirm_password VARCHAR(255) NOT NULL,
    business_name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL
);

-- Create a table for books
CREATE TABLE IF NOT EXISTS books (
    book_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(100) NOT NULL,
    isbn VARCHAR(20) NOT NULL UNIQUE,
    genre VARCHAR(50),
    pages INT,
    price DECIMAL(10, 2) NOT NULL
);

-- Create a table for orders
CREATE TABLE IF NOT EXISTS orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    book_id INT,
    quantity INT NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (book_id) REFERENCES books(book_id)
);

-- Insert sample data
INSERT INTO users (username, email, password, confirm_password) VALUES
    ('john_doe', 'john.doe@example.com', 'hashed_password', 'hashed_password');

INSERT INTO sellers (username, email, password, confirm_password, business_name, address) VALUES
    ('seller_user', 'seller@example.com', 'hashed_password', 'hashed_password', 'BookStore Inc.', '123 Main Street');

INSERT INTO books (title, author, isbn, genre, pages, price) VALUES
    ('Book Title 1', 'Author 1', '1234567890', 'Fiction', 300, 19.99);

INSERT INTO orders (user_id, book_id, quantity, total_price) VALUES
    (1, 1, 2, 39.98);
    
DELETE FROM books WHERE isbn = ?;

    
SELECT * FROM users; 
SELECT * FROM sellers; 
SELECT * FROM books;
SELECT * FROM orders;