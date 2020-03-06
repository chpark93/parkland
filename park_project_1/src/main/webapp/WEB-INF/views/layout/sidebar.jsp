<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<div id="sidebar">
	<div class="inner">
		<br/><br/>
		
		<!--Google Search -->  
	    <form id="search-google-form" name="searchMain" class="nav-sidebar-form" action="https://www.google.com/search">
	        <div class="input-group" style="display: inline-block;">
	            <input class="gcse-search" type="text" name="q" class="form-control" placeholder="Google 검색" />
				<input type="hidden" name="qt" class="gcse-search" />
				<script async src="https://cse.google.com/cse.js?cx=006004568803175385893:eomivzcjiih"></script>
	        </div>
	        &nbsp;&nbsp;
            <span >
                <a href="javascript:searchMain.submit()" type="submit"><i class="fa fa-search" style="font-size: 16px;"></i></a>
            </span>
	    </form>
		
		<!-- User Info -->
		<section>
			<div style="font-size: 13px; font-family: sans-serif;">
		        <c:if test="${loginUser.id eq null }">
		        	<div><a href="${pageContext.request.contextPath}/login/login">로그인</a> 후 이용 해주세요</div>
		        </c:if>
		        <c:if test="${loginUser.id ne null }">
		        	<div>
		        		<a href="${pageContext.request.contextPath}/mypage/getBoardListPagingFromId">${loginUser.nickname}</a>님 환영 합니다
		        	</div> 
		        	<div style="text-align: right;">
		        		<a href="${pageContext.request.contextPath}/login/logout" style="color : gray;">Logout</a>
		        	</div>
		        </c:if>
	        </div>
		</section>

		<!-- Menu -->
		<nav id="menu">
			<header class="major">
				<h2>Menu</h2>
			</header> 
			
			<ul id="primeUpperBoardGroup">
				
				<!-- 로그인 상태 -->
				<c:if test="${loginUser.authority eq 'ROLE_ADMIN'}">
					<li>
						<span class="opener">관리자 페이지</span>
						<ul>
							<li><a href="${pageContext.request.contextPath}/admin//selectUserList">유저 관리</a></li>
							<li><a href="${pageContext.request.contextPath}/menu/getMenuList">게시판 관리</a></li>
						</ul>
					</li> 
				</c:if>
				
				<c:if test="${loginUser.id ne null}">
					<li>
						<span class="opener">마이 페이지</span>
						<ul>
							<li><a href="${pageContext.request.contextPath}/mypage/getBoardListPagingFromId">내 정보 보기</a></li>
							<li><a href="${pageContext.request.contextPath}/memberShip/modifyPage">회원 정보 변경</a></li>
							<li><a href="${pageContext.request.contextPath}/mypage/getBoardListPagingFromId">작성글</a></li>
							<li><a href="${pageContext.request.contextPath}/message/getReceiveMessageList">쪽지함</a></li>
						</ul>
					</li> 
				</c:if>
				
				<li>
					<span class="opener">추천 게시판</span>
					<ul>
						<li><a href="${pageContext.request.contextPath}/board/getViewBestBoardList">인기글</a></li>
						<li><a href="${pageContext.request.contextPath}/board/getRecommendBoardList">추천글</a></li>
					</ul>
				</li>
				
				<!-- 자유 게시판 -->
				<li id="freeboard">
					<span class="opener">자유 게시판</span>
					<ul>
					</ul>
				</li>
				
				<!-- 스포츠 게시판 -->
				<li id="sportboard">
					<span class="opener">스포츠 게시판</span>
					<ul>
					</ul>
				</li>
				
				<!-- 취미 게시판 -->
				<li id="hobbyboard">
					<span class="opener">취미 게시판</span>
					<ul>
					</ul>
				</li>
				
				<!-- 신고/건의 게시판 -->
				<li id="suggestboard">
					<span class="opener">신고 / 건의</span>
					<ul>
					</ul>
				</li>
						
						
				<!-- 소개 -->				
				<li>
					<span class="opener">소개</span>
					<ul>		
						<li><a href="${pageContext.request.contextPath}/me/me.jsp">자기 소개</a></li>		
					</ul>
				</li>			
			
			</ul>
		</nav>
				
		<!-- Section -->
		<section>
			<header class="major">
				<h2>Enquire</h2>
			</header>
			<p>문의 사항이 있으실 경우 아래의 연락처로 연락 주시길 바랍니다.</p>
			<ul class="contact">
				<li class="icon solid fa-envelope"><a href="#" onclick="enquireModal()" style="border-bottom: dotted 1px;">문의 쪽지 보내기</a></li>
				<li class="icon solid fa-phone">(010) 5852-6952</li>
			</ul>
		</section>

		<!-- Footer -->
		<footer id="footer">
			<p class="copyright">&copy; ChPark. All rights reserved.</p>
		</footer>
	</div>
</div>

<%@ include file="/WEB-INF/views/layout/messageModalForAdmin.jsp"%>


<script>
$(document).ready(function(){
	
	fn_boardGroupList();
});

function fn_boardGroupList() {

	var bg_name = "${boardGroupFromUpperBgName}";

	$.ajax({
		url : "${pageContext.request.contextPath}/restMenu/getBoardGroupList",
		type : "POST",
		dataType : "json",
		success : function(data) {
			console.log(data);

			if (data.status == "OK") {

				var htmls = "";
				var htmls1 = "";
				var htmls2 = "";
				var htmls3 = "";
				
				if(data.free.length > 0) {
					$(data.free).each(function() {
					
						htmls += "<li>";
						htmls += "<a href='${pageContext.request.contextPath}/board/getBoardList?bg_no=" + this.bg_no + "'>";
						htmls += this.bg_name;
						htmls += "</a>";
						htmls += "</li>";
					});
					
					$("#freeboard ul").html(htmls);
				}
				
				if(data.sport.length > 0) {
					$(data.sport).each(function() {
						
						htmls1 += "<li>";
						htmls1 += "<a href='${pageContext.request.contextPath}/board/getBoardList?bg_no=" + this.bg_no + "'>";
						htmls1 += this.bg_name;
						htmls1 += "</a>";
						htmls1 += "</li>";
					});
					
					$("#sportboard ul").html(htmls1);
				}
				
				if(data.hobby.length > 0) {
					$(data.hobby).each(function() {
						
						htmls2 += "<li>";
						htmls2 += "<a href='${pageContext.request.contextPath}/board/getBoardList?bg_no=" + this.bg_no + "'>";
						htmls2 += this.bg_name;
						htmls2 += "</a>";
						htmls2 += "</li>";
					});
					
					$("#hobbyboard ul").html(htmls2);
				}
				
				if(data.suggest.length > 0) {
					$(data.suggest).each(function() {
						
						htmls3 += "<li>";
						htmls3 += "<a href='${pageContext.request.contextPath}/board/getBoardList?bg_no=" + this.bg_no + "'>";
						htmls3 += this.bg_name;
						htmls3 += "</a>";
						htmls3 += "</li>";
					});
					
					$("#suggestboard ul").html(htmls3);
				}
					

		}
			
		}
	});
	
}



</script>
