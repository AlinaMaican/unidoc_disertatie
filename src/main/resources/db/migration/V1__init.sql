CREATE SEQUENCE users_seq;
CREATE TABLE users (
 id       BIGINT PRIMARY KEY,
 role     VARCHAR(50) NOT NULL,
 email    VARCHAR(255) NOT NULL UNIQUE,
 password VARCHAR(255) NOT NULL,
 is_active BOOLEAN NOT NULL
);

CREATE SEQUENCE learning_type_seq;
CREATE TABLE learning_type (
id       BIGINT PRIMARY KEY,
name      VARCHAR(50) NOT NULL
);

CREATE SEQUENCE university_study_type_seq;
CREATE TABLE university_study_type (
id       BIGINT PRIMARY KEY,
learning_type_id BIGINT REFERENCES learning_type (id) NOT NULL,
name   VARCHAR(50) NOT NULL
);

CREATE SEQUENCE domain_seq;
CREATE TABLE domain (
 id       BIGINT PRIMARY KEY,
 university_study_type_id BIGINT REFERENCES university_study_type (id) NOT NULL,
 name   VARCHAR(50) NOT NULL
);

CREATE SEQUENCE study_program_seq;
CREATE TABLE study_program (
 id       BIGINT PRIMARY KEY,
 domain_id BIGINT REFERENCES domain (id) NOT NULL,
 name   VARCHAR(50) NOT NULL
);

CREATE SEQUENCE study_year_seq;
CREATE TABLE study_year (
 id       BIGINT PRIMARY KEY,
 study_program_id BIGINT REFERENCES study_program (id) NOT NULL,
 name   VARCHAR(50) NOT NULL
);

CREATE SEQUENCE study_group_seq;
CREATE TABLE study_group (
 id       BIGINT PRIMARY KEY,
 study_year_id BIGINT REFERENCES study_year (id) NOT NULL,
 name   VARCHAR(50) NOT NULL
);

CREATE SEQUENCE secretary_seq;
CREATE TABLE secretary (
 id       BIGINT PRIMARY KEY,
 user_id  BIGINT REFERENCES users (id) NOT NULL,
 first_name    VARCHAR(255) NOT NULL,
 last_name VARCHAR(255) NOT NULL
);

CREATE SEQUENCE secretary_allocation_seq;
CREATE TABLE secretary_allocation (
 id       BIGINT PRIMARY KEY,
 secretary_id  BIGINT REFERENCES secretary (id) NOT NULL,
 learning_type_id BIGINT REFERENCES learning_type (id) NOT NULL,
 university_study_type_id BIGINT REFERENCES university_study_type (id) NOT NULL,
 domain_id BIGINT REFERENCES domain (id) NOT NULL,
 study_program_id BIGINT REFERENCES study_program (id) NOT NULL,
 study_year_id BIGINT REFERENCES study_year (id) NOT NULL,
 UNIQUE (learning_type_id, university_study_type_id, domain_id, study_program_id, study_year_id)
);

CREATE SEQUENCE phone_number_seq;
CREATE TABLE phone_number (
id       BIGINT PRIMARY KEY,
user_id  BIGINT REFERENCES users (id) NOT NULL,
phone_number    VARCHAR(10) NOT NULL UNIQUE
);

CREATE SEQUENCE student_seq;
CREATE TABLE student (
 id       BIGINT PRIMARY KEY,
 user_id  BIGINT REFERENCES users (id) NOT NULL,
 secretary_allocation_id BIGINT REFERENCES secretary_allocation (id),
 study_group_id BIGINT REFERENCES study_group (id) NOT NULL,
 study_program_id BIGINT REFERENCES study_program (id) NOT NULL,
 study_year_id BIGINT REFERENCES study_year (id) NOT NULL,
 domain_id BIGINT REFERENCES domain (id) NOT NULL,
 university_study_type_id BIGINT REFERENCES university_study_type (id) NOT NULL,
 learning_type_id BIGINT REFERENCES learning_type (id) NOT NULL,
 first_name    VARCHAR(255) NOT NULL,
 last_name VARCHAR(255) NOT NULL,
 cnp       VARCHAR(13) NOT NULL UNIQUE,
 registration_number varchar(20) NOT NULL UNIQUE
);

CREATE SEQUENCE secretary_document_seq;
CREATE TABLE secretary_document (
 id       BIGINT PRIMARY KEY,
 secretary_allocation_id BIGINT REFERENCES secretary_allocation (id) NOT NULL,
 name     VARCHAR(255) NOT NULL,
 description     VARCHAR(255),
 file_path_name  VARCHAR(255) NOT NULL,
 end_date_of_upload TIMESTAMP NOT NULL
);

CREATE SEQUENCE student_document_seq;
CREATE TABLE student_document (
 id       BIGINT PRIMARY KEY,
 student_id BIGINT REFERENCES student (id) NOT NULL,
 secretary_document_id BIGINT REFERENCES secretary_document (id),
 status   VARCHAR(255) NOT NULL,
 name     VARCHAR(255) NOT NULL,
 description     VARCHAR(255),
 file_path_name  VARCHAR(255) NOT NULL,
 date_of_upload TIMESTAMP NOT NULL,
 document_type   VARCHAR(255) NOT NULL
);

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

CREATE SEQUENCE notification_seq;
CREATE TABLE notification (
 id       BIGINT PRIMARY KEY,
 student_document_id BIGINT REFERENCES student_document (id) NOT NULL,
 message     VARCHAR(5000) NOT NULL,
 date TIMESTAMP NOT NULL,
 seen bool NOT NULL,
 type VARCHAR(20) NOT NULL
);
