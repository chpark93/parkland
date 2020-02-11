<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 

<!DOCTYPE html>
<html>
<head> 
<%@ include file="/WEB-INF/views/layout/indexView.jsp"%>  
<title>Index</title>
<script>
	//게시판
	$(document).on('click', '#btnBoard', function(e){
		e.preventDefault();
	
		//location.href = "${pageContext.request.contextPath}/board/getBoardList?bg_no=<c:out value='${board.bg_no}'/>";
		
		var url = "${pageContext.request.contextPath}/board/getBoardList";
		url = url + "?bg_no=<c:url value='${pagination.bg_no}'/>";
		location.href = url;
	
	});
	
	//메뉴
	$(document).on('click', '#btnMenu', function(e){
		e.preventDefault();
		
		location.href = "${pageContext.request.contextPath}/menu/getMenuList";
	});
	
	//회원가입(삭제 예정)
 	$(document).on('click', '#btnMemberShip', function(e){
		e.preventDefault();
		
		location.href = "${pageContext.request.contextPath}/memberShip/memberShipJoin";
	});
	
	//회원 리스트(삭제 예정)
	$(document).on('click', '#btnUserList', function(e){
		e.preventDefault();
		
		location.href = "${pageContext.request.contextPath}/admin/selectUserList";
	});
	
	//마이 페이지(삭제 예정)
	$(document).on('click', '#btnMyPage', function(e){
		e.preventDefault();
		
		location.href = "${pageContext.request.contextPath}/memberShip/modifyPage";
	});
	
	//비밀번호 변경(삭제 예정)
 	$(document).on('click', '#btnModPw', function(e){
		e.preventDefault();
		
		location.href = "${pageContext.request.contextPath}/memberShip/modifyPwPage";
	});
	
	//메인 페이지(삭제예정)
	$(document).on('click', '#btnMain', function(e){
		e.preventDefault();
		
		location.href = "${pageContext.request.contextPath}/main/mainPage";
	});
	
	//파일 업로드(삭제예정)
	$(document).on('click', '#btnFile', function(e){
		e.preventDefault();
		
		location.href = "${pageContext.request.contextPath}/fileUtil/upload";
	});
	
	//채팅(삭제 예정)
	$(document).on('click', '#btnChat', function(e){
		e.preventDefault();
		
		location.href = "${pageContext.request.contextPath}/chat/moveAllChatRoom";
	});
	
	//로그 아웃
	$(document).on('click', '#btnLogout', function(e){
		e.preventDefault();
		
		location.href = "${pageContext.request.contextPath}/login/logout";
	});
	
	//마이페이지
	$(document).on('click', '#btnUserInfo', function(e){
		e.preventDefault();
		
		location.href = "${pageContext.request.contextPath}/mypage/getBoardListPagingFromId";
	});
	
	$(document).on('click', '#btnUserInfo2', function(e){
		e.preventDefault();
		
		location.href = "${pageContext.request.contextPath}/mypage/getReplyListPagingFromId";
	});
	
	//회원 탈퇴
	$(document).on('click', '#btnWithdraw', function(e){
		e.preventDefault();
		
		location.href = "${pageContext.request.contextPath}/memberShip/deleteMemberShipPage";
	});
	
	//회원 프로필
	$(document).on('click', '#btnProfile', function(e){
		e.preventDefault();
		
		location.href = "${pageContext.request.contextPath}/memberShip/updateUserProfilePage";
	});
	
	//메세지
	$(document).on('click', '#btnMessage', function(e){
		e.preventDefault();
		
		location.href = "${pageContext.request.contextPath}/message/messageForm";
	});
	
	//추천글
	$(document).on('click', '#btnRecommendBoard', function(e){
		e.preventDefault();
		
		location.href = "${pageContext.request.contextPath}/board/recommendBoardList";
	});
	
	//인기글
	$(document).on('click', '#btnViewBestBoard', function(e){
		e.preventDefault();
		
		location.href = "${pageContext.request.contextPath}/board/getViewBestBoardList";
	});
	
</script>

</head>
<body>
	
	<div class="container-fluid" style="margin-top:80px">
		<!-- 로그인 -->
		<c:if test="${loginUser.id eq null}">
			<h5><a href='<c:url value="/login/login"/>' >Login</a></h5>
		</c:if>
		
		<!-- 로그아웃 -->
		<c:if test="${loginUser.id ne null}">
				<button type="button" class="btn btn-dark btn-sm" id="btnLogout">Logout</button>
		</c:if>
		
		<br><br>
		<button type="button" class="btn btn-sm btn-primary" id="btnBoard" >게시판</button>
		<button type="button" class="btn btn-sm btn-primary" id="btnMenu">메뉴</button>
		<button type="button" class="btn btn-sm btn-primary" id="btnMemberShip">회원가입</button>
		<button type="button" class="btn btn-sm btn-primary" id="btnUserList">회원 리스트</button>
		<button type="button" class="btn btn-sm btn-primary" id="btnMyPage">회원정보 수정</button>
		<button type="button" class="btn btn-sm btn-primary" id="btnModPw">비밀번호 변경</button>
		<button type="button" class="btn btn-sm btn-primary" id="btnMain">메인</button>
		<button type="button" class="btn btn-sm btn-primary" id="btnFile">파일 업로드</button>
		<button type="button" class="btn btn-sm btn-primary" id="btnChat">채팅</button>
		<button type="button" class="btn btn-sm btn-primary" id="btnUserInfo">마이페이지</button>
		<button type="button" class="btn btn-sm btn-primary" id="btnUserInfo2">마이페이지2</button>
		<button type="button" class="btn btn-sm btn-primary" id="btnWithdraw">회원 탈퇴</button>
		<button type="button" class="btn btn-sm btn-primary" id="btnProfile">회원 프로필 이미지</button>
		<button type="button" class="btn btn-sm btn-primary" id="btnMessage">메세지 보내기</button>
		<button type="button" class="btn btn-sm btn-primary" id="btnRecommendBoard">추천글</button>
		<button type="button" class="btn btn-sm btn-primary" id="btnViewBestBoard">인기글</button>
	
	</div>	
</body>
</html>