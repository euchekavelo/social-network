ALTER TABLE person
ALTER COLUMN reg_date TYPE bigint USING 0,
ALTER COLUMN birth_date TYPE bigint USING 0,
ALTER COLUMN last_online_time TYPE bigint USING 0;

ALTER TABLE block_history
ALTER COLUMN time TYPE bigint USING 0;

ALTER TABLE friendship
ALTER COLUMN time TYPE bigint USING 0;

ALTER TABLE message
ALTER COLUMN time TYPE bigint USING 0;

ALTER TABLE post
ALTER COLUMN time TYPE bigint USING 0;

ALTER TABLE post_like
ALTER COLUMN time TYPE bigint USING 0;

ALTER TABLE post_comment
ALTER COLUMN time TYPE bigint USING 0;

ALTER TABLE notification
ALTER COLUMN sent_time TYPE bigint USING 0;


UPDATE person SET reg_date = 1649367846506, birth_date = 1649367846506, last_online_time = 1649367846506;

UPDATE post SET time = 1649367846506;

UPDATE message SET time = 1649367846506;

UPDATE post_comment SET time = 1649367846506;