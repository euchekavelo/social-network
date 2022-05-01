DELETE FROM message;
INSERT INTO message (time, author_id, recipient_id, message_text, read_status, dialog_id) values
    ('2022-03-18 19:10:25-07', 2, 1, 'test', 'SENT', 1),
    ('2022-03-18 19:11:25-07', 2, 1, 'test1', 'SENT', 1),
    ('2022-03-18 19:11:25-07', 1, 2, 'hae', 'SENT', 1),
    ('2022-03-18 19:10:20-07', 3, 1, 'test2', 'SENT', 2),
    ('2022-03-18 19:10:25-07', 4, 1, 'test3', 'SENT', 3),
    ('2022-03-18 19:10:10-07', 5, 1, 'test4', 'READ', 4),
    ('2022-03-18 19:10:09-07', 5, 1, 'test5', 'READ', 4),
    ('2022-03-18 19:09:25-07', 6, 1, 'test6', 'SENT', 5),
    ('2022-03-18 19:10:25-07', 6, 1, 'test7', 'SENT', 5);

ALTER TABLE dialog
ADD COLUMN author_id int4,
ADD COLUMN recipient_id int4,
ADD COLUMN dialog_id int4;

INSERT INTO dialog (dialog_id, author_id, recipient_id) values
    (1, 1, 2), (1, 2, 1), (2, 3, 1), (2, 1, 3), (3, 4, 1),
    (3, 1, 4), (4, 5, 1), (4, 1, 5), (5, 6, 1), (5, 1, 6);