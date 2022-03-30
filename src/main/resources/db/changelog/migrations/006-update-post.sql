ALTER TABLE post
ADD COLUMN likes INT;

ALTER TABLE post
RENAME author_id TO author;

UPDATE post SET is_blocked = 'false';
UPDATE post SET likes = 10;