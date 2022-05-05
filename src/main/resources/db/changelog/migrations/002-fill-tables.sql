insert into person (first_name, last_name, reg_date, birth_date, e_mail, phone, password, photo) values
    ('Тест', 'Тест', '1649367846500', '1649367846000', 'test@mail.ru', '+7(111)1111111', '$2a$10$2bACTJ0LEKT8E36hgAu.x./IrdIe7B5S08K5vwQ2FHmZ/DoEJ1RLS', 'https://www.dropbox.com/s/ekczqxzi1jw8b0y/default.jpg?raw=1'),
    ('Иван', 'Иванов', '1649367846500', '1649367846000', 'i@mail.ru', '+7(999)9999999', '$2a$10$2bACTJ0LEKT8E36hgAu.x./IrdIe7B5S08K5vwQ2FHmZ/DoEJ1RLS', 'https://www.dropbox.com/s/ekczqxzi1jw8b0y/default.jpg?raw=1'),
    ('Петр', 'Петров', '1649367846500', '1649367846000', 'p@mail.ru', '+7(999)9999999', '$2a$10$2bACTJ0LEKT8E36hgAu.x./IrdIe7B5S08K5vwQ2FHmZ/DoEJ1RLS', 'https://www.dropbox.com/s/ekczqxzi1jw8b0y/default.jpg?raw=1'),
    ('Тихон', 'Тихонов', '1649367846500', '1649367846000', 't@mail.ru', '+7(999)9999999', '$2a$10$2bACTJ0LEKT8E36hgAu.x./IrdIe7B5S08K5vwQ2FHmZ/DoEJ1RLS', 'https://www.dropbox.com/s/ekczqxzi1jw8b0y/default.jpg?raw=1'),
    ('Илья', 'Ильин', '1649367846500', '1649367846000', 'l@mail.ru', '+7(999)9999999', '$2a$10$2bACTJ0LEKT8E36hgAu.x./IrdIe7B5S08K5vwQ2FHmZ/DoEJ1RLS', 'https://www.dropbox.com/s/ekczqxzi1jw8b0y/default.jpg?raw=1'),
    ('Онуфрий', 'Онуфриев', '1649367846500', '1649367846000', 'o@mail.ru', '+7(999)9999999', '$2a$10$2bACTJ0LEKT8E36hgAu.x./IrdIe7B5S08K5vwQ2FHmZ/DoEJ1RLS', 'https://www.dropbox.com/s/ekczqxzi1jw8b0y/default.jpg?raw=1');

insert into post (time, author, title, post_text, is_blocked) values
    ('1649367846501', 1, 'test title example', 'test post text example', 'false'),
    ('1649367846502', 2, 'ivan title example', 'ivan post text example', 'false'),
    ('1649367846503', 3, 'petr title example', 'petr post text example', 'false'),
    ('1649367846504', 4, 'tihon title example', 'tihon post text example', 'false'),
    ('1649367846505', 5, 'ilya title example', 'ilya post text example', 'false'),
    ('1649367846506', 6, 'onufriy title example', 'onufriy post text example', 'false'),
    ('1649367846507', 1, 'test title example 2', 'test post text example 2', 'false'),
    ('1649367846508', 2, 'ivan title example 2', 'ivan post text example 2', 'false'),
    ('1649367846509', 3, 'petr title example 2', 'petr post text example 2', 'false'),
    ('1649367846508', 4, 'tihon title example 2', 'tihon post text example 2', 'false');

insert into tag (tag) values ('top15'), ('java'), ('code'), ('true_story'), ('spring'), ('core'), ('bootstrap');

INSERT INTO message (time, author_id, recipient_id, message_text, read_status, dialog_id) values
    ('1649367846503', 2, 1, 'test', 'SENT', 1),
    ('1649367846503', 2, 1, 'test1', 'SENT', 1),
    ('1649367846505', 1, 2, 'hae', 'SENT', 1),
    ('1649367846503', 3, 1, 'test2', 'SENT', 2),
    ('1649367846503', 4, 1, 'test3', 'SENT', 3),
    ('1649367846503', 5, 1, 'test4', 'READ', 4),
    ('1649367846503', 5, 1, 'test5', 'READ', 4),
    ('1649367846503', 6, 1, 'test6', 'SENT', 5),
    ('1649367846503', 6, 1, 'test7', 'SENT', 5);
