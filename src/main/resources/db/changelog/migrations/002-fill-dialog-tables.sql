INSERT INTO person (id, first_name, last_name, reg_date, e_mail, password, photo, is_deleted) values
    (1, 'TEST', 'TEST', 1649367846500, 'test@mail.ru', '$2a$10$NY09iv8la3Hk/SWgBsh5x.KnwunvDCqlGYSJUebaP1LdFEv81w0k6', 'https://www.dropbox.com/s/ekczqxzi1jw8b0y/default.jpg?raw=1', false),
    (2, 'TEST2', 'TEST2', 1649367846500, 'test2@mail.ru', '$2a$10$NY09iv8la3Hk/SWgBsh5x.KnwunvDCqlGYSJUebaP1LdFEv81w0k6', 'https://www.dropbox.com/s/ekczqxzi1jw8b0y/default.jpg?raw=1', false);
INSERT INTO dialog (dialog_id, author_id, recipient_id) values (1, 1, 2), (1, 2, 1);
INSERT INTO message (time, author_id, recipient_id, message_text, read_status, dialog_id) values
    ('1649367846503', 1, 2, 'test', 'SENT', 1),
    ('1649367846503', 2, 1, 'test1', 'SENT', 1);
