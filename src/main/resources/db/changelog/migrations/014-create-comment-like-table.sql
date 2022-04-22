ALTER table post_comment
ADD COLUMN likes INT;

alter table tag
ADD CONSTRAINT tag_unique UNIQUE (tag);

create table comment_like (
    id serial,
    time timestamp,
    person_id int4,
    comment_id int4,
    primary key (id)
);