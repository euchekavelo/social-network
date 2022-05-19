create table deleted_users(
  id serial,
  person_id int4,
  photo varchar(255),
  first_name varchar(50),
  last_name varchar(50),
  expire bigint
);
