insert into person (id, first_name, last_name, reg_date, birth_date, e_mail, phone, password) values
    (1, 'Тест', 'Тест', '1649367846500', '1649367846000', 'test@mail.ru', '+7(111)1111111', '$2a$10$2bACTJ0LEKT8E36hgAu.x./IrdIe7B5S08K5vwQ2FHmZ/DoEJ1RLS'),
    (2, 'Иван', 'Иванов', '1649367846500', '1649367846000', 'ivanov@mail.ru', '+7(999)9999999', '$2a$10$2bACTJ0LEKT8E36hgAu.x./IrdIe7B5S08K5vwQ2FHmZ/DoEJ1RLS'),
    (3, 'Петр', 'Петров', '1649367846500', '1649367846000', 'petrov@mail.ru', '+7(999)9999999', '$2a$10$2bACTJ0LEKT8E36hgAu.x./IrdIe7B5S08K5vwQ2FHmZ/DoEJ1RLS'),
    (4, 'Тихон', 'Тихонов', '1649367846500', '1649367846000', 'tihonov@mail.ru', '+7(999)9999999', '$2a$10$2bACTJ0LEKT8E36hgAu.x./IrdIe7B5S08K5vwQ2FHmZ/DoEJ1RLS'),
    (5, 'Илья', 'Ильин', '1649367846500', '1649367846000', 'ilin@mail.ru', '+7(999)9999999', '$2a$10$2bACTJ0LEKT8E36hgAu.x./IrdIe7B5S08K5vwQ2FHmZ/DoEJ1RLS'),
    (6, 'Онуфрий', 'Онуфриев', '1649367846500', '1649367846000', 'onufriy@mail.ru', '+7(999)9999999', '$2a$10$2bACTJ0LEKT8E36hgAu.x./IrdIe7B5S08K5vwQ2FHmZ/DoEJ1RLS');

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
    ('1649367846501', 2, 1, 'test', 'SENT', 1),
    ('1649367846502', 2, 1, 'test1', 'SENT', 1),
    ('1649367846503', 3, 1, 'test2', 'SENT', 2),
    ('1649367846504', 4, 1, 'test3', 'SENT', 3),
    ('1649367846505', 5, 1, 'test4', 'READ', 4),
    ('1649367846506', 5, 1, 'test5', 'READ', 4),
    ('1649367846507', 6, 1, 'test6', 'SENT', 5),
    ('1649367846508', 6, 1, 'test7', 'SENT', 5);

INSERT INTO dialog (id) values
    (1), (2), (3), (4), (5);

insert into post_comment (time, post_id, author_id, comment_text, is_blocked) values
    ('1649367846508', 1, 5, 'test title example', false),
    ('1649367846508', 2, 6, 'ivan title example', false),
    ('1649367846508', 3, 1, 'petr title example', false),
    ('1649367846508', 4, 5, 'tihon title example', false),
    ('1649367846508', 5, 2, 'ilya title example', false),
    ('1649367846508', 6, 3, 'onufriy title example', false),
    ('1649367846508', 6, 5, 'test title example 2', false),
    ('1649367846508', 2, 4, 'ivan title example 2', false),
    ('1649367846508', 3, 2, 'petr title example 2', false),
    ('1649367846508', 3, 1, 'tihon title example 2', false);
