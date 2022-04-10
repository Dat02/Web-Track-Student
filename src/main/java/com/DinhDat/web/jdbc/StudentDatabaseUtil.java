package com.DinhDat.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import javax.sql.DataSource;






// lớp này sinh ra với mục đích kết nối với databse, lấy những dữ liệu gì mà mình muốn lấy, dựa trên lớp Student
// lớp này cần nhiều class sẵn của java

public class StudentDatabaseUtil {
	private DataSource datasoure;
	
	public StudentDatabaseUtil (DataSource theDataSource){
		datasoure = theDataSource;
	}
	
	public  List<Student> getStudents() throws Exception{
		List<Student> students = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStm = null;
		ResultSet myRs = null;
		
		
		try {
		// get a connection 
		myConn = datasoure.getConnection();
		
		// creat sql statement
		myStm = myConn.createStatement();
		String sql = "select * from student order by last_name";
		
		// execute query
		myRs = myStm.executeQuery(sql);
		
		// process result set
		while (myRs.next()) {
			
			int id = myRs.getInt("id");
			String firstName = myRs.getString("first_name");
			String lastName = myRs.getString("last_name");
			String email = myRs.getString("email");
			if (firstName!=null) { 		
			Student tempstudent = new Student(id,firstName,lastName,email);
			students.add(tempstudent);
			}
			
		}
		
		}
		finally {
		// close JDBC object
			close (myConn,myStm,myRs); // tránh lãng phí tài nguyên và rò rỉ bộ nhớ
		}
		return students;
	}

	private void close(Connection myConn, Statement myStm, ResultSet myRs) {
  try {
	  if (myConn != null) {
		  // make the connection availble for other to use
		  myConn.close();
	  }
	  if (myStm != null) {
		  myStm.close();
	  }
	  if (myRs !=null) {
		  myRs.close();
	  }
  }
		catch (Exception e) {
			e.printStackTrace();
		}
	}




	public void addStudent(Student student) throws SQLException {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
	  // get db connection
		myConn = datasoure.getConnection();
		// creat sql query for insert
		String sql = "insert into student" +"(first_name,last_name, email)"
						+"values(?,?,?)";
		
		myStmt =  myConn.prepareStatement(sql);
		
		// set the param values for the student
		 myStmt.setString(1,student.getFirstName());
		 myStmt.setString(2,student.getLastName());
		 myStmt.setString(3,student.getEmail());
		//execute sql insert
		myStmt.execute();
		
		}
		finally {
			// close connection
			close(myConn, myStmt, null);
		}
		
		
		
	}

	public Student getStudent(String theStudentId) throws Exception {
	 Student theStudent = null;
	 
	 Connection myConn = null;
	 PreparedStatement myStm = null;
	 ResultSet myRs = null;
	 
	 int studentId;
	 try {
		 // convert student id to int
		 studentId = Integer.parseInt(theStudentId);
		 // get connection to database
		 myConn = datasoure.getConnection();
		 // creat sql query to select student
		 String sql ="select * from student where id=?";
		 // creat prepared statement
		 myStm = myConn.prepareStatement(sql);
		 //set params 
		 myStm.setInt(1, studentId);
		 //execute statement
		 myRs = myStm.executeQuery();
		 // retrieve data from result ser row
		 if (myRs.next()) {
			 
			 String firstName = myRs.getString("first_name"); 
			 String lastName = myRs.getString("last_name"); 
			 String email = myRs.getString("email");
			 
			 theStudent = new Student(studentId,firstName,lastName,email);
		}
		 else {
			 throw new Exception("Could not find student id: "+studentId);
		 }
		 return theStudent;
	 } finally {
		 close(myConn, myStm, myRs);
		// close connection
	}
	}

	public void updateStudent(Student newStudent) throws SQLException {
		 
		 Connection myConn = null;
		 PreparedStatement myStm = null;
		 
		 
		 try {

			 // get connection to database
			 myConn = datasoure.getConnection();
			 // creat sql query to select student
			 String sql ="update student set first_name = ?, last_name = ?, email = ? where id=?";
			 // creat prepared statement
			 myStm = myConn.prepareStatement(sql);
			 //set params 
			 myStm.setString(1,newStudent.getFirstName());
			 myStm.setString(2,newStudent.getLastName());
			 myStm.setString(3,newStudent.getEmail());
			 myStm.setInt(4, newStudent.getId());
			 
			 //execute statement
			 myStm.executeUpdate();
			 // retrieve data from result ser row
		 } finally {
			 close(myConn, myStm, null);
			// close connection
		}
		}

	public void deleteStudent(int studentId) throws SQLException {
		Connection myConn = null;
		PreparedStatement myStm = null;
		try {
		myConn = datasoure.getConnection();
		String sql = "delete from student where id =?";
		myStm = myConn.prepareStatement(sql);
		myStm.setInt(1, studentId);
		myStm.execute();
	}
		finally {
			close(myConn, myStm, null);
		}
	}

	public void delelAll() throws SQLException {
		Connection myConn = null;
		Statement myStm = null;
		try {
		myConn = datasoure.getConnection();
		String sql = "delete from student";
		myStm = myConn.createStatement();
		myStm.execute(sql);
	}
		finally {
			close(myConn, myStm, null);
		}
		
	}
		
	}

	

