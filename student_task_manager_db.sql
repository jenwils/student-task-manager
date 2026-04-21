CREATE DATABASE IF NOT EXISTS student_task_manager;
USE student_task_manager;

-- STUDENT TABLE
CREATE TABLE student (
    studentID INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    passwordHash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- DELIVERABLE TABLE
CREATE TABLE deliverable (
    deliverableID INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    dueDate DATE NOT NULL, 
    isHanded BOOLEAN DEFAULT FALSE,
    course VARCHAR(100),
    category VARCHAR(100),
    priority VARCHAR(20),
    status VARCHAR(100),
    studentID INT,
    FOREIGN KEY (studentID) REFERENCES student(studentID)
);

CREATE TABLE assignment (
    deliverableID INT PRIMARY KEY,
    assignmentWeight DECIMAL (4, 2),
    FOREIGN KEY (deliverableID) REFERENCES deliverable(deliverableID) ON DELETE CASCADE
);

CREATE TABLE exam (
    deliverableID INT PRIMARY KEY,
    examWeight DECIMAL (4, 2),
    FOREIGN KEY (deliverableID) REFERENCES deliverable(deliverableID) ON DELETE CASCADE
);

INSERT INTO student (name, email, passwordHash) 
VALUES ('Test Student', 'test@test.com', 'password');
