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
<body>

	<!-- Wrapper -->
	<div id="wrapper" style="height: 1200px;">
	 			
	<div id="main">
		<br/><br/>
		
		<div class="inner" style="width: 50%; height: 50%;">	       
			
			<!-- Header -->	
			<div class="align-center">
	        	<label style="margin-bottom: 50px;">
	        		<a href="${pageContext.request.contextPath}/main/mainPage">
	        			<img src="${pageContext.request.contextPath}/resources/img/chparklandImg.png" alt="Logo">
	        		</a>
	        	</label>
	        	<label style="font-size:24px;">회원 정보</label>
	        </div>
	      
	        <div class="box">
				
				<form:form name="form" id="form" class="form" role="form" modelAttribute="userVO" style="margin-left: auto; margin-right: auto;"
					action="${pageContext.request.contextPath}/memberShip/updateUserInfo" method="post">
						<div class="">
							<input type="hidden" name="id" value="${user.id}"/>
							<input type="hidden" name="email" value="${user.email}"/>
							<input type="hidden" name="name" value="${user.name}"/>
							<input type="hidden" id="birth" name="birth" value="${user.birth}">
							
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
								<form:input path="nickname" type="text" id="nickname" name="nickname" class="form-control" value="${user.nickname}" placeholder="닉네임을 입력해주세요"/>
								<span id="nickNameCheck" class="label label-danger" style="color:red;">${nickNameCheck}</span>
							</div>
							<br/><hr>
							
							
							<!-- 추가 정보  -->
							<div class="align-center">
					        	<label style="font-size:20px;">추가 정보</label>
					        </div>
					        
							<!-- 생년월일 -->
							<div>
								<label for="birth">생년월일</label>
								<div class="row">
									<div class="col-4 col-12-small">
										<span> 
											<input type="text" id="year" placeholder="년(4자)" aria-label="년(4자)" class="int" maxlength="4"  value="${birth.substring(0, 4)}">
										</span>
									</div>
									
									<div class="col-4 col-12-small">
										<span>
											<select id="month" aria-label="월">
												<option value="">월</option>
												<option value="01" <c:if test="${birth.substring(4,6) == '01'}"> selected = 'selected' </c:if> >01</option>
												<option value="02" <c:if test="${birth.substring(4,6) == '02'}"> selected = 'selected' </c:if> >02</option>  
												<option value="03" <c:if test="${birth.substring(4,6) == '03'}"> selected = 'selected' </c:if> >03</option>
												<option value="04" <c:if test="${birth.substring(4,6) == '04'}"> selected = 'selected' </c:if> >04</option>
												<option value="05" <c:if test="${birth.substring(4,6) == '05'}"> selected = 'selected' </c:if> >05</option>
												<option value="06" <c:if test="${birth.substring(4,6) == '06'}"> selected = 'selected' </c:if> >06</option>
												<option value="07" <c:if test="${birth.substring(4,6) == '07'}"> selected = 'selected' </c:if> >07</option>
												<option value="08" <c:if test="${birth.substring(4,6) == '08'}"> selected = 'selected' </c:if> >08</option>
												<option value="09" <c:if test="${birth.substring(4,6) == '09'}"> selected = 'selected' </c:if> >09</option>
												<option value="10" <c:if test="${birth.substring(4,6) == '10'}"> selected = 'selected' </c:if> >10</option>
												<option value="11" <c:if test="${birth.substring(4,6) == '11'}"> selected = 'selected' </c:if> >11</option>
												<option value="12" <c:if test="${birth.substring(4,6) == '12'}"> selected = 'selected' </c:if> >12</option>
											</select>
										</span>
									</div>
									
									<div class="col-4 col-12-small">
										<span>
											<input type="text" id="day" placeholder="일(2자)" aria-label="일(2자)" class="int" maxlength="2" value="${birth.substring(6, 8)}">
											<label for="day"></label>
										</span>
									</div>
									<span class="error" id="birthMsg" style="display:none; color:red;"></span>
								</div>
							</div>
							<br/>
							
							<!-- 성별 -->
							<label for="sex">성별</label>
							<div class="form-group row">
								<div class="col-6 col-12-xsmall">
									<input type="radio" id="male" name="sex" value="male" 
									<c:if test="${user.sex eq 'male'}">checked</c:if> />
									<label for="male">남성</label>
								</div>
								<div class="col-6 col-12-xsmall">
									<input type="radio" id="female" name="sex" value="female"
									<c:if test="${user.sex eq 'female'}">checked</c:if> />
									<label for="female">여성</label>
								</div>
							</div>
							<br/>
							
							<!-- 휴대폰 번호 -->
							<div class="form-group">
							   	<label for="mobile">휴대 전화</label>
								<form:input path="mobile" type="text" id="mobile" name="mobile" class="form-control" value="${user.mobile}" placeholder=" - 를 제외하고 번호만 입력해주세요"/>
								<form:errors path="mobile" class="label label-danger text-red" style="color:red;"/>
							</div>		
							<br/>
							
						</div>
						
					<!-- button -->
					<div class="" align="right">	
						<c:if test="${user.member_section eq 'member' }">
							<button type="button" id="btnModifyPw" class="btn btn-primary" style="float:left; margin-right: 5px;">비밀번호 변경</button>						
						</c:if>
						<button type="button" id="btnWithdraw" class="btn btn-primary" style="float:left;">회원 탈퇴</button>
						<button type="button" id="btnSubmit" class="btn btn-primary">회원 정보 수정</button>
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
var birthFlag = false;

$(document).on('click', '#btnSubmit', function(e){
	e.preventDefault();
	
	if(birthFlag == true) {
		$("#form").submit();
		return true;
	}
});

//취소
$(document).on('click', '#btnCancel', function(e){
	e.preventDefault();
	
	if(confirm("메인화면으로 이동 하시겠습니까?")) {
		location.href = "${pageContext.request.contextPath}/main/mainPage";	
	}
});

//비밀번호 변경
$(document).on('click', '#btnModifyPw', function(e) {
	e.preventDefault();
	
	location.href = "${pageContext.request.contextPath}/memberShip/modifyPwPage";
});

//회원 탈퇴
$(document).on('click', '#btnWithdraw', function(e) {
	e.preventDefault();
	
	location.href = "${pageContext.request.contextPath}/memberShip/deleteMemberShipPage";
});

$(document).ready(function(){
	
	//생년월일
	if($("#year").val() != null) {
		checkBirth();
	}
	$("#year").blur(function(){
		checkBirth();
	});
	$("#month").change(function(){
		checkBirth();
	});
	$("#day").blur(function(){
		checkBirth();
	});
	

});

function checkBirth() {
	var birth;
	
	var year = $("#year").val();
	var month = $("#month option:selected").val();
	var day = $("#day").val();
	
	var birthMsg = $("#birthMsg");
	
	var byear = $("#year");
	var bmonth = $("#month");
	var bday = $("#day");

	//월,일 : 한자리 입력시 0 채움
	if(month.length == 1) {
		month = "0" + month;
	}
	if(day.lenth == 1) {
		day = "0" + day;
	}
	
	//생년월일 유효성 검사
	if(year == "" && month == "" && day == "") {
		errorMsg(birthMsg, "년도 부분 4자리를 제대로 입력해주세요.");
		isSubmit(byear);
		return false;
	}
	
	//년도
	if(year == "") {
		errorMsg(birthMsg, "년도 부분 4자리를 제대로 입력해주세요.");
		isSubmit(byear);
		return false;
	}
	if(year.length != 4) {
		errorMsg(birthMsg, "년도 부분 4자리를 제대로 입력해주세요.");
		isSubmit(byear);
		return false;
	}
	//월
	if(month == "") {
		errorMsg(birthMsg, "월 부분을 제대로 입력해주세요.");
		isSubmit(bmonth);
		return false;
	}	
	//일
	if(day == "") {
		errorMsg(birthMsg, "일 부분을 제대로 입력해주세요.");
		isSubmit(bday);
		return false;
	}
	if(day.length != 2) {
		errorMsg(birthMsg, "일 부분 2자리를 제대로 입력해주세요.");
		isSubmit(bday);
		return false;
	}
	
	birth = year + month + day;
	if(!isValidDate(birth)) {
		errorMsg(birthMsg, "생년월일을 확인해주세요.");
		isSubmit(byear);
		return false;
	}
	$("#birth").val(birth);
	
	var age = calAge(birth);
    
	if (age < 0) {
    	errorMsg(birthMsg, "생년월일을 확인해주세요.");
    	isSubmit(byear);
    	return false;
    } 
    else if (age > 100) {
    	errorMsg(birthMsg, "생년월일을 확인해주세요.");
    	isSubmit(byear);
    	return false;
    } 
    else {
        hideMsg(birthMsg);
      	birthFlag = true;
        return true;
    }
	
	birthFlag = true;
	return true;
	
}

function errorMsg(obj, msg) {
	obj.attr("class", "label label-danger");
	obj.html(msg);
	obj.show();
}

function hideMsg(obj) {
	obj.hide();
}

//나이 계산
function calAge(birth) {
    var date = new Date();
    
    var year = date.getFullYear();
    var month = (date.getMonth() + 1);
    var day = date.getDate();
    
    if (month < 10)
        month = '0' + month;
    
    if (day < 10)
        day = '0' + day;
    
    var monthDay = month + '' + day;

    birth = birth.replace('-', '').replace('-', '');
    
    var birthyear = birth.substr(0, 4);
    var birthmd = birth.substr(4, 4);
    var age = monthDay < birthmd ? year - birthyear - 1 : year - birthyear;
    
    return age;
}

function isValidDate(param) {
    try {
        param = param.replace(/-/g, '');

        //자리수가 맞지 않음 
        if (isNaN(param) || param.length != 8) {
            return false;
        }

        var year = Number(param.substring(0, 4));
        var month = Number(param.substring(4, 6));
        var day = Number(param.substring(6, 8));

        if (month < 1 || month > 12) {
            return false;
        }

        var maxDaysInMonth = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];
        var maxDay = maxDaysInMonth[month - 1];

        //윤년
        if (month == 2 && (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)) {
            maxDay = 29;
        }

        if (day <= 0 || day > maxDay) {
            return false;
        }
        return true;

    } catch (errors) {
        return false;
    };
}

function isSubmit(obj) {
	if(birthFlag) {
		birthFlag = false;
		obj.focus();
	}
	
}


//자동 하이픈(휴대전화)
$(document).on("keyup", "#mobile", function() { 
	$(this).val( 
		
		$(this).val().replace(/[^0-9]/g, "").replace(/(^02|^0505|^1[0-9]{3}|^0[0-9]{2})([0-9]+)?([0-9]{4})/,"$1-$2-$3").replace("--", "-") 
		
	); 
	
});
 

</script>

</html>