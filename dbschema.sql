CREATE TABLE Departments (
  departmentID integer NOT NULL,
  name varchar(50) NULL,
  headOfDeptt varchar(50) NULL,
  PRIMARY KEY (departmentID)
);

CREATE TABLE Students (
  studentID integer NOT NULL,
  name varchar(50) NULL,
  PRIMARY KEY (studentID)
);

CREATE TABLE Faculties (
  facultyID integer NOT NULL,
  departmentID integer NULL REFERENCES Departments (departmentID) ON DELETE CASCADE,
  name varchar(50) NULL,
  PRIMARY KEY (facultyID)
);

CREATE TABLE Courses (
  courseID integer NOT NULL,
  courseName varchar(50) NULL,
  semester varchar(50) NULL,
  year integer NULL,
  meetsAt varchar(50) NULL,
  room varchar(50) NULL,
  facultyID integer NULL REFERENCES Faculties (facultyID) ON DELETE CASCADE,
  PRIMARY KEY (courseID)
);

CREATE TABLE CourseEvaluations (
  evalID integer NOT NULL,
  courseID integer NULL REFERENCES Courses (courseID) ON DELETE CASCADE,
  type integer NULL,
  weightage number(2,2) NULL,
  deadline date NULL,
  meetingRoom varchar(50) NULL,
  PRIMARY KEY (evalID)
);

CREATE TABLE StudentsEnrolledCourses (
  courseID integer NOT NULL REFERENCES Courses (courseID) ON DELETE CASCADE,
  studentID integer NOT NULL REFERENCES Students (studentID) ON DELETE CASCADE,
  PRIMARY KEY (courseID,studentID)
);

CREATE TABLE EvaluationGrades (
  evalID integer NOT NULL REFERENCES CourseEvaluations (evalID) ON DELETE CASCADE,
  studentID integer NULL,
  courseID integer NULL,
  grade number(5,2) NULL,
  PRIMARY KEY (evalID,studentID),
  FOREIGN KEY (courseID, studentID) REFERENCES StudentsEnrolledCourses (courseID, studentID) ON DELETE CASCADE
);
