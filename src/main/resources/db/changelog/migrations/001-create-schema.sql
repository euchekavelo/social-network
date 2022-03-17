create sequence hibernate_sequence start 1 increment 1;

create type permission_type as enum ('ALL', 'FRIENDS');

create table person (
       id int8 not null,
       first_name varchar(255) not null,
       last_name varchar(255) not null,
       reg_date timestamp not null,
       birth_date timestamp,
       e_mail varchar(255) not null,
       phone varchar(255) not null,
       password varchar(512) not null,
       photo varchar(255) not null,
       about varchar(512) not null,
       town varchar(255) not null,
       confirmation_code varchar(255) not null,
       is_approved boolean not null,
       messages_permission permission_type not null,
       last_online_time timestamp,
       is_blocked boolean not null,
       primary key (id)
);

create type action_type as enum ('BLOCK', 'UNBLOCK');

create table block_history (
    id int8 not null,
    time timestamp not null,
    person_id int8 not null,
    post_id int8 not null,
    comment_id int8 not null,
    action action_type not null,
    primary key (id)
);

create type user_type as enum ('MODERATOR', 'ADMIN');

create table usr (
    id int8 not null,
    name varchar(255) not null,
    e_mail varchar(255) not null,
    password varchar(512) not null,
    type user_type not null,
    primary key (id)
);

create table friendship (
    id int8 not null,
    status_id int8 not null,
    src_person_id int8 not null,
    dst_person_id int8 not null,
    primary key (id)
);

create type code_type as enum ('REQUEST', 'FRIEND', 'BLOCKED', 'DECLINED', 'SUBSCRIBED');

create table friendship_status (
    id int8 not null,
    time timestamp not null,
    name varchar(255) not null,
    code code_type not null,
    primary key (id)
);

create type read_status_type as enum ('SENT', 'READ');

create table message (
    id int8 not null,
    time timestamp not null,
    author_id int8 not null,
    recipient_id int8 not null,
    message_text varchar(2048) not null,
    read_status read_status_type not null,
    primary key (id)
);

create table post (
    id int8 not null,
    time timestamp not null,
    author_id int8 not null,
    title varchar(255) not null,
    post_text varchar(4096) not null,
    is_blocked boolean not null,
    primary key (id)
);

create table tag (
    id int8 not null,
    tag varchar(255) not null,
    primary key (id)
);

create table post2tag (
    id int8 not null,
    post_id int8 not null,
    tag_id int8 not null,
    primary key (id)
);

create table post_like (
    id int8 not null,
    time timestamp not null,
    person_id int8 not null,
    post_id int8 not null,
    primary key (id)
);

create table post_file (
    id int8 not null,
    post_id int8 not null,
    name varchar(255) not null,
    path varchar(255) not null,
    primary key (id)
);

create table post_comment (
    id int8 not null,
    time timestamp not null,
    post_id int8 not null,
    parent_id int8,
    author_id int8 not null,
    comment_text varchar(2048) not null,
    is_blocked boolean,
    primary key (id)
);

create table notification (
    id int8 not null,
    type_id int8 not null,
    sent_time timestamp not null,
    person_id int8 not null,
    entity_id int8 not null,
    contact varchar(255) not null,
    primary key (id)
);

create type notification_code_type as enum ('POST', 'POST_COMMENT', 'COMMENT_COMMENT', 'FRIEND_REQUEST', 'MESSAGE');

create table notification_type (
    id int8 not null,
    code notification_code_type not null,
    name varchar(255) not null,
    primary key (id)
);