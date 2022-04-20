ALTER post_comment
ADD COLUMN likes INT;

create table comment_like (
    id serial,
    time timestamp,
    person_id int4,
    comment_id int4,
    primary key (id)
);