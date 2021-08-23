ALTER TABLE student_document
ADD COLUMN seen bool;

UPDATE student_document
SET seen = false;