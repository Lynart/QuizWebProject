<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	
	<head>
		<meta charset="utf-8"> 	
		<title>Quiz Application</title>
		<!--Google fonts
		<link href="http://fonts.googleapis.com/css?family=Lobster" rel="stylesheet" type="text/css">
		-->
		<link rel="stylesheet" type="text/css" href="css/site.css" />

	</head> 

<body>

	<!--Menu Bar-->
	<nav class="navbar navbar-inverse" style="margin: 0px;">
	  <div class="container-fluid">
	  
	    <div class="navbar-header"></div>
	    <!-- Collect the nav links, forms, and other content for toggling -->
	    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
	      <ul class="nav navbar-nav">
			<li><a href="#"><b>Quiz Application</b></a></li>
			<li><a href="#">Home</a></li>
	      </ul>
	    </div><!-- /.navbar-collapse -->
	  </div><!-- /.container-fluid -->
	</nav>
	

	
	
	<div class="col-md-12" id="grad">
	
	<div class="col-md-12">
		<h1>View Questions</h1>
	</div>>
	<br />
	
	
	<div class="col-md-2"></div>
	<div class="col-md-8 vertical-center">
	
		<!-- Below will be blank unless delete is requested and fails -->
	${noDelete}
	<table class="table" style="color:white;">
		<tr>
			<th>Id</th>
			<th>Correct</th>
			<th>Type</th>
			<th>Question</th>
			<th></th>
		</tr>
	
		<c:forEach var="question" items="${questions}">
			<tr>
				<td><c:out value="${question.getId()}" /></td>
				<td><c:out value="${question.getDifficulty()}" /></td>
				<td><c:out value="${question.getType()}" /></td>
				<td><c:out value="${question.getDescription()}" /></td>
				
				<td>
					<form action="admin" method="post">
						<button class="btn-info" type="submit" name="getQuestion"
							value="${question.getId()}">Edit</button>
						<button class="btn-danger" type="submit" name="deleteQuestion"
							value="${question.getId()}">Delete</button>
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>
	<div class="col-md-2"></div>
	
	</div>
	</div>

	<!--Footer-->
	<div id="footer" class="container">
		<nav class="navbar navbar-inverse navbar-fixed-bottom">
			
			<div class="col-md-12">
			
				<div class="col-md-2"></div>
				
				<div class="col-md-8">
					<div class="navbar-inner text-center">
						<h5 style="color:white;margin-top:17px">
							2016 - Vincent Lee, Frank Panico, Shawn Knowles
						</h5>
					</div>
				</div>
				
				<div class="col-md-2"></div>
				
			</div>
		</nav>
	</div>
	
	<style>
		/* Sticky footer styles
		-------------------------------------------------- */
		
		html,
		body {
		  height: calc(100% - 50px);
		}
	</style>
	
	
	
	
	<!--Bootstrap Javascript placed at end of document for decreased loading times		-->
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
	
	<style>
		overflow: hidden;
	</style>
	
</body>

</html>
