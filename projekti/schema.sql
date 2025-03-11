CREATE TABLE category (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);


CREATE TABLE book (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    isbn VARCHAR(13) UNIQUE CHECK (isbn ~ '^[0-9]{13}$'),
    publication_year INT CHECK (publication_year >= 100 AND publication_year <= 2026),
    price NUMERIC (10,2) CHECK (price >=0),
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE SET NULL
);


CREATE TABLE usertable (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL CHECK (role IN ('admin', 'user'))
);

INSERT INTO category (name) VALUES
    ('Fiction'),
    ('Non-Fiction'),
    ('Science Fiction'),
    ('Fantasy'),
    ('Mystery'),
    ('Biography'),
    ('History'),
    ('Technology'),
    ('Self-Help'),
    ('Philosophy');


INSERT INTO book (title, author, isbn, publication_year, price, category_id) VALUES
    ('The Great Gatsby', 'F. Scott Fitzgerald', '9780743273565', 1925, 10.99, 1),
    ('Sapiens: A Brief History of Humankind', 'Yuval Noah Harari', '9780062316097', 2011, 19.99, 2),
    ('Dune', 'Frank Herbert', '9780441013593', 1965, 14.99, 3),
    ('Harry Potter and the Sorcerer Stone', 'J.K. Rowling', '9780590353427', 1997, 9.99, 4),
    ('The Da Vinci Code', 'Dan Brown', '9780307474278', 2003, 12.99, 5),
    ('A Brief History of Time', 'Stephen Hawking', '9780553380163', 1988, 15.99, 7),
    ('Atomic Habits', 'James Clear', '9780735211292', 2018, 16.99, 9),
    ('The Republic', 'Plato', '9780140455113', 380, 11.99, 10);




INSERT INTO usertable (username, password, role) VALUES
('testuser', '$2a$10$enjteVvx9SjhElxKW/n3MeOKUAuhkzRt1D87lLGig4BxTRac1zg9S', 'user'),
('adminuser', '$2a$10$R38a/XTsBr7a.ENIZ.CFYuwV.P4oD2nBfuc38MVaC2ZVu1WAoxakq', 'admin');


