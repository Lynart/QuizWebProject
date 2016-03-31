<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="utf-8">
<title>Quiz Application</title>
<!--Google fonts
		<link href="http://fonts.googleapis.com/css?family=Lobster" rel="stylesheet" type="text/css">
		-->
<link rel="stylesheet" type="text/css" href="css/site.css" />

<!--sweetalert cdn-->
<script
	src="https://cdn.jsdelivr.net/sweetalert/1.1.3/sweetalert.min.js"></script>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/sweetalert/1.1.3/sweetalert.css">

</head>

<body>
	<!--Menu Bar-->
	<nav class="navbar navbar-inverse" style="margin: 0px;">
		<div class="container-fluid">

			<div>
				class="navbar-header>

	    </div>
	    <!-- Collect the nav links, forms, and other content for toggling -->
	    <div class="
				collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li><a href="./index"><b>Quiz Application</b></a></li>
					<li><a href="./admin"><b>Menu</b></a></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>




	<!--Form-->
	<div class="col-md-12" id="grad">
		<div class="col-md-4"></div>
		<div class="col-md-4 vertical-center">






			<form class="col-md-12" action="admin" method="post">
			

				<h1>Add a Question</h1>
				<input type="hidden" name="editQuestion" value="${question.getId()}"/>
				<input type="hidden" name="questionTypeH" value="${question.getType()}"/>
				<br /> <label for="questionType">Select a question type</label>
				<div class="form-group">
					<select class="form-control" id="questionType" disabled>
						<option <c:if test="${question.getType()=='Multichoice'}">selected="selected"
				</c:if>value="Multichoice">Multiple Choice</option>
						<option <c:if test="${question.getType()=='Checkbox'}">selected="selected"</c:if>value="Checkbox">Checkbox</option>
						<option <c:if test="${question.getType()=='Text'}">selected="selected"</c:if>value="Text">Short Answer</option>
					</select>
				</div>


			<div class="form-group">
				<label for="questionDifficulty">Select the Difficulty</label> <select
					class="form-control" name="questionDifficulty">
					<option
						<c:if test="${question.getDifficulty()==1}">selected="selected"</c:if>
						value="1">Easy</option>
					<option
						<c:if test="${question.getDifficulty()==2}">selected="selected"</c:if>
						value="2">Medium</option>
					<option
						<c:if test="${question.getDifficulty()==3}">selected="selected"</c:if>
						value="3">Hard</option>
				</select>
			</div>

			<div id="questionDiv"></div>




			</form>
			<div class="col-md-4"></div>
		</div>

		<!--Javascript was included in this page so that you can see the forms being dynamically added via JS-->
		<script type="text/javascript">
		this.saveMulti = "";
		this.saveCheck = "";
		this.saveShort = "";
	
		function checkQuestionType(){	
			<!--Multiple Choice-->
			var mcHTML =	"<div class='form-group' name='mcq'>" +
							"<label for='multiQuestion'>Question</label>" +
							"<input type='text' class='form-control' name='multiQ' value='${question.getDescription()}'>" +
							"</div>" +
							"<div class='form-group'>" +
							"<label for='multiA'>Answer A</label>" +
							"<input type='text' class='form-control' name='multiA' value='${answerText[0]}'>" +
							"</div>" +
							"<div class='form-group'>" +
							"<label for='multiB'>Answer B</label>" +
							"<input type='text' class='form-control' name='multiB' value='${answerText[1]}'>" +
							"</div>" +
							"<div class='form-group'>" +
							"<label for='multiC'>Answer C</label>" +
							"<input type='text' class='form-control' name='multiC' value='${answerText[2]}'>" +
							"</div>" +
							"<div class='form-group'>" +
							"<label for='multiD'>Answer D</label>" +
							"<input type='text' class='form-control' name='multiD' value='${answerText[3]}'>" +
							"</div>" +
							"<div class='form-group'>" +
							"<label for='multiAnswer'>Select the correct answer</label>" +
							"<select class='form-control' name='multiAnswer'>" +
							"<option value='A' <c:if test="${correctIndex[0]==1}">selected='selected'</c:if>>A</option>" +
							"<option value='B' <c:if test="${correctIndex[1]==1}">selected='selected'</c:if>>B</option>" +
							"<option value='C' <c:if test="${correctIndex[2]==1}">selected='selected'</c:if>>C</option>" +
							"<option value='D' <c:if test="${correctIndex[3]==1}">selected='selected'</c:if>>D</option>" +
							"</select>" +
							"</div>" +
							"<button type='submit' class='btn btn-success' onClick='successMessage()'>Submit</button>";
			<!--Checkbox-->
			var cbHTML = 	"<div class='form-group'>" +
							"<label for='checkQuestion'>Question</label>" +
							"<input type='text' class='form-control' name='checkQuestion' value='${question.getDescription()}'>" +
							"</div>" +
							"<div class='form-group'>" +
							"<label for='checkA'>Answer A</label>" +
							"<input type='text' class='form-control' name='checkA' value='${answerText[0]}'>" +
							"</div>" +
							"<div class='form-group'>" +
							"<label for='checkB'>Answer B</label>" +
							"<input type='text' class='form-control' name='checkB' value='${answerText[1]}'>" +
							"</div>" +
							"<div class='form-group'>" +
							"<label for='checkC'>Answer C</label>" +
							"<input type='text' class='form-control' name='checkC' value='${answerText[2]}'>" +
							"</div>" +
							"<div class='form-group'>" +
							"<label for='checkD'>Answer D</label>" +
							"<input type='text' class='form-control' name='checkD' value='${answerText[3]}'>" +
							"<label for='checkboxAnswer'>Select the correct answers</label><br />" +
							"<input type='checkbox' value='A' name='boolA' <c:if test="${correctIndex[0]==1}">checked</c:if>>A</option><br />" +
							"<input type='checkbox' value='B' name='boolB' <c:if test="${correctIndex[1]==1}">checked</c:if>>B</option><br />" +
							"<input type='checkbox' value='C' name='boolC' <c:if test="${correctIndex[2]==1}">checked</c:if>>C</option><br />" +
							"<input type='checkbox' value='D' name='boolD' <c:if test="${correctIndex[3]==1}">checked</c:if>>D</option><br />" +
							"</div>" +
							"<button type='submit' class='btn btn-success' onClick='successMessage()'>Submit</button>";
			<!--Short Answer-->
			var saHTML = 	"<div class='form-group'>" +
							"<label for='shortQuestion'>Question Text</label>" +
							"<input type='text' class='form-control' name='shortQuestion' value='${question.getDescription()}'>" +
							"</div>" +
							"<div class='form-group'>" +
							"<label for='shortAnswer'>Answer Text</label>" +
							"<input type='text' class='form-control' name='shortAnswer' value='${answerText[0]}'>" +
							"</div>" +
							"<button type='submit' class='btn btn-success' onClick='successMessage()'>Submit</button>";
		
			<!--Multiple Choice-->
			if(document.getElementById("questionType").value == "Multichoice"){
				document.getElementById("questionDiv").innerHTML =  mcHTML;
			}<!--Checkbox-->
			else if(document.getElementById("questionType").value == "Checkbox"){	
				document.getElementById("questionDiv").innerHTML = cbHTML;
			}<!--Short Answer-->			
			else if(document.getElementById("questionType").value == "Text"){
				document.getElementById("questionDiv").innerHTML = saHTML;				
			}else{
				document.getElementById("questionDiv").innerHTML = "";		
			}
			return 0;
		};
		
		function successMessage(){
			var type = "You have successfuly added a ";
			if(document.getElementById("questionType").value == "Multichoice"){
				type += "multiple choice";
			}<!--Checkbox-->
			else if(document.getElementById("questionType").value == "Checkbox"){	
				type += "checkbox";
			}<!--Short Answer-->			
			else if(document.getElementById("questionType").value == "Text"){
				type += "short answer";				
			}
			type += " question.";
			
			 swal("Success!", type, "success");
		};
	</script>






		<!--Footer-->
		<div id="footer" class="container">
			<nav class="navbar navbar-inverse navbar-fixed-bottom">

				<div class="col-md-12">

					<div class="col-md-2"></div>

					<div class="col-md-8">
						<div class="navbar-inner text-center">
							<h5 style="color: white; margin-top: 17px">2016 - Vincent
								Lee, Frank Panico, Shawn Knowles</h5>
						</div>
					</div>

					<div class="col-md-2"></div>

				</div>
			</nav>
		</div>

		<style>
/* Sticky footer styles
		-------------------------------------------------- */
html, body {
	height: calc(100% - 50px);
}
</style>




		<!--Bootstrap Javascript placed at end of document for decreased loading times		-->

		<script
			src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
		<link rel="stylesheet"
			href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
		<link rel="stylesheet"
			href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
		<script
			src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
		<link rel="stylesheet"
			href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">

		<style>
overflow






:



 



hidden






;
</style>
<script type="text/javascript">
	window.onload=checkQuestionType;
</script>
</body>

</html>