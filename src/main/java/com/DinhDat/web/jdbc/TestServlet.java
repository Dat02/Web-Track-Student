package com.DinhDat.web.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	// Define datasource/connection pool for Resource Injection
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// step 1:set up the printwriter (in ra ky tu o browser)
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");  
		// step 2: get a connection to the database
		Connection myConn = null;
		java.sql.Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = dataSource.getConnection();
		// step 3: creat a sql statements
			String sql = "select * from student";
			myStmt = myConn.createStatement();
		// step 4: execute sql query
			myRs = myStmt.executeQuery(sql);
		// step 5: process the result set
			while (myRs.next()){
				// loop table
				String email = myRs.getString(4);
				out.println(email);
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

}
	

