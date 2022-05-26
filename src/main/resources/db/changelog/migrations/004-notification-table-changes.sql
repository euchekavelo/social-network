drop table notification;

create table notification (
    id serial,
    notification_type notification_code_type,
    sent_time bigint,
    person_id int4,
    entity_id int4,
    contact varchar(50),
    primary key (id)
);
