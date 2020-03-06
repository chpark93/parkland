<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/layout/main_head.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/common/css/memberShip.css" />

<title>Find Password Form</title>
</head>
<body>

	<!-- Wrapper -->
	<div id="wrapper" style="height: 1200px;">
	
	<div id="main">
		
		<div class="inner" style="height: 50%;">
			<br/><br/><br/>
			<!-- Header -->
			<div class="align-center">
				<label style="margin-bottom: 50px;">
	        		<a href="${pageContext.request.contextPath}/main/mainPage">
	        			<img src="${pageContext.request.contextPath}/resources/img/chparklandImg.png" alt="Logo">
	        		</a>
	        	</label>
				<label style="font-size: 24px;">비밀번호 찾기</label>								
			</div>	
			<br/>
			
			<div class="box">
			
				<form:form name="form" id="form" class="user" role="form" modelAttribute="memberShipVO" action="${pageContext.request.contextPath}/memberShip/findPw" method="post">
					<div class="form-group">
						<label>Email</label>
						<form:input path="email" type="email" class="form-control form-control-user" id="email" />
					</div>
					<br/>
					<div>
						<form:errors path="email" class="label label-danger" style="color:red; margin-top:2px" />
					</div>
					<br>
					<div style="text-align: right;">
						<input type="submit" value="비밀번호 찾기" class="btn btn-primary">
						<input type="button" value="취소" class="btn btn-primary" onclick="location.href='${pageContext.request.contextPath}/main/mainPage'">
					</div>
				</form:form>
				<br>				
			</div>
		</div>
	</div>
	</div>
	<%@ include file="/WEB-INF/views/layout/main_plugins.jsp"%>
</body>
</html>