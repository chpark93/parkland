<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>      
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/views/layout/main_head.jsp"%>
<title>MemberShip Detail</title>
</head>
<body class="is-preload">
	
	<div class="container">
		<div id="main">
		
			<div class="inner" style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); height: 680px;">	       
				
				<!-- Header -->	
				<%@ include file="/WEB-INF/views/layout/header.jsp"%>
				<br/><br/>
				
				<div class="align-center">
		        	<label style="font-size:24px;">회원 정지 페이지(관리자용)</label>
		        </div>
		      
		        <div class="box" style="width:600px;">
					<div class="">
						<form:form id="suspendForm" modelAttribute="userVO" action="${pageContext.request.contextPath}/admin/userSuspend" method="post">
							
							<!-- 아이디 -->
							<input name="id" type="hidden" class="form-control" value="${user.id}" readonly />
							<c:if test="${user.member_section eq 'member'}">
								<div class="form-group">
									<label for="id">아이디</label>
									<input name="id" type="text" class="form-control" value="${user.id}" readonly />
								</div>
							</c:if>
							<br/>
							
							<!-- 이름  -->
							<div class="form-group">
								<label for="name">이름</label>
								<input name="name" type="text" class="form-control" value="${user.name}" readonly />
							</div>
							<br/>
												
							<!-- 이메일 -->
							<div class="form-group">
								<label for="email">이메일</label>
								<input name="email" type="text" class="form-control" value="${user.email}" readonly />
							</div>
							<br/>
							
							<!-- 닉네임 -->
							<div class="form-group">
							   	<label for="nickname">닉네임</label>
								<input type="text" name="nickname" class="form-control" value="${user.nickname}" readonly />
								<span id="nickNameCheck" class="label label-danger" style="color:red;">${nickNameCheck}</span>
							</div>
							<br/><hr><br/>
							
							<div class="form-group">
								<form:input path="suspend_term" type="text" id="suspend_term" name="suspend_term" value="" placeholder="정지 기한을 숫자로 적어주세요"/>
							</div>
						</form:form>
					</div>
					<br/>
					
					<div style="text-align:center;">
						<c:if test="${suspend.id eq user.id}">
							<div>현재 유저는 회원 정지 상태 입니다</div>
							<div style="color : tomato;">${suspend.suspend_term} 일 정지</div>
						</c:if> 
					</div>
					<br/>
						
					<!-- button -->
					<div style="text-align: right;">
						<button type="button" id="btnSuspend">회원 정지</button>
						<button type="button" id="btnClear">정지 해제</button>						
					</div>
					<hr>
					<div style="text-align: right;">						
						<button type="button" id="btnCancel">취소</button>
					</div>
							
				</div>
			</div>
		</div>
		<!-- Sidebar -->
		<%@ include file="/WEB-INF/views/layout/sidebar.jsp"%>	
	</div>
<%@ include file="/WEB-INF/views/layout/main_plugins.jsp"%>
</body>

<script>

//회원 정지
$(document).on('click', '#btnSuspend', function(e) {
	e.preventDefault();
	
	if(confirm('해당 회원을 정지 하시겠습니까?')) {
		$("#suspendForm").submit();
	}
});


//정지 해제(관리자)
$(document).on('click', '#btnClear', function(e) {
	e.preventDefault();
	
	var url = "${pageContext.request.contextPath}/admin/userSuspendClearAdmin";
		url = url + "?id=${user.id}";
	
	if(confirm('해당 회원 정지를 해제 하시겠습니까?')) {
		location.href = url;
	}
	
});


//취소
$(document).on('click', '#btnCancel', function(e){
	e.preventDefault();
	
	if(confirm("리스트 화면으로 이동 하시겠습니까?")) {
		location.href = "${pageContext.request.contextPath}/admin/selectUserList";	
	}
}); 

</script>

</html>