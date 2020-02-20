<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/layout/main_head.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/common/css/login.css" />
<meta charset="UTF-8">
<title>Login Page</title>
</head>
<body class="is-preload">

	<!-- Wrapper -->
	<div id="wrapper" style="height: 1200px;">				
	
		<div id="main">
		
			<div class="inner" style="width: 50%; height: 50%;">
				<br/><br/><br/>
				
				<article style="width: 500px; margin-left: 80px;">
				
					<!-- Header -->	
					<div class="align-center">
			        	<label style="margin-bottom: 50px;">
			        		<a href="${pageContext.request.contextPath}/main/mainPage">
			        			<img src="${pageContext.request.contextPath}/resources/img/chparklandImg.png" alt="Logo">
			        		</a>
			        	</label>
			        </div>
					
					<div class="container" role="main" style="width: 500px;">
						<div class="box">
							
							<form:form role="form" modelAttribute="loginDTO" action="${pageContext.request.contextPath}/login/postLogin" method="post">
								<div class="box-body">
									<div class="form-group">
										<label for="Id" class="col-sm-5 control-label">ID</label> 
										<form:input path="id" type="text" class="form-control" placeholder="ID" />
									</div>
									<br/>
	
									<div class="form-group">
										<label for="Password" class="col-sm-5 control-label">Password</label>
										<form:input path="pw" type="password" class="form-control" placeholder="Password" />
										<br/>
										<div>
											<form:errors path="id" class="label label-danger" style="color: red;"/>
										</div>
										<div>
											<form:errors path="pw" class="label label-danger" style="color: red;"/>
										</div>
									</div>
									<br/>
									
									<!-- Error Message -->
									<c:if test="${not empty errormsg}">
										<font color="red">
											<p>${errormsg}</p>
										</font>
									</c:if>
									<hr>
									
									<!-- Remember Me -->
									<div class="form-group">
										<div class="custom-control custom-checkbox small" style="float:left; margin-top: 8px;">
											<input type="checkbox" class="custom-control-input" id="customCheck" name="customCheck"> 
											<label class="custom-control-label" for="customCheck">로그인 유지</label>
										</div>
										<div align="right">
											<button type="submit" class="btn btn-primary">Sign In</button>
										</div>
									</div>
	
								</div>
							</form:form>   
							<hr>	
	
							<div class="hr-sect"><span style="font-size: 20px; color: gray;">SNS Login</span></div>
							<br/> 
							<div style="text-align: center;">
								<a href="${googleUrl}"><img
								   src="${pageContext.request.contextPath}/resources/img/login_google.png" alt="Google Login" />
								</a> 
								<a href="${naverUrl}"><img
									src="${pageContext.request.contextPath}/resources/img/login_naver.png" alt="Naver Login" /> 
								</a> 
								<a href="${kakaoUrl}"><img 
								   src="${pageContext.request.contextPath}/resources/img/login_kakao.png" alt="Kakao Login"  />
								</a>
							</div>
							<hr>
							
							<div class="text-center" style="font-size: 14px;">
								<span>회원 정보가 기억나지 않으세요?</span> &nbsp; <a class="small" href="${pageContext.request.contextPath}/memberShip/findIdForm">아이디/비밀번호 찾기</a>
							</div>
							<div class="text-center" style="font-size: 14px;">
								<span>아직 회원이 아니신가요?</span> &nbsp; <a class="small" href="${pageContext.request.contextPath}/memberShip/memberShipJoin">회원 가입</a>
							</div>
						</div>
						<br/><br/>
					</div>
					
				</article>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/layout/main_plugins.jsp"%>
</body>
</html>