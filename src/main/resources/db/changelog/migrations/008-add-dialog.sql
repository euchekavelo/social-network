CREATE TABLE dialog (
    id serial,
    primary key (id)
);
ALTER TABLE message
ADD COLUMN dialog_id int4