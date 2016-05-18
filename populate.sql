INSERT INTO Departments VALUES (1, 'Computer Science', 'Nathan');
INSERT INTO Departments VALUES (2, 'Mathematics', 'Abhinav');
INSERT INTO Departments VALUES (3, 'Statistics', 'Abhishek');
INSERT INTO Departments VALUES (4, 'Physics', 'Megan');


INSERT INTO Students VALUES (1, 'Abhijeet Gaurav');
INSERT INTO Students VALUES (3, 'Yusen Zhang');
INSERT INTO Students VALUES (4, 'Zack Perry');
INSERT INTO Students VALUES (5, 'Nich Narla');


INSERT INTO Faculties VALUES (1, 1, 'Elisa Bertino');
INSERT INTO Faculties VALUES (2, 1, 'Ananth Grama');
INSERT INTO Faculties VALUES (3, 1, 'Elena Grigoresu');
INSERT INTO Faculties VALUES (4, 3, 'Mark Ward');
INSERT INTO Faculties VALUES (5, 2, 'Veronica Quitalo');



INSERT INTO Courses VALUES (1, 'Information Systems', 'Spring', 2016, '9:30 AM', 'LWSN B114', 1);
INSERT INTO Courses VALUES (2, 'Foundations Of CS', 'Spring', 2015, '1:30 PM', 'PHYS 114', 2);
INSERT INTO Courses VALUES (3, 'Theory of Computation', 'Spring', 2016, '12:00 AM', 'LWSN 102', 3);
INSERT INTO Courses VALUES (4, 'Differntial Equations', 'Spring', 2016, '2:30 PM', 'LWSN 102', 3);
INSERT INTO Courses VALUES (5, 'Programming in C', 'Spring', 2015, '10:30 AM', 'LWSN 102', 1);



INSERT INTO CourseEvaluations VALUES (1, 1, 0, 0.10, '30-JAN-16', 'LWSN 155');
INSERT INTO CourseEvaluations VALUES (2, 1, 0, 0.10, '05-FEB-16', 'LWSN 155');
INSERT INTO CourseEvaluations VALUES (3, 1, 1, 0.10, '07-FEB-16', 'LWSN 155');
INSERT INTO CourseEvaluations VALUES (4, 1, 3, 0.10, '14-FEB-16', 'LWSN 155');
INSERT INTO CourseEvaluations VALUES (5, 1, 2, 0.10, '29-FEB-16', 'LWSN 155');
INSERT INTO CourseEvaluations VALUES (6, 1, 0, 0.50, '10-MAR-16', 'MSB 100');


INSERT INTO StudentsEnrolledCourses VALUES (1, 1);
INSERT INTO StudentsEnrolledCourses VALUES (2, 1);
INSERT INTO StudentsEnrolledCourses VALUES (3, 1);
INSERT INTO StudentsEnrolledCourses VALUES (1, 3);

INSERT INTO EvaluationGrades VALUES (1, 1, 1, 90.00);
INSERT INTO EvaluationGrades VALUES (2, 1, 1, 60.00);
INSERT INTO EvaluationGrades VALUES (5, 3, 1, 40.00);



