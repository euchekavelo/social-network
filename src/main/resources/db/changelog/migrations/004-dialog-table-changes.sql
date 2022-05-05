ALTER TABLE dialog
ADD COLUMN author_id int4,
ADD COLUMN recipient_id int4,
ADD COLUMN dialog_id int4;

INSERT INTO dialog (dialog_id, author_id, recipient_id) values
    (1, 1, 2), (1, 2, 1), (2, 3, 1), (2, 1, 3), (3, 4, 1),
    (3, 1, 4), (4, 5, 1), (4, 1, 5), (5, 6, 1), (5, 1, 6);
