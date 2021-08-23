ALTER TABLE notification
ADD COLUMN seen bool;

UPDATE notification
SET seen = false;