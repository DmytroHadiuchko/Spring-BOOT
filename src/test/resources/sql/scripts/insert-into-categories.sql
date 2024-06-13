DELETE FROM categories;

INSERT INTO categories (id, name, description, is_deleted)
VALUES
    (1, 'Fiction', 'A category for fictional books.', FALSE),
    (2, 'Non-Fiction', 'A category for non-fictional books.', FALSE),
    (3, 'Science Fiction', 'A category for science fiction books.', FALSE),
    (4, 'Fantasy', 'A category for fantasy books.', FALSE),
    (5, 'Biography', 'A category for biographies.', FALSE);
