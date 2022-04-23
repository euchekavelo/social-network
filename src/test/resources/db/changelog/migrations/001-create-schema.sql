create type permission_type as enum ('ALL', 'FRIENDS');

create table person (
    id serial,
    first_name varchar(50),
    last_name varchar(50),
    reg_date bigint,
    birth_date bigint,
    e_mail varchar(50),
    phone varchar(15),
    password varchar(512),
    photo varchar(255),
    about text,
    city varchar(100),
    country varchar(100),
    confirmation_code varchar(20),
    is_approved boolean,
    messages_permission permission_type,
    last_online_time bigint,
    is_blocked boolean,
    primary key (id)
);

create type action_type as enum ('BLOCK', 'UNBLOCK');

create table block_history (
    id serial,
    time bigint,
    person_id int4,
    post_id int4,
    comment_id int4,
    action action_type,
    primary key (id)
);

create type code_type as enum ('REQUEST', 'FRIEND', 'BLOCKED', 'DECLINED', 'SUBSCRIBED');

create table friendship_status (
    id serial,
    time bigint,
    name varchar(50),
    code code_type,
    primary key (id)
);

create table friendship (
    id serial,
    time bigint,
    code code_type,
	status_id int4,
    src_person_id int4,
    dst_person_id int4,
    CONSTRAINT fk_friendShipStatus FOREIGN KEY (status_id) REFERENCES friendship_status (id) ON DELETE CASCADE,
    CONSTRAINT fk_personSrc FOREIGN KEY (src_person_id) REFERENCES person (id) ON DELETE CASCADE,
    CONSTRAINT fk_personDst FOREIGN KEY (dst_person_id) REFERENCES person (id) ON DELETE CASCADE,
    primary key (id)
);

create type read_status_type as enum ('SENT', 'READ');

create table message (
    id serial,
    time bigint,
    author_id int4,
    recipient_id int4,
    dialog_id int4,
    message_text text,
    read_status read_status_type,
    primary key (id)
);

CREATE TABLE dialog (
    id serial,
    primary key (id)
);

create table post (
    id serial,
    time bigint,
    author int4,
    title varchar(100),
    post_text text,
    is_blocked boolean,
    likes int4,
    primary key (id)
);

create table tag (
    id serial,
    tag varchar(15),
    primary key (id),
    CONSTRAINT tag_unique UNIQUE (tag)
);

create table post2tag (
    id serial,
    post_id int4,
    tag_id int4,
    primary key (id)
);

create table post_like (
    id serial,
    time bigint,
    person_id int4,
    post_id int4,
    primary key (id)
);

create table post_file (
    id serial,
    post_id int4,
    name varchar(255),
    path varchar(255),
    primary key (id)
);

create table post_comment (
    id serial,
    time bigint,
    post_id int4,
    parent_id int4,
    author_id int4,
    comment_text text,
    is_blocked boolean,
    likes int4,
    primary key (id)
);

create table comment_like (
    id serial,
    time bigint,
    person_id int4,
    comment_id int4,
    primary key (id)
);

create table notification (
    id serial,
    type_id int4,
    sent_time bigint,
    person_id int4,
    entity_id int4,
    contact varchar(50),
    primary key (id)
);

create type notification_code_type as enum ('POST', 'POST_COMMENT', 'COMMENT_COMMENT', 'FRIEND_REQUEST', 'MESSAGE');

create table notification_type (
    id serial,
    code notification_code_type,
    name varchar(50),
    primary key (id)
);

create table temptoken (
    id serial,
    email varchar(255),
    token varchar(255),
    primary key (id)
);