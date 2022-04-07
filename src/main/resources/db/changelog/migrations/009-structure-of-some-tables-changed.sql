ALTER TABLE friendship
ADD COLUMN IF NOT EXISTS time timestamp,
ADD COLUMN IF NOT EXISTS code code_type,
DROP COLUMN IF EXISTS status_id;

DROP TABLE IF EXISTS friendship_status;

/*Data update*/
UPDATE friendship SET time = '2021-07-15', code = 'FRIEND' WHERE id = 1;
UPDATE friendship SET time = '2021-06-08', code = 'FRIEND' WHERE id = 2;
UPDATE friendship SET time = '2021-07-30', code = 'FRIEND' WHERE id = 3;
UPDATE friendship SET time = '2022-02-03', code = 'BLOCKED' WHERE id = 4;
UPDATE friendship SET time = '2021-12-15', code = 'FRIEND' WHERE id = 5;
UPDATE friendship SET time = '2022-03-16', code = 'BLOCKED' WHERE id = 6;
UPDATE friendship SET time = '2022-01-24', code = 'FRIEND' WHERE id = 7;
UPDATE friendship SET time = '2021-09-25', code = 'FRIEND' WHERE id = 8;
UPDATE friendship SET time = '2021-07-20', code = 'FRIEND' WHERE id = 9;
UPDATE friendship SET time = '2022-01-05', code = 'BLOCKED' WHERE id = 10;