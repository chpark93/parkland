<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Access Denied</title>
</head>
<body>
	<br><br>
	<div class="container text-center">
		<h1>Access Denied</h1>
	</div>
	<br><br>
	
	<div class="container text-center">
		<h5>접근 권한이 없습니다.</h5>
	</div>
	<br><br>
	
	<div class="container text-center">
		<a href='<c:url value="/"/>' class="text-dark"><i class="fas fa-undo">돌아가기</i></a>
	</div>

</body>
</html>