DROP TABLE borrowed_items;
DROP TABLE items;
DROP TABLE administrators;
DROP TABLE students;
DROP TABLE users;
DROP TABLE role_type;

CREATE TABLE role_type(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    role_type_id INT,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash CHAR(60) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255),
    FOREIGN KEY (role_type_id) REFERENCES role_type(id)
);

CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNIQUE,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE administrators (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNIQUE,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE borrowed_items(
    id INT AUTO_INCREMENT PRIMARY KEY,
    item_id INT,
    student_id INT,
    FOREIGN KEY (item_id) REFERENCES items(id),
    FOREIGN KEY (student_id) REFERENCES students(id)
);

INSERT INTO role_type (name) VALUES 
    ("STUDENT"), 
    ("ADMINISTRATOR");

INSERT INTO items (name) VALUES
    ("A Good Book 1"),
    ("A Good Book 2"),
    ("A Good Book 3"),
    ("A Good Book 4"),
    ("A Good Book 5"),
    ("Lab Equipment 1"),
    ("Lab Equipment 2"),
    ("Lab Equipment 3"),
    ("Lab Equipment 4"),
    ("Lab Equipment 5");

INSERT INTO users (role_type_id, email, password_hash, first_name, last_name) VALUES 
    (2, "email_1@admins.nu-dasma.edu.ph",   "XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=", "FN1", "LN1"),
    (2, "email_2@admins.nu-dasma.edu.ph",   "XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=", "FN2", "LN2"),
    (1, "email_1@students.nu-dasma.edu.ph", "XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=", "FN3", "LN3"),
    (1, "email_2@students.nu-dasma.edu.ph", "XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=", "FN4", "LN4"),
    (1, "email_3@students.nu-dasma.edu.ph", "XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=", "FN5", "LN5"),
    (1, "email_4@students.nu-dasma.edu.ph", "XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=", "FN6", "LN6"),
    (1, "email_5@students.nu-dasma.edu.ph", "XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=", "FN7", "LN7"),
    (1, "email_6@students.nu-dasma.edu.ph", "XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=", "FN8", "LN8"),
    (1, "email_7@students.nu-dasma.edu.ph", "XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=", "FN9", "LN9");

INSERT INTO administrators (user_id) VALUES 
    (1),
    (2);

INSERT INTO students (user_id) VALUES
    (3),
    (4),
    (5),
    (6),
    (7),
    (8),
    (9);

SELECT * FROM role_type;
SELECT * FROM users;
SELECT * FROM students;
SELECT * FROM administrators;
SELECT * FROM items;
SELECT * FROM borrowed_items;
