DELETE FROM message;
INSERT INTO message (time, author_id, recipient_id, message_text, read_status, dialog_id) values
    ('1649367846506', 2, 1, 'test', 'SENT', 1),
    ('1649367846506', 2, 1, 'test1', 'SENT', 1),
    ('1649367846506', 3, 1, 'test2', 'SENT', 2),
    ('1649367846506', 4, 1, 'test3', 'SENT', 3),
    ('1649367846506', 5, 1, 'test4', 'READ', 4),
    ('1649367846506', 5, 1, 'test5', 'READ', 4),
    ('1649367846506', 6, 1, 'test6', 'SENT', 5),
    ('1649367846506', 6, 1, 'test7', 'SENT', 5);

INSERT INTO dialog (id) values
    (1), (2), (3), (4), (5);