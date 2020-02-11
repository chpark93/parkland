<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/views/layout/main_head.jsp"%>
<title>작성글 페이지</title>

<style type="text/css">
#table a {
	font: Normal 14px sans-serif;
	color: #333;
	text-decoration: none;
}

Img {
	width: 180px;
    height:180px;
    border-radius: 90px;
}
</style>

</head>
<body class="is-preload">

	<!-- Wrapper -->
	<div id="wrapper">

		<!-- Main -->
		<div id="main">
			<div class="inner">

				<!-- Header -->
				<%@ include file="/WEB-INF/views/layout/header.jsp"%>
				<br/>

				<!-- User Info -->
				<div class="user_info">
				
					<!-- 프로필 이미지 변경 -->
					<div style="text-align: right;">
						<button type="button" id="btnModifyProfile" class="btn btn-default">프로필 변경</button>
					</div>
					
					<dl>
						<dd class="user_profile" style="display: table; margin:0 auto;">
							<div id="radius-box">
								<ul class="uploadedFileList" style="margin-right: 12px;">
								<c:if test="${profile ne null && profile ne '' }">
									<div>
										<span class="attachment-icon has-img">
											<a href="../file/displayFile?fileName=${profile}" class="attachment-name"> 
												<img src="../file/displayFile?fileName=${profile}" alt="Attachment">
											</a>
										</span>
									</div>
								</c:if>
								</ul>
								
								<div>
									<h3 id="nickname" style="font-family: sans-serif; color: gray; text-align: center;">
										<a href="${pageContext.request.contextPath}/memberShip/updateUserProfilePage">${loginUser.nickname}</a>
									</h3>
								</div>
							</div>
						</dd>
					</dl>
				</div>
				<hr>
				
					<!-- 작성글,댓글 바-->
					<table class="user_menu">
						<colgroup>
							<col style="width: 20%;" />		
							<col style="width: 20%;" />
							<col style="width: 20%;" />
						</colgroup>
						<tbody>
							<tr style="text-align: center;">
								<td><a href="#" onclick="moveMyBoardPage()">나의 게시글</a></td>
								<td><a href="#" onclick="moveMyReplyPage()">나의 댓글</a></td>
								<td><a href="#" onclick="moveMyAlarmPage()">알림</a></td>
							</tr>
						</tbody>
					</table>
					<br/>
					
					<div class="row" style="flex-direction: row-reverse;">
						<div>
							<input type="checkbox" name="allCheck" id="allCheck" />
							<label for="allCheck">모두 선택</label>					
							<button type="button" id="deleteBtnChecked">선택 삭제</button>
						</div>
						
						<!-- selectPage -->
						<div class="form-group">
							<div class="w100">
								<select class="form-control" id="selectPageCnt">
							  		<option value="10">10</option>
							  		<option value="20">20</option>
							  		<option value="30">30</option>
								</select>
							</div>				
						</div>
					</div>
					<br/>
					
					<div id="user_write_info">
						<div class="box">
							<!-- 작성글,댓글  -->
							<div>
								<table class="table table-striped table-sm" id="table">
									<colgroup>									
										<col style="width: 5%;" />
										<col style="width: 15%;" />
										<col style="width: auto;" />
										<col style="width: 20%;" />
										<col style="width: 20%;" />
									</colgroup>
									<thead>
										<tr>
											<th></th>
											<th>글 번호</th>
											<th>댓글 내용</th>
											<th>닉네임</th>
											<th>작성일</th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when test="${empty replyList}">
												<tr>
													<td colspan="5" align="center">데이터가 없습니다</td>
												</tr>
											</c:when>
											<c:when test="${not empty replyList}">
												<form id="deleteCheckedBoxForm" method="post" action="${pageContext.request.contextPath}/mypage/deleteMyPageReply">
												<c:forEach var="list" items="${replyList}">
													<tr>
														<td>
															<input type="checkbox" name="deleteCheckBox" class="deleteCheckBox" id="deleteCheckBox${list.rid}" value="${list.rid}" data-checkbox="${list.rid}"/>
															<label for="deleteCheckBox${list.rid}"></label>
														</td>
														<td><c:out value="${list.bid}" /></td>
														<td>
															<c:if test="${searchCriteria.keyword ne null && searchCriteria.keyword ne '' }">
																<a href="${pageContext.request.contextPath}/board/getBoardContent${pagination.makeAllSearch(pagination.criteria.page)}&bid=${list.bid}&boardSection=${searchCriteria.boardSection}">
																	<c:out value="${list.rcontent}" /> 
																</a>
															</c:if>
															<c:if test="${searchCriteria.keyword eq null || searchCriteria.keyword eq '' }">
																<a href="${pageContext.request.contextPath}/board/getBoardContent${pagination.makeAllQuery(pagination.criteria.page)}&bid=${list.bid}&boardSection=${searchCriteria.boardSection}">
																	<c:out value="${list.rcontent}" /> 
																</a>
															</c:if>
															<div style="margin-top: 10px;">
																<a href="${pageContext.request.contextPath}/board/getBoardContent?bid=${list.bid}&boardSection=${searchCriteria.boardSection}" style="font-size: 12px;">
																	<c:out value="[게시글 제목 : " /> 
																	<c:out value="${list.title}"/>
																	<c:out value="]" />
																</a>
															</div>
														</td>
														<td><c:out value="${list.reg_nickname}" /></td>
														<td><c:out value="${list.reg_dt}" /></td>
													</tr>
												</c:forEach>
												</form>
											</c:when>
										</c:choose>
									</tbody>
								</table>
							</div>
						</div>
					
						<!-- pagination(start) -->
						<div id="paginationBox" style="text-align: center;">
							<ul class="pagination" style="margin-top: 10px">
								<c:if test="${pagination.prevPage}">
									<li class="page"><a class="button"
										href="${pageContext.request.contextPath}/mypage/getReplyListPagingFromId${pagination.makeAllSearch(pagination.startPage - 1)}">이전</a>
									</li>
								</c:if>
		
								<c:forEach begin="${pagination.startPage}" end="${pagination.endPage}" var="idx">
									<li>
									    <a class="page <c:out value="${pagination.criteria.page == idx ? 'active' : ''}"/>"
										href="${pageContext.request.contextPath}/mypage/getReplyListPagingFromId${pagination.makeAllSearch(idx)}">${idx}</a>
									</li>
								</c:forEach>
		
								<c:if test="${pagination.nextPage && pagination.endPage > 0}">
									<li class="page"><a class="button"
										href="${pageContext.request.contextPath}/mypage/getReplyListPagingFromId${pagination.makeAllSearch(pagination.endPage + 1)}">다음</a></li>
								</c:if>
							</ul>
						</div>
						<!-- pagination(end) -->
		
						<!-- search(start) -->
						<div class="form-group row justify-content-center">
							<div class="w100" style="padding-right: 10px">
								<select class="form-control" id="searchType" name="searchType">
									<option value="rcontent"
										<c:out value="${searchCriteria.searchType eq 'rcontent' ? 'selected' : ''}"/>>내용</option>
								</select>
							</div>
							<div class="w300" style="padding-right: 10px">
								<input type="text" class="form-control" id="keyword" name="keyword" value="${searchCriteria.keyword}">
							</div>
							<div>
								<button type="button" id="btnSearch">검색</button>
							</div>
							
						</div>
					<!-- search(end) -->
					</div>
					<br/><br/><br/>
					
			</div>
		</div>
		<!-- Sidebar -->
		<%@ include file="/WEB-INF/views/layout/sidebar.jsp"%>
	</div>
	<%@ include file="/WEB-INF/views/layout/main_plugins.jsp"%>
</body>

<script>
	$(document).ready(function() {
		setPageCnt();
		setSearchPage();
	});

	
	//프로필 이미지 변경 
	$(document).on('click', '#btnModifyProfile', function(e) {
		e.preventDefault();
		
		location.href = "${pageContext.request.contextPath}/memberShip/updateUserProfilePage";
	});

	//페이지당 게시글 수
	function setPageCnt() {
		var listSize = "${pagination.criteria.listSize}";
		var $selectPageCnt = $('#selectPageCnt');
		var page = "${pagination.criteria.page}";

		$selectPageCnt.val(listSize).prop("selected", true);
		$selectPageCnt.on('change', function() {
			location.href = "${pageContext.request.contextPath}/mypage/getReplyListPagingFromId?"
						  + "&page=" + page
						  + "&listSize=" + $selectPageCnt.val();
			});
	}

	//검색 버튼 이벤트
	function setSearchPage() {
		var $searchType = $("#searchType");
		var $keyword = $("#keyword");

		$(document).on('click', '#btnSearch', function() {
			
			var searchType = $searchType.val();
			var keyword = $keyword.val();

			var url = "${pageContext.request.contextPath}/mypage/getReplyListPagingFromId${pagination.makeAllQuery(1)}"
					+ "&searchType=" + searchType
					+ "&keyword=" + encodeURIComponent(keyword);

			location.href = url;

		});
	}
	
	//페이지 전환
	function moveMyReplyPage() {	
		location.href = "${pageContext.request.contextPath}/mypage/getReplyListPagingFromId";
		
	}
	function moveMyBoardPage() {	
		location.href = "${pageContext.request.contextPath}/mypage/getBoardListPagingFromId";
		
	}
	function moveMyAlarmPage() {
		location.href = "${pageContext.request.contextPath}/mypage/getAlarmListPagingFromId";
	}
</script>

<script>
//체크박스

//전체 체크
$("#allCheck").click(function() {
	var chk = $("#allCheck").prop("checked");
	
	if(chk) {
		$(".deleteCheckBox").prop("checked", true);
	}
	else {
		$(".deleteCheckBox").prop("checked", false);
	}
});


//전체 체크 해제
$(".deleteCheckBox").click(function() {

	$("#allCheck").prop("checked", false);
});


//삭제 
$("#deleteBtnChecked").click(function(e) {
	e.preventDefault();
	 
	var checkedBoxArr = new Array();
	
	$("input[class='deleteCheckBox']:checked").each(function(){
		checkedBoxArr.push($(this).attr("data-checkbox"));	
	}); 
	
	var deleteCheckBox = checkedBoxArr;
	
	
	if(confirm('삭제 하시겠습니까?')) {
		$("#deleteCheckedBoxForm").submit();
	}
});
</script>

</html>