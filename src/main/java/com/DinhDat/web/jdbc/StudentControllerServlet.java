package com.DinhDat.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */

// lớp này có nhiệm vụ xử lí những data lấy được từ lớp StudentDatabaseUtil, tạo thuộc tính cho request và 1 vài object để
// chuyển tới trang jsp nhằm nhiệm vụ view
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private StudentDatabaseUtil studentDbUtil;
	
	@Resource(name="jdbc/web_student_tracker") // phải có dòng này để kết nối với jdbc driver
	
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		// creat our student db util .. and pass in the conn pool / datasource
		try {
			studentDbUtil = new StudentDatabaseUtil(dataSource);
		}catch(Exception e) {
			throw new ServletException(e);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		// list the students.. in MVC fashion
		String theCommand = request.getParameter("command");
		if (theCommand == null) theCommand = "LIST";
		switch (theCommand) {
		case "LIST" : {
			listStudent(request, response);
		}
		case "ADD": {
			addStudent(request,response);
		}
		case "LOAD": {
			loadStudent(request,response);
			break;
		}
		case "UPDATE": {
			updateStudent(request,response);
			break;
		}
		case "DELETE":{
			deleteStudent(request,response);
			break;
		}
		case "DELETE_ALL": {
			delteAllStudent(request,response);
		}
		default:
			listStudent(request, response);
		}
		}catch (Exception e) {
			throw new ServletException(e);
		}

	}

	private void delteAllStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		studentDbUtil.delelAll();
		listStudent(request, response);

	}

	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 int StudentId = Integer.parseInt(request.getParameter("studentId"));
		 studentDbUtil.deleteStudent(StudentId);
		 listStudent(request, response);
		 
	}

	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	String firstName = request.getParameter("firstName");
	String lastName = request.getParameter("lastName");
	String email = request.getParameter("email");
	
	int studentId = Integer.parseInt(request.getParameter("studentId"));
	Student newStudent = new Student(studentId, firstName, lastName, email);
	studentDbUtil.updateStudent(newStudent);
	listStudent(request, response);
	
	}

	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// read student id from form data
		String theStudentId= request.getParameter("studentId");
		
		// get student from database
		Student theStudent = studentDbUtil.getStudent(theStudentId);
		
		// place student in the request attribute
		request.setAttribute("THE_STUDENT", theStudent);
		
		// send to jsp page: update-student-form
		RequestDispatcher dispatcher = request.getRequestDispatcher("update-student-form.jsp");
		dispatcher.forward(request, response);
		
	}

	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get the info from jsp form
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		// creat new Student object
		Student student = new Student(firstName,lastName,email);
		// add student into database
		studentDbUtil.addStudent(student);
		// return main page (the student list - servlet)
		listStudent(request, response);
		
	}

	private void listStudent(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		// get the students from dc util
		List<Student> students = studentDbUtil.getStudents();
		// add students to the request
		request.setAttribute("STUDENT_LIST", students);		
		// send to jsp page 
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-student.jsp");
		dispatcher.forward(request, response);
		
	}
}
