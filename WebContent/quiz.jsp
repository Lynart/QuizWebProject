<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz Time!</title>
</head>
<body>
	Question:
	<br /> ${question.getDescription()}
	<form action="user" method="post">
		<input type="hidden" name="quizId" value="${quizId}" /> <input
			type="hidden" name="questionIndex" value="${questionIndex}" /> <input
			type="hidden" name="questionType" value="${question.getType()}" />
		<c:if test="${question.getType()=='Multichoice'}">
			<input type="radio" name="userAnswer" value="${answerText[0]}"> ${answerText[0]}<br />
			<input type="radio" name="userAnswer" value="${answerText[1]}"> ${answerText[1]}<br />
			<input type="radio" name="userAnswer" value="${answerText[2]}"> ${answerText[2]}<br />
			<input type="radio" name="userAnswer" value="${answerText[3]}"> ${answerText[3]}<br />
		</c:if>

		<c:if test="${question.getType()=='Checkbox'}">
			<input type="checkbox" name="userAnswerA" value="${answerText[0]}"> ${answerText[0]}<br />
			<input type="checkbox" name="userAnswerB" value="${answerText[1]}"> ${answerText[1]}<br />
			<input type="checkbox" name="userAnswerC" value="${answerText[2]}"> ${answerText[2]}<br />
			<input type="checkbox" name="userAnswerD" value="${answerText[3]}"> ${answerText[3]}<br />
		</c:if>

		<c:if test="${question.getType()=='Text'}">
			<input type='text' name='userAnswer'>
		</c:if>
		<input type="submit" value="Next">
	</form>
</body>
</html>

<!--
question
answerText
correctIndex
questionIndex
 -->