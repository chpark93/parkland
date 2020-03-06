<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/layout/main_head.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/common/css/memberShip.css" />

<title>Find ID Form</title>
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
				<label style="font-size: 24px;">아이디 찾기</label>								
			</div>	
			<br/>
			
			<div class="box">
				<div style="text-align: right;">
					<input type="button" value="비밀번호 찾기" class="btn btn-primary" onclick="location.href='${pageContext.request.contextPath}/memberShip/findPwForm'"/>
				</div>
				
				<form:form name="form" id="form" class="user" role="form" modelAttribute="memberShipVO" action="${pageContext.request.contextPath}/login/login" method="get">
					
					<c:choose>
						<c:when test="${memberShipVO.password eq null}">
							<div class="form-group">
								<label>ID</label>
								<form:input path="id" type="text" value="SNS으로 가입한 회원 입니다." class="form-control form-control-user" readonly="true"/>	
							</div>
						</c:when>
						<c:otherwise>
							<div class="form-group">
								<label>ID</label>
								<form:input path="id" type="text" value="${memberShipVO.id}" class="form-control form-control-user" readonly="true"/>	
							</div>	
						</c:otherwise>
					</c:choose>
					<br/><br/>
					
					<div style="text-align: right;">
						<button type="submit" class="btn btn-primary">로그인</button>
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