<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<title>STUDENT TRACKER APP</title>

<link type="text/css" rel = "stylesheet " href = "CSS/style.css">



</head>
<body>
	<div id="wrapper">
		<div id="header">
		
			<h2>Hanoi University</h2>
		</div>
	
	</div>
	
	<div id="container">
		<div id="content">
		
		<input type = "button" value = "Add Student"
		onclick ="window.location.href='add-student-form.jsp'; return false;"
		class = "add-student-button"/>
		
		
	
		<c:url var="deleteAll" value ="StudentControllerServlet">
	
	 	<c:param name="command" value ="DELETE_ALL"> </c:param>
	
	</c:url>
	
		&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
		<input type = "button" value = "Delete All"  onclick = "window.location.href= '${deleteAll}' ; return false;
		" class = "add-student-button"   >
		
		<table>
		<tr>
		<th> First Name </th>
		<th> Last Name </th>
		<th> Email </th>
		<th> Action </th>
		</tr>
	<c:forEach var = "tempstudent" items="${STUDENT_LIST}">
	
	<c:url var="tempLink" value ="StudentControllerServlet">
	
	 <!--2 parameters is sending by the link in two line code after-->
	 	<c:param name="command" value ="LOAD"> </c:param>
	 	<c:param name="studentId" value="${tempstudent.id}"> </c:param>
	
	</c:url>
	
	<c:url var="deleteLink" value ="StudentControllerServlet">
	
	 <!--2 parameters is sending by the link in two line code after-->
	 	<c:param name="command" value ="DELETE"> </c:param>
	 	<c:param name="studentId" value="${tempstudent.id}"> </c:param>
	
	</c:url>
	<tr>
	<td> ${tempstudent.firstName} </td>
	<td> ${tempstudent.lastName} </td>
	<td> ${tempstudent.email} </td>
	<td> <a href="${tempLink}">Update</a> 
	|
	<!-- put a little javascript code here after onclick -->
	<a href="${ deleteLink}" onclick="if(!confirm('Are you sure want to delete this student?')) return false">Delete</a>
	</td>
	</tr>
	</c:forEach>
	
		</table>
		
		</div>	
	</div>

</body>
</html>