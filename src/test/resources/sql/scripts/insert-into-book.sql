DELETE FROM books;

INSERT INTO books (id, title, author, isbn, price, description, cover_image)
VALUES
    (1, 'Book Title 1', 'Author 1', '1234567890123', 19.99, 'Description of Book 1', 'cover1.jpg'),
    (2, 'Book Title 2', 'Author 2', '1234567890124', 29.99, 'Description of Book 2', 'cover2.jpg');

INSERT INTO book_category (book_id, category_id)
SELECT 1, id FROM categories WHERE name = 'Fiction'
UNION ALL
SELECT 2, id FROM categories WHERE name = 'Fiction';
