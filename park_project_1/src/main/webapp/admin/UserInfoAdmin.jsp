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
	
	<!-- Wrapper -->
	<div id="wrapper">
	
		<div id="main">
			<br/><br/><br/>
			<div class="inner" style="width: 50%; height: 50%;">	       
				
				<!-- Header -->	
				<div class="align-center">
		        	<label style="margin-bottom: 50px;">
		        		<a href="${pageContext.request.contextPath}/main/mainPage">
		        			<img src="${pageContext.request.contextPath}/resources/img/chparklandImg.png" alt="Logo">
		        		</a>
		        	</label>
		        </div>
		        
				<div class="align-center">
		        	<label style="font-size:24px;">회원 정보(관리자용)</label>
		        </div>
		      
		        <div class="box">
					<div class="">
						
						<!-- 아이디 -->
						<c:if test="${user.member_section eq 'member'}">
							<div class="form-group">
								<label for="id">아이디</label>
								<input id="id" type="text" class="form-control" value="${user.id}" readonly />
							</div>
						</c:if>
						<br/>
						
						<!-- 이름  -->
						<div class="form-group">
							<label for="name">이름</label>
							<input id="name" type="text" class="form-control" value="${user.name}" readonly />
						</div>
						<br/>
											
						<!-- 이메일 -->
						<div class="form-group">
							<label for="email">이메일</label>
							<input id="email" type="text" class="form-control" value="${user.email}" readonly />
						</div>
						<br/>
						
						<!-- 닉네임 -->
						<div class="form-group">
						   	<label for="nickname">닉네임</label>
							<input type="text" id="nickname" name="nickname" class="form-control" value="${user.nickname}" readonly />
							<span id="nickNameCheck" class="label label-danger" style="color:red;">${nickNameCheck}</span>
						</div>
						<br/><hr>
					</div>
					
					<div style="text-align:center;">
						<c:if test="${suspend.id eq user.id}">
							<div>현재 유저는 회원 정지 상태 입니다</div>
							<div style="color : tomato;">${suspend.suspend_term} 일 정지</div>
						</c:if> 
					</div>
					<br/><br/>
						
					<!-- button -->
					<div style="text-align: right">
						<button type="button" id="btnEject">강제 탈퇴</button>
						<button type="button" id="btnSuspend">회원 정지 페이지</button>					
					</div>
					<hr>
					<div style="text-align: right;">
						<button type="button" id="btnMessage" onclick="messageModal()">쪽지 보내기</button>
						<button type="button" id="btnCancel">취소</button>
					</div>
							
				</div>
			</div>
		</div>
		<br/><br/><br/>
		<!-- Sidebar -->
		<%@ include file="/WEB-INF/views/layout/sidebar.jsp"%>	
		<%@ include file="/WEB-INF/views/layout/messageModal.jsp"%>
	</div>
<%@ include file="/WEB-INF/views/layout/main_plugins.jsp"%>
</body>

<script>


//강제 탈퇴
$(document).on('click', '#btnEject', function(e) {
	e.preventDefault();
	
	var url = "${pageContext.request.contextPath}/admin/ejectUserAdmin";
		url = url + "?id=${user.id}";
	
	if(confirm("회원을 정말로 탈퇴시키겠습니까?")) {
		location.href = url;
	}
})

//회원 정지 페이지
$(document).on('click', '#btnSuspend', function(e) {
	e.preventDefault();
	
	var url ="${pageContext.request.contextPath}/admin/userSuspendPage";
		url = url + "?id=${user.id}";  
	
	location.href = url;	
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