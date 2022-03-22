insert into person (first_name, last_name, reg_date, birth_date, e_mail, phone, password) values
    ('Тест', 'Тест', '2022-03-13 19:10:25-07', '2022-03-17', 'test@mail.ru', '+7(111)1111111', '123'),
    ('Иван', 'Иванов', '2022-03-17 19:10:25-07', '2000-06-22', 'ivanov@mail.ru', '+7(999)9999999', '123'),
    ('Петр', 'Петров', '2022-03-16 19:10:25-07', '2000-05-22', 'petrov@mail.ru', '+7(999)9999999', '123'),
    ('Тихон', 'Тихонов', '2022-03-15 19:10:25-07', '2000-04-22', 'tihonov@mail.ru', '+7(999)9999999', '123'),
    ('Илья', 'Ильин', '2022-03-14 19:10:25-07', '2000-03-22', 'ilin@mail.ru', '+7(999)9999999', '123'),
    ('Онуфрий', 'Онуфриев', '2022-03-13 19:10:25-07', '2000-02-22', 'onufriy@mail.ru', '+7(999)9999999', '123');

insert into post (time, author_id, title, post_text) values
    ('2022-03-18 19:10:25-07', 1, 'test title example', 'test post text example'),
    ('2022-03-18 19:11:25-07', 2, 'ivan title example', 'ivan post text example'),
    ('2022-03-18 19:12:25-07', 3, 'petr title example', 'petr post text example'),
    ('2022-03-18 19:13:25-07', 4, 'tihon title example', 'tihon post text example'),
    ('2022-03-18 19:14:25-07', 5, 'ilya title example', 'ilya post text example'),
    ('2022-03-18 19:15:25-07', 6, 'onufriy title example', 'onufriy post text example'),
    ('2022-03-18 19:16:25-07', 1, 'test title example 2', 'test post text example 2'),
    ('2022-03-18 19:17:25-07', 2, 'ivan title example 2', 'ivan post text example 2'),
    ('2022-03-18 19:18:25-07', 3, 'petr title example 2', 'petr post text example 2'),
    ('2022-03-18 19:19:25-07', 4, 'tihon title example 2', 'tihon post text example 2');

insert into tag (tag) values ('top15'), ('java'), ('code'), ('true_story'), ('spring'), ('core'), ('bootstrap');

insert into message (time, author_id, recipient_id, message_text) values
    ('2022-03-18 19:10:25-07', 1, 2, 'test to ivan message example'),
    ('2022-03-18 19:10:25-07', 1, 3, 'test to petr message example'),
    ('2022-03-18 19:10:25-07', 1, 4, 'test to tihon message example'),
    ('2022-03-18 19:10:25-07', 1, 5, 'test to ilya message example'),
    ('2022-03-18 19:10:25-07', 1, 6, 'test to onufriy message example'),
    ('2022-03-18 19:10:25-07', 1, 1, 'test to test message example'),
    ('2022-03-18 19:10:25-07', 2, 1, 'ivan to test message example 2'),
    ('2022-03-18 19:10:25-07', 2, 3, 'ivan to petr message example 2'),
    ('2022-03-18 19:10:25-07', 2, 4, 'ivan to tihon message example 2');

/*Fill table 'friendship_status'*/
insert into friendship_status (id, time, name, code) values (1, '2021-07-15', '1', 'FRIEND');
insert into friendship_status (id, time, name, code) values (2, '2021-06-08', '2', 'FRIEND');
insert into friendship_status (id, time, name, code) values (3, '2021-07-30', '3', 'FRIEND');
insert into friendship_status (id, time, name, code) values (4, '2022-02-03', '4', 'BLOCKED');
insert into friendship_status (id, time, name, code) values (5, '2021-12-15', '5', 'FRIEND');
insert into friendship_status (id, time, name, code) values (6, '2022-03-16', '6', 'BLOCKED');
insert into friendship_status (id, time, name, code) values (7, '2022-01-24', '7', 'FRIEND');
insert into friendship_status (id, time, name, code) values (8, '2021-09-25', '8', 'FRIEND');
insert into friendship_status (id, time, name, code) values (9, '2021-07-20', '9', 'FRIEND');
insert into friendship_status (id, time, name, code) values (10, '2022-01-05', '10', 'BLOCKED');

/*Fill table 'friendship'*/
insert into friendship (id, status_id, src_person_id, dst_person_id) values (1, 1, 2, 4);
insert into friendship (id, status_id, src_person_id, dst_person_id) values (2, 2, 3, 2);
insert into friendship (id, status_id, src_person_id, dst_person_id) values (3, 3, 5, 1);
insert into friendship (id, status_id, src_person_id, dst_person_id) values (4, 4, 2, 5);
insert into friendship (id, status_id, src_person_id, dst_person_id) values (5, 5, 2, 3);
insert into friendship (id, status_id, src_person_id, dst_person_id) values (6, 6, 5, 4);
insert into friendship (id, status_id, src_person_id, dst_person_id) values (7, 7, 4, 6);
insert into friendship (id, status_id, src_person_id, dst_person_id) values (8, 8, 5, 6);
insert into friendship (id, status_id, src_person_id, dst_person_id) values (9, 9, 6, 4);
insert into friendship (id, status_id, src_person_id, dst_person_id) values (10, 10, 2, 3);

