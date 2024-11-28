-- liquibase formatted sql

-- changeset Linskay:1
CREATE INDEX student_name_index ON student (name);

-- changeset Linskay:2
CREATE INDEX faculty_cn_index ON faculty (color, name);