CREATE SEQUENCE secretary_response_document_seq;
CREATE TABLE secretary_response_document (
id       BIGINT PRIMARY KEY,
secretary_allocation_id BIGINT REFERENCES secretary_allocation (id) NOT NULL,
student_document_id BIGINT REFERENCES student_document (id) NOT NULL,
name     VARCHAR(255) NOT NULL,
description     VARCHAR(255),
file_path_name  VARCHAR(255) NOT NULL,
date_of_upload TIMESTAMP NOT NULL
);