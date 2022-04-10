<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Student</title>
<link type="text/css" rel = "stylesheet" href="CSS/style.css">
<link type="text/css" rel = "stylesheet" href="CSS/add-student-style.css">
</head>


<body>
	<div id="wrapper">
		<div id ="header">
			<h2>Hanoi University</h2>
		
		</div>
	</div>
	
	<div id="container">
	<h3>Add Student</h3>
	
	<form action="StudentControllerServlet" method = "GET">
	<input type="hidden" name= "command" value = "ADD"/>
	
	<table>
		<tbody>
		 <tr>
		 	<td><label for ="firstName">First name:</label></td>
		 	<td><input type="text" name="firstName"/> </td>
		 </tr>
		  <tr>
		 	<td><label for ="lastName">Last name:</label></td>
		 	<td><input type="text" name="lastName"/> </td>
		 </tr>
		  <tr>
		 	<td><label for ="email">Your email:</label></td>
		 	<td><input type="email" name="email"/> </td>
		 </tr>
		   <tr>
		 	<td><label></label></td>
		 	<td><input type="submit" value="Save" class="save"/> </td>
		 </tr>
		</tbody>
	</table>
	</form>
	
	</div>
		<br/>
<p><a href= "StudentControllerServlet">Back to the list</a> </p>
</body>

</html>