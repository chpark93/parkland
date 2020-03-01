<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>      
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/layout/main_head.jsp"%>
<!-- custom css -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/common/css/memberShip.css" />

<title>MemberShip</title>
</head>
<body>
	
	<!-- Wrapper -->
	<div id="wrapper" style="height: 1200px;">
	
	<div id="main">
		<br/><br/>
		
		<div class="inner" style="height: 50%;">	       
			
			<!-- Header -->	
			<div class="align-center">
	        	<label style="margin-bottom: 50px;">
	        		<a href="${pageContext.request.contextPath}/main/mainPage">
	        			<img src="${pageContext.request.contextPath}/resources/img/chparklandImg.png" alt="Logo">
	        		</a>
	        	</label>
	        	<label style="font-size:24px;">회원 가입(SNS)</label>
	        </div>
	      
	        <div class="box" >
	
				<form:form id="joinForm" modelAttribute="memberShipSnsVO" action="${pageContext.request.contextPath}/memberShip/memberRegisterSns" method="post">
					<div class="">
						
						<!-- Member Section -->
						<form:input path="member_section" id="member_section" type="hidden" value="${member_section}"/>
						
						<!-- 아이디 -->
						<div class="form-group">
							<form:input path="id" id="id" type="hidden" class="form-control" value="${snsUser.id}" />
	                   	    <span id="id_check" class="label label-danger text-red" style="color:red;">${idCheck}</span>
						</div>
						
						<!-- 이름  -->
						<c:if test="${snsUser.name eq null || snsUser.name eq '' }">
							<div class="form-group">
								<form:input path="name" id="name" type="text" class="form-control" value="${snsUser.name}"/>
		                   		<span id="name_check" class="label label-danger text-red" style="color:red;">${nameCheck}</span>
		                   	</div>
						</c:if>
						<c:if test="${snsUser.name ne null && snsUser.name ne '' }">
							<div class="form-group">
								<form:input path="name" id="name" type="hidden" class="form-control" value="${snsUser.name}"/>
		                   	</div>
						</c:if>
						
						<!-- 이메일 -->
						<c:if test="${snsUser.email eq null || snsUser.email eq '' }">
							<div class="form-group">
								<label for="email">Email</label>
								<form:input path="email" id="email" type="text" class="form-control" value="${snsUser.email}" placeholder="이메일을 입력해주세요"/>
		                   	   	<form:errors path="email" class="label label-danger" style="color:red;" />
		                   	    <span id="email_check" class="label label-danger text-red" style="color:red;">${emailCheck}</span>
							</div>
							<br/>
						</c:if>
						<c:if test="${snsUser.email ne null && snsUser.email ne '' }">
							<div class="form-group">
								<form:input path="email" id="email" type="hidden" class="form-control" value="${snsUser.email}" />
		                   	    <span id="email_check" class="label label-danger text-red" style="color:red;">${emailCheck}</span>
							</div>
						</c:if>
						
						<!-- 닉네임 -->
						<div class="form-group">
						   	<label for="nickname">NickName</label>
							<form:input path="nickname" type="text" id="nickname" name="nickname" class="form-control" value="${snsUser.nickname}" placeholder="닉네임을 입력해주세요" />
							<form:errors path="nickname" class="label label-danger" style="color:red;" />
							<span id="nickName_check"class="label label-danger" style="color:red;">${nickNameCheck}</span>
						</div>
						<br/>
						
						<!-- 체크 박스 -->
						<div class="form-group">
							<div class="custom-control custom-checkbox small">
								<input type="checkbox" class="custom-control-input" id="checkMemberShip" name="checkMemberShip" checked="checked"> 
								<label class="custom-control-label" for="checkMemberShip">위의 정보로 가입을 완료합니다.</label>
							</div>
						</div>					
					</div>
					
					<!-- button -->
					<div class="" align="right">
						<button type="button" id="btnSubmit" class="btn btn-primary">회원 가입</button>
						<button type="button" id="btnCancel" class="btn btn-primary">취소</button>
					</div>		
				</form:form>
			</div>
		</div>
		<br/><br/><br/>
		
	</div>	
	</div>
<%@ include file="/WEB-INF/views/layout/main_plugins.jsp"%>	
</body>

<script>

//회원가입 submit
$(document).on('click', '#btnSubmit', function(e){
	e.preventDefault();
	
    if($("#checkMemberShip").is(":checked")) {    
        
		$("#form").submit();
    }
    else {
		alert('가입 완료에 체크 해주세요'); 
		return false;
    }
	
});

//회원가입 취소
$(document).on('click', '#btnCancel', function(e){
	e.preventDefault();
	
	if(confirm("회원가입을 취소 하시겠습니까?")) {
		location.href = "${pageContext.request.contextPath}/main/mainPage";	
	}
});
</script>

<script>

$(document).ready(function(){
	validate();
});

//유효성 검사
function validate() {

	var reg1 = RegExp(/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i); //이메일 정규식
	var reg2 = RegExp(/^[가-힣a-zA-Z0-9]{2,20}$/); //닉네임 체크 정규식
	var reg3 = RegExp(/^[가-힣a-zA-Z]{2,20}$/); //이름 체크 정규식
	
	
	//이메일
	$("#email").keyup(function() {
		
		if(!reg1.test($("#email").val())) {
			$("#email_check").html('이메일 양식에 맞게 입력 해주세요.');
		}
		else {
			$("#email_check").html('');
		}
	});

	//닉네임
	$("#nickname").keyup(function() {
		
		if(!reg2.test($("#nickname").val())) {
			$("#nickName_check").html('닉네임은 2~20자의 영문,한글,숫자만 가능합니다.');
		}
		else {
			$("#nickName_check").html('');
		}
	});
	
	//이름
	$("#name").keyup(function() {		
		
		if(!reg3.test($("#name").val())) {
			$("#name_check").html('이름은 2~20자의 영문,한글만 가능합니다.');
		}
		else {
			$("#name_check").html('');
		}
	});
	
	
	
}
</script>

</html>