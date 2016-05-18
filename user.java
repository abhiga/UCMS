import java.util.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class user {
	Connection con;

	public user() {
		try {
			Class.forName( "oracle.jdbc.driver.OracleDriver" );
		}
		catch ( ClassNotFoundException e ) {
			e.printStackTrace();
		}
		try {
			con =
					DriverManager.getConnection( "jdbc:oracle:thin:@claros.cs.purdue.edu:1524:strep","agaurav", "abhiga232" );
		}
		catch ( SQLException e ){
			e.printStackTrace();
		}
	}
	//faculty functions
	public void createCourse() {

		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter course ID:");
		int cid = scanner.nextInt();
		scanner.nextLine();
		System.out.print("Enter course name:");
		String cname = scanner.nextLine();
		System.out.print("Enter semester:");
		String semester = scanner.nextLine();
		System.out.print("Enter year:");
		int year = scanner.nextInt();
		scanner.nextLine();
		System.out.print("Enter meetsat:");
		String meetsAt = scanner.nextLine();
		System.out.print("Enter room:");
		String room = scanner.nextLine();
		System.out.print("Enter facultyID:");
		int facultyID = scanner.nextInt();
		scanner.nextLine();

		String update = "insert into Courses values(" + cid + ", '" + cname + "','" + semester +"', " + year + ",'" + meetsAt + "', '" + room + "', " + facultyID + ")";
		System.out.println(update);
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate( update );
			stmt.close();
		}
		catch ( SQLException e ) {
		}

	}
	public void modifyCourse() {
		try {
			String semester = "", meetsAt = "", room = "";
			int cid = 0, year = 0;
			int count = 0;
			Scanner scanner = new Scanner(System.in);
			System.out.print("Enter course name you want to update:");
			String cname = scanner.nextLine();
			System.out.print("Enter facultyID:");
			int facultyID = scanner.nextInt();
			scanner.nextLine();
			String stmt = "Select * from Courses where courseName=? and facultyID=?";
			try {
				PreparedStatement ps = con.prepareStatement( stmt );
				ps.setString( 1, cname );
				ps.setInt( 2, facultyID);
				ResultSet rs = ps.executeQuery();
				while ( rs.next() ) {
					cid = rs.getInt( "courseID" );
					year = rs.getInt( "year" );
					meetsAt = rs.getString( "meetsAt" );
					room = rs.getString( "room" );
					semester = rs.getString( "semester" );
					count++;
				}
				if (count != 0) {
					System.out.println("\nOld Entry::\n" + "courseID: " + cid + " \ncourseName: " + cname + " \nsemester: " + semester + " \nyear: " + year + " \nmeetsAt: " + meetsAt + " \nroom: " + room);
					System.out.println("\nDo you want to delete this course record? Enter 1 for yes and 0 for no:");
					int ans = scanner.nextInt();
					scanner.nextLine(); 
					if (ans == 1) {
						String par = "delete from Courses where courseID=?";
						try {
							PreparedStatement pars = con.prepareStatement( par );
							pars.setInt( 1, cid);
							pars.executeQuery();
							pars.close();
							return;
						}
						catch (SQLException e){
							e.printStackTrace();
						}
					}
					System.out.println("\n\nEnter the new modified values:\n");
					System.out.println("Enter new updated course name:");
					String newcname = scanner.nextLine();
					System.out.print("Enter updated semester value:");
					semester = scanner.nextLine();
					System.out.print("Enter updated year value:");
					year = scanner.nextInt();
					scanner.nextLine();
					System.out.print("Enter updated meetsAt value:");
					meetsAt = scanner.nextLine();
					System.out.print("Enter updated room:");
					room = scanner.nextLine();

					String dstmt = "update Courses SET courseName=?, semester=?, year=?, meetsAt=?, room=? where courseID=?";
					try {
						PreparedStatement nps = con.prepareStatement( dstmt );
						nps.setString( 1, newcname );
						nps.setString( 2, semester);
						nps.setInt( 3, year);
						nps.setString( 4, meetsAt);
						nps.setString( 5, room);
						nps.setInt( 6, cid);

						nps.executeQuery();
						nps.close();
					}
					catch (SQLException e){
						e.printStackTrace();
					}
				}


				else {
					System.out.println("Unauthorized Access: You didn't create this course.\n");
				}
				rs.close();
				ps.close();
			}
			catch (SQLException e){
				e.printStackTrace();
			}
		}
		catch (Exception e){
			System.out.println("Oops! Something didn't work. Check your parameters please. :(");
		}
	}
	public void assignStudents() {

		Scanner scanner = new Scanner(System.in);

		LinkedList list = new LinkedList();
		int cid;
		System.out.print("Enter facultyID:");
		int facultyID = scanner.nextInt();
		scanner.nextLine();
		String s = "Select * from Courses where facultyID=?";
		try {
			PreparedStatement ps = con.prepareStatement( s );
			ps.setInt( 1, facultyID);
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ) {
				cid = rs.getInt( "courseID" );
				list.add(cid);
			}
		}
		catch ( SQLException e ) {
		}
		System.out.println("Enter the unique ID the course (courseID) in which you want to add this student:");
		int courseID = scanner.nextInt();
		scanner.nextLine();
		if (!list.contains(courseID)) {
			System.out.println("Unauthorized access: You didn't create this course\n");
			return;
		}
		System.out.println("Enter the unique ID (studentID) of this student:");
		int studentID = scanner.nextInt();
		scanner.nextLine();
		String update = "insert into StudentsEnrolledCourses values(" + courseID + ", " + studentID + ")";
		System.out.println(update);
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate( update );
			stmt.close();
		}
		catch ( SQLException e ) {
		}

	}

	public void createEval() {
		try {
			Scanner scanner = new Scanner(System.in);

			LinkedList list = new LinkedList();
			int cid;
			System.out.print("Enter facultyID:");
			int facultyID = scanner.nextInt();
			scanner.nextLine();
			String s = "Select * from Courses where facultyID=?";
			try {
				PreparedStatement ps = con.prepareStatement( s );
				ps.setInt( 1, facultyID);
				ResultSet rs = ps.executeQuery();
				while ( rs.next() ) {
					cid = rs.getInt( "courseID" );
					list.add(cid);
				}
			}
			catch ( SQLException e ) {
			}
			System.out.println("Enter the unique ID of the course (courseID) for which you want to create evaluations:");
			int courseID = scanner.nextInt();
			scanner.nextLine();
			if (!list.contains(courseID)) {
				System.out.println("Unauthorized access: You didn't create this course\n");
				return;
			}

			System.out.println("\n\nEnter the evaluation table entries:\n");
			System.out.println("Enter evaluationID:");
			int evalID = scanner.nextInt();
			scanner.nextLine();
			System.out.print("For type, enter 1 for hw, 2 for midterm, 3 for final Exam and 4 for project:");
			int type = scanner.nextInt();
			scanner.nextLine();
			System.out.println("Enter Weightage:");
			float weight = scanner.nextFloat();
			scanner.nextLine();
			System.out.println("Steps to enter deadline: (type: DD-MMM-YY)\n");

			System.out.println("Enter Date(DD): ");
			int dd = scanner.nextInt();
			scanner.nextLine();
			System.out.println("Enter Month (MMM) (March is MAR and not 04): ");
			String mm = scanner.nextLine();
			System.out.print("Enter year (YY): ");
			int yr = scanner.nextInt();
			scanner.nextLine();
			java.util.Date date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse("DEC");
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int month = cal.get(Calendar.MONTH);
			month++;
			SimpleDateFormat fmt = new SimpleDateFormat("yy-MM-dd");
			java.util.Date dueDate = fmt.parse(yr + "-" + month + "-" + dd);
			java.util.Date dateNow = new java.util.Date();
			if (dateNow.after(dueDate) == true) {
				System.out.println("Error: Due date has passed. Please Enter again\n");
				return;
			}

			System.out.print("Enter meetingRoom");
			String meetingRoom = scanner.nextLine();
			String dstmt = "insert into CourseEvaluations values(?, ?, ?, ?, ?, ?)";
			try {
				PreparedStatement nps = con.prepareStatement( dstmt );
				nps.setInt( 1,  evalID);
				nps.setInt( 2, courseID);
				nps.setInt( 3, type);
				nps.setFloat( 4, weight);
				nps.setString( 5, dd + "-" + mm + "-" + yr);
				nps.setString( 6, meetingRoom);

				nps.executeQuery();
				nps.close();
			}
			catch (SQLException e){
				e.printStackTrace();
			}
		}
		catch (Exception e){
			System.out.println("Ooops! Something didn't work\n");
		}

	}


	public void modifyEval() {
		try {
			Scanner scanner = new Scanner(System.in);

			LinkedList list = new LinkedList();
			int cid;
			System.out.print("Enter facultyID:");
			int facultyID = scanner.nextInt();
			scanner.nextLine();
			String s = "Select * from Courses where facultyID=?";
			try {
				PreparedStatement ps = con.prepareStatement( s );
				ps.setInt( 1, facultyID);
				ResultSet rs = ps.executeQuery();
				while ( rs.next() ) {
					cid = rs.getInt( "courseID" );
					list.add(cid);
				}
			}
			catch ( SQLException e ) {
			}
			System.out.println("Enter the unique ID of the course (courseID) for which you want to modify evaluations:");
			int courseID = scanner.nextInt();
			scanner.nextLine();
			if (!list.contains(courseID)) {
				System.out.println("Unauthorized access: You didn't create this course\n");
				return;
			}

			String stmt = "Select * from CourseEvaluations where courseID=?";
			try {
				PreparedStatement ps = con.prepareStatement( stmt );
				ps.setInt( 1, courseID );
				ResultSet rs = ps.executeQuery();
				System.out.println("You have created folowing Evaluations for this course.");
				System.out.println("EvalID type weight dealine meetingRoom\n");
				while ( rs.next() ) {
					int evalid = rs.getInt( "evalID" );
					int type = rs.getInt( "type" );
					float weight = rs.getFloat( "weightage" );
					String deadline = rs.getString( "deadline" );
					String meetingRoom = rs.getString( "meetingRoom" );
					System.out.println(evalid + "     " + type + "       " + weight + "       " + deadline + "      " + meetingRoom);
				} 
			}
			catch ( SQLException e ) {
			}
			System.out.println("Enter the EvalId from above whose details you want to modify and enter the updated value:\n");

			System.out.println("Enter evaluationID of entry you want to modify:");
			int evalID = scanner.nextInt();
			scanner.nextLine();





			System.out.println("\nDo you want to delete this evaluation record? Enter 1 for yes and 0 for no:");
			int ans = scanner.nextInt();
			scanner.nextLine(); 
			if (ans == 1) {
				String par = "delete from CourseEvaluations where evalID=?";
				try {
					PreparedStatement pars = con.prepareStatement( par );
					pars.setInt( 1, evalID);
					pars.executeQuery();
					pars.close();
					return;
				}
				catch (SQLException e){
					e.printStackTrace();
				}
			}






			System.out.print("For new type, enter 1 for hw, 2 for midterm, 3 for final Exam and 4 for project:");
			int type = scanner.nextInt();
			scanner.nextLine();
			System.out.println("Enter new Weightage:");
			float weight = scanner.nextFloat();
			scanner.nextLine();
			System.out.println("Steps to enter deadline: (type: DD-MMM-YY)\n");

			System.out.println("Enter Date(DD): ");
			int dd = scanner.nextInt();
			scanner.nextLine();
			System.out.println("Enter Month (MMM) (March is MAR and not 04): ");
			String mm = scanner.nextLine();
			System.out.print("Enter year (YY): ");
			int yr = scanner.nextInt();
			scanner.nextLine();
			java.util.Date date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse("DEC");
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int month = cal.get(Calendar.MONTH);
			month++;
			SimpleDateFormat fmt = new SimpleDateFormat("yy-MM-dd");
			java.util.Date dueDate = fmt.parse(yr + "-" + month + "-" + dd);
			java.util.Date dateNow = new java.util.Date();
			if (dateNow.after(dueDate) == true) {
				System.out.println("Error: Due date has passed. Please Enter again\n");
				return;
			}

			System.out.print("Enter meetingRoom");
			String meetingRoom = scanner.nextLine();

			String dstmt = "update CourseEvaluations SET type=?, weightage=?, deadline=?, meetingRoom=? where evalID=?";
			try {
				PreparedStatement nps = con.prepareStatement( dstmt );

				nps.setInt( 1, type);
				nps.setFloat( 2, weight);
				nps.setString( 3, dd + "-" + mm + "-" + yr);
				nps.setString( 4, meetingRoom);
				nps.setInt( 5,  evalID);

				nps.executeQuery();
				nps.close();
			}
			catch (SQLException e){
				e.printStackTrace();
			}

		}
		catch (Exception e) {
		}


	}
	public void enterGrades() {
		  Scanner scanner = new Scanner(System.in);
		  System.out.print("Enter facultyID: ");
          int facultyID = scanner.nextInt();
          System.out.print("Enter the studentID you want to enter grades for: ");
          int studentID = scanner.nextInt();
          try {
              System.out.print("Enter the evaluationID for which you want to enter grades: ");
              int evalID = scanner.nextInt();
              System.out.print("Enter the courseID of the course you want to enter grades for: ");
              int courseID = scanner.nextInt();
              System.out.print("Enter grade in the form of a decimal with 2 decimal places");
              float grade = scanner.nextFloat();
              String addGrade = "insert into EvaluationGrades values (?, ?, ?, ?)";
              PreparedStatement stmt = con.prepareStatement(addGrade);
              stmt.setInt(1, evalID);
              stmt.setInt(2, studentID);
              stmt.setInt(3, courseID);
              stmt.setFloat(4, grade );
              stmt.executeUpdate();
              stmt.close();
          } catch (SQLException e) {
              System.out.println("error SQL Exception");
              return;
          }

	}

	public void reportClasses() {
		Scanner scanner = new Scanner(System.in);
		try {
            System.out.print("Enter your facultyID: ");
            int facultyID = scanner.nextInt();
            String report = "select co.courseName, co.meetsat,co.meetingRoom,COUNT(Distinct sc.studentID),COUNT(distinct ce.evalID) " +
                    "from Courses co join EvaluationGrades ce on ce.courseID=co.courseID join StudentsEnrolledCourses sc on " +
                    "sc.courseId=ce.courseID where co.facultyID = ? group by co.courseName,co.meetsAt,co.meetingRoom";
            
            PreparedStatement stmt = con.prepareStatement(report);
            stmt.setInt(1,facultyID);
            ResultSet rs = stmt.executeQuery();
            System.out.println("\nHere are the courses you've created: \n");
            System.out.println("CourseName   " + "   MeetsAt   " + "    Room   " + "   Num_Students   " + "   Num_evaluations");
            while (rs.next()) {
                String cName = rs.getString(1);
                String meetsAt = rs.getString(2);
                String room = rs.getString(3);
                int numS = rs.getInt(4);
                int numE = rs.getInt(5);
                System.out.println(cName + " " + meetsAt + " " + room + " " + numS + " " +numE);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("SQL Error Retry.");
            return;
        }
        catch (Exception e) {
            System.out.println("Something didn't work");
            return;
        }
		
	}

	public void reportStudentGrades() {
		Scanner scanner = new Scanner(System.in);
		try {
            System.out.print("Enter your facultyID: ");
            int facultyID = scanner.nextInt();
            String report = "select DISTINCT co.courseName, co.semester,co.year,s.name, " +
                    "SUM(se.grade * ce.weightage) from Courses co join EvaluationGrades se on se.courseID=co.courseID " +
                    "join courseevaluations ce on ce.courseId=se.courseID join StudentsenrolledCourses sc on se.courseId=sc.courseID " +
                    "join students s on s.studentID = sc.studentID where co.facultyid = ? and sc.studentId=se.studentID " +
                    "and ce.evalid=se.evalid group by co.courseName,co.semester,co.year,s.name;";
            PreparedStatement stmt = con.prepareStatement(report);
            stmt.setInt(1,facultyID);
            ResultSet rs = stmt.executeQuery();
            System.out.println("\nHere are the courses you've created: \n");
            System.out.println("CourseName   Semester  Year  Student Name    Current Grade");
            while (rs.next()) {
                String cName = rs.getString(1);
                String sem = rs.getString(2);
                int year = rs.getInt(3);
                String sName = rs.getString(4);
                float grade = rs.getFloat(5);
                System.out.println(cName +  "  "  +  sem  +  "   "  +  year +   "   "  + sName +  "  " +grade);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("SQL Error - Maybe you don't have any evaluations! Retry.");
            return;
        }
        catch (Exception e) {
            System.out.println("Error outside SQL");
            return;
        }
	}

	//student
	public void calendarOfEval(){

		System.out.println ("Enter your studentID:");
		Scanner scanner = new Scanner(System.in);
		int studentID = scanner.nextInt();
		scanner.nextLine();
		System.out.println("Following is your evaluation details:");
		System.out.println("evalID   courseID type weightage deadline meetingRoom");


		String stmt = "SELECT * FROM StudentsEnrolledCourses INNER JOIN CourseEvaluations ON StudentsEnrolledCourses.courseID=CourseEvaluations.courseID WHERE StudentsEnrolledCourses.studentID=?";
		int count = 0;
		try {	

			PreparedStatement ps = con.prepareStatement( stmt );
			ps.setInt( 1, studentID );
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ) {
				int evaluationID = rs.getInt( "evalID" );
				String courseID = rs.getString( "courseID" );
				int type = rs.getInt( "type" );
				float weight = rs.getFloat( "weightage" );
				String date = rs.getString ( "deadline" );
				String meetingRoom = rs.getString( "meetingRoom" );
				System.out.println( evaluationID + "     " + courseID + "   " + type + "   " + weight + "  " + date + "   "  +  meetingRoom + "\n");
				count++;
			} 
			if (count == 0) {
				System.out.println("You don't have any evaluations.\n");
			}
		}
		catch ( SQLException e ) {
		}

	}

	public void myCourses() {
		System.out.println ("Enter your studentID:");
		Scanner scanner = new Scanner(System.in);
		int studentID = scanner.nextInt();
		scanner.nextLine();
		System.out.println("Following is your course details:");
		System.out.println("courseID courseName semester year meetsAt room");


		String stmt = "SELECT * FROM StudentsEnrolledCourses INNER JOIN Courses ON StudentsEnrolledCourses.courseID=Courses.courseID WHERE StudentsEnrolledCourses.studentID=?";
		int count = 0;
		try {	

			PreparedStatement ps = con.prepareStatement( stmt );
			ps.setInt( 1, studentID );
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ) {
				int cid = rs.getInt( "courseID" );
				String cname = rs.getString( "courseName" );
				String semester = rs.getString( "semester" );
				int year = rs.getInt( "year" );
				String meetsAt = rs.getString( "meetsAt" );
				String room = rs.getString( "room" );
				System.out.println(cid + "  " + cname + "  " + semester + "  " + year + "  " + meetsAt + "   " + room);
				count++;
			} 
			if (count == 0) {
				System.out.println("Not enrolled in any course..\n");
			}
		}
		catch ( SQLException e ) {
		}


	}

	public void myGrades() {
		System.out.println ("Enter your studentID:");
		Scanner scanner = new Scanner(System.in);
		int studentID = scanner.nextInt();
		scanner.nextLine();
		String stmt = "Select evalID, courseID, grade from EvaluationGrades where studentID=?";
		int count = 0;
		System.out.println( "evaluationID courseID grade");
		try {	
			PreparedStatement ps = con.prepareStatement( stmt );
			ps.setInt( 1, studentID);
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ) {
				int evaluationID = rs.getInt( "evalID" );
				String courseID = rs.getString( "courseID" );
				String grade = rs.getString( "grade" );
				System.out.println( evaluationID + "     " + courseID + "      " +  grade + "\n");
				count++;
			}
			if (count == 0) {
				System.out.println("No records in Department table.\n");
			}
		}
		catch ( SQLException e ) {
		}

	}

	//admin
	public void departmentReport() {
		String stmt = "Select * from Departments";
		int count = 0;
		System.out.println( "departmentID Name headOfDeptt\n");
		try {
			PreparedStatement ps = con.prepareStatement( stmt );
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ) {
				int departmentID = rs.getInt( "departmentID" );
				String name = rs.getString( "name" );
				String head = rs.getString( "headOfDeptt" );
				System.out.println( departmentID + "     " + name + "  " +  head + "\n");
				count++;
			}
			if (count == 0) {
				System.out.println("No records in Department table.\n");
			}
		}
		catch ( SQLException e ) {
		}
	}

	public void facultyReport() {
		String stmt = "Select * from Faculties";
		int count = 0;
		System.out.println( "facultyID Name departmentID\n");
		try {
			PreparedStatement ps = con.prepareStatement( stmt );
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ) {
				int facultyID = rs.getInt( "facultyID" );
				String name = rs.getString( "name" );
				int departmentID = rs.getInt( "departmentID" );
				System.out.println( facultyID + "     " + name + "      " +  departmentID + "\n");
				count++;
			}
			if (count == 0) {
				System.out.println("No records in Faculties table.\n");
			}
		}
		catch ( SQLException e ) {
		}
	}

	public static void goodBye() {
		System.out.println("Thanks for using University Course Management System :) \n"); 
	}
	/* Main() */
	public static void main( String [] args ) {
		try {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Welcome to University Course Management System.\n");
			user par = new user();
			int choice = 0;
			while (true) {
				System.out.println("Type of USER:\n");
				System.out.println("1 for Admin\n");
				System.out.println("2 for Faculty\n");
				System.out.println("3 for Student\n");
				try {
					choice = scanner.nextInt();
					scanner.nextLine();
				}
				catch(java.util.InputMismatchException e) {
				}
				if (choice == 1 || choice == 2 || choice == 3) {
					int type = 0;
					if (choice == 1) {
						System.out.println("Welcome as an Admin:\n");
						while (true) {
							System.out.println("Type 1 for Department Report \n");
							System.out.println("Type 2 for Faculty Report \n");
							System.out.println("Type 3 to Go Back to User Menu \n");
							System.out.println("Type 4 to Exit \n");
							type = scanner.nextInt();
							scanner.nextLine();
							if (type == 1) {
								par.departmentReport();

							}
							else if (type == 2) {
								par.facultyReport();
							}
							else if (type == 3) {
								break;
							}
							else if (type == 4) {
								goodBye();
								return;
							}
							else {
								System.out.println("Please try again and enter 1, 2 or 3.\n");
							}
						}
					}
					else if (choice == 2) {
						System.out.println("Welcome as a Faculty: \n");	
						while (true) {
							System.out.println("Type 1 for Create Course \n");
							System.out.println("Type 2 for Modify Course \n");
							System.out.println("Type 3 to Assign Students to your course \n");
							System.out.println("Type 4 to Create Evaluation \n");
							System.out.println("Type 5 for Modify Evaluation \n");
							System.out.println("Type 6 to Enter Grades \n");
							System.out.println("Type 7 for Report of Classes \n");
							System.out.println("Type 8 for Report of Students and Grades \n");
							System.out.println("Type 9 to Go Back to User Menu \n");
							System.out.println("Type 10 to Exit \n");
							type = scanner.nextInt();
							scanner.nextLine();
							if (type == 1) {
								par.createCourse();

							}
							else if (type == 2) {
								par.modifyCourse();
							}
							else if (type == 3) {
								par.assignStudents();
							}
							else if (type == 4) {
								par.createEval();
							}
							else if (type == 5) {
								par.modifyEval();
							}
							else if (type == 6) {
								par.enterGrades();
							}
							else if (type == 7) {
								par.reportClasses();
							}
							else if (type == 8) {
								par.reportStudentGrades();
							}
							else if (type == 9) {
								break;
							}
							else if (type == 10) {
								goodBye();
								return;
							}
							else {
								System.out.println("Please try again and enter 1, 2, 3, 4, 5, 6, 7, 8, 9 or 10.\n");
							}
						}

					}
					else if (choice == 3) {
						System.out.println("Welcome as a Student.\n");
						while (true) {
							System.out.println("Type 1 for Calendar of Evaluations \n");
							System.out.println("Type 2 for My Courses \n");
							System.out.println("Type 3 for My Grades \n");
							System.out.println("Type 4 to Go Back to User Menu \n");
							System.out.println("Type 5 to Exit \n");
							type = scanner.nextInt();
							scanner.nextLine();
							if (type == 1) {
								par.calendarOfEval();

							}
							else if (type == 2) {
								par.myCourses();
							}
							else if (type == 3) {
								par.myGrades();
							}
							else if (type == 4) {
								break;
							}
							else if (type == 5) {
								goodBye();
								return;
							}
							else {
								System.out.println("Please try again and enter 1, 2, 3, 4 or 5.\n");
							}
						}

					}
				}
				else {
					System.out.println("Please try again and enter 1, 2 or 3.\n");
				}		

			}

		}
		catch (Exception e) {
			System.out.println("Oops! Something didn't work. Check your parameters (Data Type) you have entered please. :(");
		}
	}
	
}

