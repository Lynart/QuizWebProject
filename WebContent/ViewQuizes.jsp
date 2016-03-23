<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form class="col-md-12" action="admin" method="post">
		<table border="1">
			<c:forEach var="question" items="${questions}">
				<tr>
					<td>
						<button type="submit" name="getQuestion"
							value="${question.getId()}">
							<c:out value="${question.getId()}" />
						</button>
					</td>
					<td><c:out value="${question.getId()}" /></td>
					<td><c:out value="${question.getDifficulty()}" /></td>
					<td><c:out value="${question.getType()}" /></td>
					<td><c:out value="${question.getDescription()}" /></td>
				</tr>
			</c:forEach>
		</table>
	</form>
</body>
</html>