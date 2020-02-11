<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>         
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/views/layout/main_head.jsp"%>
<title>Withdraw MemberShip</title>
</head>
<body>
	<div class="container" >
		<div class="inner" style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); height: 680px;">	       
			<!-- Header -->	
			<div class="form-header align-center">
	        	<label style="font-size:20px;"><a href="${pageContext.request.contextPath}/initPage/main.jsp">#ChPark</a></label>
	        	<label style="font-size:24px;">회원 탈퇴</label>
	        </div>
	      
	        <div class="box" style="width:600px;">
				
				<form:form name="form" id="form" class="form" role="form"  modelAttribute="loginDTO" style="margin-left: auto; margin-right: auto;"
					action="${pageContext.request.contextPath}/memberShip/deleteMemberShip" method="post">
					<div class="">
						<br/><br/>
						<!-- 아이디 -->	
						<input id="id" name="id" type="hidden" class="form-control" value="${user.id}"/>
						
						<!-- 비밀번호  -->
						<div class="form-group">
							<label for="password">비밀번호</label>
							<form:input path="pw" id="password" type="password" class="form-control" placeholder=" Password"/>
							<br/>
							<form:errors path="pw" class="label label-danger text-red" style="color:red;" />
		      				<span class="label label-danger" style="color:red;">${pwCheck}</span>
						</div>
						<br/><br/><br/>
						
						<!-- 회원 탈퇴 확인 입력 창 -->
						<div class="form-group">
							<label for="withdrawCheck">아래 입력창의 글자를 동일하게 적어주세요 </label>
							<input id="withdrawCheck" type="text" class="form-control" placeholder="회원 탈퇴를 하겠습니다"/>
						</div>
					</div>
					<br/>	
					<!-- button -->
					<div class="" align="right">
						<button type="button" id="btnSubmit" class="btn btn-primary">회원 탈퇴</button>
						<button type="button" id="btnCancel" class="btn btn-primary">취소</button>
					</div>		
				</form:form>
			</div>
		</div>
	</div>
<%@ include file="/WEB-INF/views/layout/main_plugins.jsp"%>
</body>

<script>

//회원 탈퇴
$(document).on('click', '#btnSubmit', function(e){
	e.preventDefault();
	
	if($("#password").val() == ""){
		alert("비밀번호를 입력해주세요.");
		$("#password").focus();
		return false;
	}
	
	
	$.ajax({
		url : "${pageContext.request.contextPath}/memberShip/passwordCheck",
		type : "POST",
		dataType : "json",
		data : $("#form").serializeArray(),
		success : function(data) {
			
			if(data == true) {
				if(confirm("회원 탈퇴를 하시겠습니까?")) {	
					$("#form").submit();
				}
				
				alert('탈퇴 완료되었습니다. 그 동안 감사했습니다.');
			}
			else {
				alert('비밀번호를 다시 입력해주세요.');
				return;
			}
		}
	});

});

//취소
$(document).on('click', '#btnCancel', function(e){
	e.preventDefault();
	
	if(confirm("회원 탈퇴를 취소 하시겠습니까?")) {
		location.href = "${pageContext.request.contextPath}/initPage/main.jsp";	
	}
});
</script>


</html>