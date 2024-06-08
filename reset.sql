DROP TABLE borrowed_items;
DROP TABLE items;
DROP TABLE documents;
DROP TABLE administrators;
DROP TABLE students;
DROP TABLE users;
DROP TABLE status_type;
DROP TABLE document_type;
DROP TABLE role_type;

CREATE TABLE role_type(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
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
    paid_amount INT,
    tuition_fee INT,
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

CREATE TABLE borrowed_items (
    item_id INT,
    student_id INT,
    due DATE,
    FOREIGN KEY (item_id) REFERENCES items(id),
    FOREIGN KEY (student_id) REFERENCES students(id),
    UNIQUE (item_id, student_id)
);

CREATE TABLE document_type (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE status_type (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE documents (
    student_id INT,
    document_type_id INT,
    document_path VARCHAR(255),
    status_type_id INT,
    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN KEY (document_type_id) REFERENCES document_type(id),
    FOREIGN KEY (status_type_id) REFERENCES status_type(id),
    UNIQUE (student_id, document_type_id)
);

CREATE TABLE document_type (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE status_type (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE documents (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    document_type_id INT,
    document_path VARCHAR(255),
    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN KEY (document_type_id) REFERENCES document_type(id)
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
    (2, "daniela@admins.nu-dasma.edu.ph",   "XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=", "Daniela Jade", "Malgapo"),
    (2, "email_1@admins.nu-dasma.edu.ph",   "XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=", "FN2", "LN2"),
    (1, "aivan@students.nu-dasma.edu.ph",   "XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=", "Aivan Ross", "Anuyo"),
    (1, "email_1@students.nu-dasma.edu.ph", "XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=", "FN3", "LN3"),
    (1, "email_2@students.nu-dasma.edu.ph", "XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=", "FN4", "LN4"),
    (1, "email_3@students.nu-dasma.edu.ph", "XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=", "FN5", "LN5"),
    (1, "email_4@students.nu-dasma.edu.ph", "XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=", "FN6", "LN6"),
    (1, "email_5@students.nu-dasma.edu.ph", "XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=", "FN7", "LN7"),
    (1, "email_6@students.nu-dasma.edu.ph", "XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=", "FN8", "LN8");

INSERT INTO administrators (user_id) VALUES 
    (1),
    (2);

INSERT INTO students (user_id, paid_amount, tuition_fee) VALUES
    (3, 0, 30000),
    (4, 0, 30000),
    (5, 0, 30000),
    (6, 0, 30000),
    (7, 0, 30000),
    (8, 0, 30000),
    (9, 0, 30000);


INSERT INTO status_type (name) VALUES
    ("PENDING"),
    ("APPROVED"),
    ("REJECTED");

INSERT INTO document_type (name) VALUES
    ("CERTIFICATE_OF_GOOD_MORAL"),
    ("GRADE_12_REPORT_CARD"),
    ("BIRTH_CERTIFICATE"),
    ("ID_PICTURE"),
    ("FORM_137");

SELECT * FROM role_type;
SELECT * FROM document_type;
SELECT * FROM status_type;
SELECT * FROM users;
SELECT * FROM students;
SELECT * FROM administrators;
SELECT * FROM items;
SELECT * FROM borrowed_items;
SELECT * FROM documents;
