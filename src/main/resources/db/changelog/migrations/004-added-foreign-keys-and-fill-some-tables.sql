ALTER TABLE person
ADD COLUMN city varchar(100),
ADD COLUMN country varchar(100);

ALTER TABLE friendship
ADD CONSTRAINT fk_friendShipStatus FOREIGN KEY (status_id) REFERENCES friendship_status (id) ON DELETE CASCADE,
ADD CONSTRAINT fk_personSrc FOREIGN KEY (src_person_id) REFERENCES person (id) ON DELETE CASCADE,
ADD CONSTRAINT fk_personDst FOREIGN KEY (dst_person_id) REFERENCES person (id) ON DELETE CASCADE;

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
