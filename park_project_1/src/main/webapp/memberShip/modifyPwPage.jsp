<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/layout/main_head.jsp"%>
<meta charset="UTF-8">
<title>Modify Page</title>
</head>
<body>

	<!-- Wrapper -->
	<div id="wrapper" style="height: 1200px;">
	
	<div id="main">
	
		<div class="inner" style="width: 50%; height: 50%;">
			<br/><br/><br/>
			<!-- Header -->
			<div class="align-center">
				<label style="margin-bottom: 50px;">
	        		<a href="${pageContext.request.contextPath}/main/mainPage">
	        			<img src="${pageContext.request.contextPath}/resources/img/chparklandImg.png" alt="Logo">
	        		</a>
	        	</label>
				<label style="font-size: 24px;">비밀번호(PW) 변경</label>
			</div>

			<div class="box">

				<form:form name="form" id="form" class="user" role="form"
					modelAttribute="memberShipVO"
					action="${pageContext.request.contextPath}/memberShip/updateUserPw"
					method="post">
					<div class="form-group">
						<form:input path="id" type="hidden" value="${user.id}" />
					</div>
					
					<div class="form-group">
						<form:label path="oldPassword" class="col-sm-5 control-label">Password</form:label>
						<form:input path="oldPassword" type="password" class="form-control" id="oldPassword" placeholder="기존 비밀번호를 입력해주세요" />
					</div>
					<br/>
					<div>
						<form:errors path="oldPassword" class="label label-danger" style="color:red;" />
					</div>
					<hr>
					
					<div class="form-group">
						<form:label path="password" class="col-sm-5 control-label">New Password</form:label>
						<form:input path="password" type="password" class="form-control" id="password" placeholder="새로운 비밀번호를 입력해주세요" />
					</div>
					<br/>
					
					<div class="form-group">
						<form:label path="passwordChk" class="col-sm-5 control-label">Confirm</form:label>
						<form:input path="passwordChk" type="password" class="form-control" id="passwordChk" placeholder="비밀번호를 재입력해주세요" />
					</div>
					<br/>
					<div>
						<form:errors path="password" class="label label-danger" style="color:red;" />
					</div>
					<br>
					<div align="right">
						<button type="submit" class="btn">비밀번호 변경</button>
						<button type="button" id="btnCancel" class="btn">취소</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
	</div>
	<%@ include file="/WEB-INF/views/layout/main_plugins.jsp"%>
</body>

<script>

//취소
$(document).on('click', '#btnCancel', function(e){
	e.preventDefault();
	
	if(confirm("메인화면으로 이동 하시겠습니까?")) {
		location.href = "${pageContext.request.contextPath}/main/mainPage";	
	}
});


</script>

</html>