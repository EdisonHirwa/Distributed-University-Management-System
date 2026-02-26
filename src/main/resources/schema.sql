-- University Management System Database Schema
-- Run this script once to set up the database

CREATE DATABASE IF NOT EXISTS university_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE university_db;

-- Department table
CREATE TABLE IF NOT EXISTS department (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL UNIQUE,
    description TEXT
);

-- Lecturer table
CREATE TABLE IF NOT EXISTS lecturer (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name      VARCHAR(50)  NOT NULL,
    last_name       VARCHAR(50)  NOT NULL,
    email           VARCHAR(100) NOT NULL UNIQUE,
    specialization  VARCHAR(100),
    department_id   BIGINT,
    CONSTRAINT fk_lecturer_dept FOREIGN KEY (department_id) REFERENCES department(id) ON DELETE SET NULL
);

-- Course table
CREATE TABLE IF NOT EXISTS course (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_code     VARCHAR(20)  NOT NULL UNIQUE,
    title           VARCHAR(200) NOT NULL,
    credits         INT          NOT NULL DEFAULT 3,
    description     TEXT,
    department_id   BIGINT,
    lecturer_id     BIGINT,
    CONSTRAINT fk_course_dept    FOREIGN KEY (department_id) REFERENCES department(id) ON DELETE SET NULL,
    CONSTRAINT fk_course_lec     FOREIGN KEY (lecturer_id)   REFERENCES lecturer(id)   ON DELETE SET NULL
);

-- Student table
CREATE TABLE IF NOT EXISTS student (
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name       VARCHAR(50)  NOT NULL,
    last_name        VARCHAR(50)  NOT NULL,
    email            VARCHAR(100) NOT NULL UNIQUE,
    enrollment_date  DATE,
    student_number   VARCHAR(20) UNIQUE,
    department_id    BIGINT,
    CONSTRAINT fk_student_dept FOREIGN KEY (department_id) REFERENCES department(id) ON DELETE SET NULL
);

-- Student-Course enrollment join table (many-to-many)
CREATE TABLE IF NOT EXISTS student_course (
    student_id  BIGINT NOT NULL,
    course_id   BIGINT NOT NULL,
    PRIMARY KEY (student_id, course_id),
    CONSTRAINT fk_sc_student FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
    CONSTRAINT fk_sc_course  FOREIGN KEY (course_id)  REFERENCES course(id)  ON DELETE CASCADE
);

-- Sample seed data
INSERT IGNORE INTO department (name, description) VALUES
    ('Computer Science', 'Covers software engineering, AI, and computing fundamentals'),
    ('Mathematics', 'Pure and applied mathematics including statistics'),
    ('Business Administration', 'Business strategy, management, and economics'),
    ('Electrical Engineering', 'Circuit design, embedded systems, and power engineering');

INSERT IGNORE INTO lecturer (first_name, last_name, email, specialization, department_id) VALUES
    ('Alice', 'Johnson',  'alice.johnson@university.edu',  'Artificial Intelligence',       1),
    ('Bob',   'Williams', 'bob.williams@university.edu',   'Data Structures & Algorithms',  1),
    ('Carol', 'Smith',    'carol.smith@university.edu',    'Linear Algebra',                2),
    ('David', 'Brown',    'david.brown@university.edu',    'Business Strategy',             3);

INSERT IGNORE INTO course (course_code, title, credits, description, department_id, lecturer_id) VALUES
    ('CS101',  'Introduction to Programming',   3, 'Fundamentals of programming using Java',       1, 2),
    ('CS201',  'Data Structures',               4, 'Arrays, linked lists, trees and graphs',       1, 2),
    ('CS301',  'Artificial Intelligence',       4, 'Search, knowledge representation, ML basics',  1, 1),
    ('MATH101','Calculus I',                    3, 'Limits, derivatives, and integrals',            2, 3),
    ('BUS101', 'Principles of Management',      3, 'Foundations of business management',           3, 4);

INSERT IGNORE INTO student (first_name, last_name, email, enrollment_date, student_number, department_id) VALUES
    ('Emma',   'Davis',   'emma.davis@students.edu',   '2024-02-01', 'STU2024001', 1),
    ('Liam',   'Wilson',  'liam.wilson@students.edu',  '2024-02-01', 'STU2024002', 1),
    ('Olivia', 'Moore',   'olivia.moore@students.edu', '2025-01-15', 'STU2025001', 2),
    ('Noah',   'Taylor',  'noah.taylor@students.edu',  '2025-01-15', 'STU2025002', 3);

