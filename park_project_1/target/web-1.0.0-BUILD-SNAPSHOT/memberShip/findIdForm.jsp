<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/layout/main_head.jsp"%>
<title>Find ID</title>
</head>
<body class="bg-gradient-primary">
	<div class="container">
		<div class="inner" style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); height: 680px;">
			
			<!-- Header -->
			<div class="form-header align-center">
				<label style="font-size:20px;"><a href="${pageContext.request.contextPath}/main/mainPage" >#ChPark</a></label>
				<label style="font-size: 24px;">아이디(ID) 찾기</label>								
			</div>	
			<br/>
			
			<div class="box" style="width:600px;">
				<div style="text-align: right;">
					<input type="button" value="비밀번호 찾기" class="btn btn-primary" onclick="location.href='${pageContext.request.contextPath}/memberShip/findPwForm'"/>
				</div>
				
				<form:form name="form" id="form" class="user" role="form" modelAttribute="memberShipVO" action="${pageContext.request.contextPath}/memberShip/findId" method="post">
					<div class="form-group">
						<label>Email</label>
						<form:input path="email" type="email" class="form-control form-control-user" id="email" />													
					</div>
					<br/><br/>
					
					<div style="text-align: right;">
						<input type="submit" value="찾기" class="btn btn-primary">
						<input type="button" value="취소" class="btn btn-primary" onclick="location.href='${pageContext.request.contextPath}/main/mainPage'">
					</div>
				</form:form>
				<br>
				
			</div>
		</div>
	</div>

	<%@ include file="/WEB-INF/views/layout/main_plugins.jsp"%>
</body>
</html>