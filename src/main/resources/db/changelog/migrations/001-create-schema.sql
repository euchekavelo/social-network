create sequence hibernate_sequence start 1 increment 1;

create type permission_type as enum ('ALL', 'FRIENDS');

create table person (
    id serial,
    first_name varchar(50),
    last_name varchar(50),
    reg_date timestamp,
    birth_date date,
    e_mail varchar(50),
    phone varchar(15),
    password varchar(512),
    photo varchar(255),
    about text,
    town varchar(255),
    confirmation_code varchar(20),
    is_approved boolean,
    messages_permission permission_type,
    last_online_time timestamp,
    is_blocked boolean,
    primary key (id)
);

create type action_type as enum ('BLOCK', 'UNBLOCK');

create table block_history (
    id serial,
    time timestamp,
    person_id int4,
    post_id int4,
    comment_id int4,
    action action_type,
    primary key (id)
);

create table friendship (
    id serial,
    status_id int4,
    src_person_id int4,
    dst_person_id int4,
    primary key (id)
);

create type code_type as enum ('REQUEST', 'FRIEND', 'BLOCKED', 'DECLINED', 'SUBSCRIBED');

create table friendship_status (
    id serial,
    time timestamp,
    name varchar(50),
    code code_type,
    primary key (id)
);

create type read_status_type as enum ('SENT', 'READ');

create table message (
    id serial,
    time timestamp,
    author_id int4,
    recipient_id int4,
    message_text text,
    read_status read_status_type,
    primary key (id)
);

create table post (
    id serial,
    time timestamp,
    author_id int4,
    title varchar(100),
    post_text text,
    is_blocked boolean,
    primary key (id)
);

create table tag (
    id serial,
    tag varchar(15),
    primary key (id)
);

create table post2tag (
    id serial,
    post_id int4,
    tag_id int4,
    primary key (id)
);

create table post_like (
    id serial,
    time timestamp,
    person_id int4,
    post_id int4,
    primary key (id)
);

create table post_file (
    id serial,
    post_id int4,
    name varchar(50),
    path varchar(50),
    primary key (id)
);

create table post_comment (
    id serial,
    time timestamp,
    post_id int4,
    parent_id int4,
    author_id int4,
    comment_text text,
    is_blocked boolean,
    primary key (id)
);

create table notification (
    id serial,
    type_id int4,
    sent_time timestamp,
    person_id int4,
    entity_id int4,
    contact varchar(15),
    primary key (id)
);

create type notification_code_type as enum ('POST', 'POST_COMMENT', 'COMMENT_COMMENT', 'FRIEND_REQUEST', 'MESSAGE');

create table notification_type (
    id serial,
    code notification_code_type,
    name varchar(50),
    primary key (id)
);