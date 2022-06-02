drop table notification;

create table notification (
    id serial,
    notification_type notification_code_type,
    sent_time bigint,
    person_id int4,
    entity_id int4,
	dist_user_id int4,
    status read_status_type,
    primary key (id)
);