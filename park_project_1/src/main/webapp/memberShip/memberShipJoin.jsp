<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>      
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/layout/main_head.jsp"%>
<title>MemberShip</title>
</head>
<body class="is-preload">
	
	<!-- Wrapper -->
	<div id="wrapper" style="height: 1200px;">
	
	<div id="main" >
		<br/><br/>
		<div class="inner" style="width: 50%; height: 50%;">	       
			
			<!-- Header -->	
			<div class="align-center">
	        	<label style="margin-bottom: 50px;">
	        		<a href="${pageContext.request.contextPath}/main/mainPage">
	        			<img src="${pageContext.request.contextPath}/resources/img/chparklandImg.png" alt="Logo">
	        		</a>
	        	</label>
	        	<label style="font-size:24px;">회원 가입</label>
	        </div>
	      
	        <div class="box">
				
				<form:form name="form" id="form" class="form" role="form"  modelAttribute="memberShipVO" style="margin-left: auto; margin-right: auto;"
					action="${pageContext.request.contextPath}/memberShip/memberRegister" method="post">
						<div class="">
							
							<!-- Member Section -->
							<form:input path="member_section" id="member_section" type="hidden" value="${member_section}"/>
							
							<!-- 아이디 -->
							<div class="form-group">
								<label for="id">아이디</label>
								<form:input path="id" id="id" type="text" class="form-control" placeholder=" ID" />
		                   		<form:errors path="id" class="label label-danger text-red" style="color:red;"/>
		                   	    <span id="id_check" class="label label-danger text-red" style="color:red;">${idCheck}</span>
							</div>
							<br/>
							
							<!-- 이름  -->
							<div class="form-group">
								<label for="name">이름</label>
								<form:input path="name" id="name" type="text" class="form-control" placeholder=" Name" />
		                   		<form:errors path="name" class="label label-danger text-red" style="color:red;"/>
		                   		<span id="name_check"class="label label-danger" style="color:red;">${nameCheck}</span>
							</div>
							<br/>
							
							<!-- 비밀번호  -->
							<div class="form-group">
								<label for="password">비밀번호</label>
								<form:password path="password" id="password" class="form-control" placeholder=" Password"/>
							</div>
							<br/>
							
							<!-- 비밀번호 확인 -->
							<div class="form-group">
								<label for="">비밀번호 확인</label>
								<form:input path="passwordChk" type="password" id="passwordChk" class="form-control" placeholder=" Password" />
			      				<form:errors path="password" class="label label-danger text-red" style="color:red;" />
			      				<span id="pw_check" class="label label-danger" style="color:red;">${pwCheck}</span>
							</div>
							<br/>
							
							<!-- 이메일 -->
							<div class="form-group">
								<label for="email">이메일</label>
								<form:input path="email" id="email" type="text" class="form-control" placeholder="Ex. abcd1234@abcd.com" />
		                   		<form:errors path="email" class="label label-danger" style="color:red;" />
		                   		<span id="email_check"class="label label-danger" style="color:red;">${emailCheck}</span>
							</div>
							<br/>
							
							<!-- 닉네임 -->
							<div class="form-group">
							   	<label for="nickname">닉네임</label>
								<form:input path="nickname" type="text" id="nickname" name="nickname" class="form-control" placeholder="닉네임을 입력해주세요"/>
								<span id="nickName_check"class="label label-danger" style="color:red;">${nickNameCheck}</span>
							</div>
							<br/><br/>
							
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
			<br/><br/><br/>
		</div>
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
	
	var reg1 = RegExp(/^[a-zA-Z0-9]{4,25}$/); //아이디 체크 정규식
	var reg2 = RegExp(/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i); //이메일 정규식
	var reg3 = RegExp(/^[가-힣a-zA-Z0-9]{2,20}$/); //닉네임 체크 정규식
	var reg4 = RegExp(/^[가-힣a-zA-Z]{2,20}$/); //이름 체크 정규식
	
	
	//아이디
	$("#id").keyup(function() {
		
		if(!reg1.test($("#id").val())) {
			$("#id_check").html('아이디는 4~25자의 영문과 숫자만 가능합니다.');
		}
		else {
			$("#id_check").html('');
		}
				
		
	});
	
	//패스워드
	$("#password").keyup(function() {
		
		if($("#password").val().length < 4) {
			$("#pw_check").html('비밀번호는 4자 이상 입력해주셔야 합니다.');
		 
		}
		else if($("#password").val() != $("#passwordChk").val()){
			
			$("#pw_check").html('비밀번호가 일치 하지 않습니다.');
			
		} 
		else {
			$("#pw_check").html('');				
		}
		
	});
	
	$("#passwordChk").keyup(function() {
		
		if($("#passwordChk").val().length < 4) {
			$("#pw_check").html('비밀번호는 4자 이상 입력해주셔야 합니다.');
		 
		}
		else if($("#password").val() != $("#passwordChk").val()){
			
			$("#pw_check").html('비밀번호가 일치 하지 않습니다.');
			
		} 
		else {
			$("#pw_check").html('');				
		}
	});
	
	//이메일
	$("#email").keyup(function() {
		
		if(!reg2.test($("#email").val())) {
			$("#email_check").html('이메일 양식에 맞게 입력 해주세요.');
		}
		else {
			$("#email_check").html('');
		}
	});
	
	//닉네임
	$("#nickname").keyup(function() {
		
		if(!reg3.test($("#nickname").val())) {
			$("#nickName_check").html('닉네임은 2~20자의 영문,한글,숫자만 가능합니다.');
		}
		else {
			$("#nickName_check").html('');
		}
	});
	
	//이름
	$("#name").keyup(function() {
		
		if(!reg4.test($("#name").val())) {
			$("#name_check").html('이름은 2~20자의 영문,한글만 가능합니다.');
		}
		else {
			$("#name_check").html('');
		}
	});
	
	
}

</script>

</html>