<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- Custom CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/common/css/common.css?ver=1.1" />
<%@ include file="/WEB-INF/views/layout/main_head.jsp"%>

<title>유저 페이지</title>
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
				<div class="user_info" >
				<br/><br/>	
					
					<!-- 유저 프로필 -->
					<dl>
						<dd class="user_profile" style="display: table; margin:0 auto;">
							<div id="radius-box">
								<ul class="uploadedFileList" style="margin-right: 12px;">
								<c:if test="${profile ne null && profile ne '' }">
									<div>
										<span class="mailbox-attachment-icon has-img">
											<a href="../file/displayFile?fileName=${profile}" class="mailbox-attachment-name"> 
												<img src="../file/displayFile?fileName=${profile}" alt="Attachment">
											</a>
										</span>
									</div>
								</c:if>
								</ul>
								
								<div>
									<h3 id="nickname" style="font-family: sans-serif; color: gray; text-align: center;">
										<label>${user.nickname}</label>
									</h3>
								</div>
							
							</div>
						</dd>
					</dl> 
				</div>
				
				<!-- 쪽지 보내기 -->				
				<div style="text-align: right;">
					<button onclick="messageModalUserPage()">쪽지 보내기</button>
				</div>
				<hr>

				<!-- 작성글,댓글 바-->
				<table class="user_menu">
					<colgroup>
						<col style="width: 50%;">
						<col style="width: 50%;">
					</colgroup>
					<tbody>
						<tr style="text-align: center;">
							<td><a href="#" onclick="moveUserBoardPage()">게시글</a></td>
							<td><a href="#" onclick="moveUserReplyPage()">댓글</a></td>
						</tr>
					</tbody>
				</table>
				<br/>
				
				<div id="user_write_info">
					<div class="box">
						<!-- 작성글,댓글  -->
						<div>
							<table class="table table-striped table-sm" id="table">
								<colgroup>
									<col style="width: 15%;" />
									<col style="width: auto;" />
									<col style="width: 15%;" />
									<col style="width: 15%;" />
									<col style="width: 15%;" />
								</colgroup>
								<thead>
									<tr>
										<th>글 번호</th>
										<th>글 제목</th>
										<th>작성자</th>
										<th>조회수</th>
										<th>작성일</th>
									</tr>
								</thead>    
								<tbody>
									<c:choose>
										<c:when test="${empty boardList}">
											<tr>
												<td colspan="5" align="center">데이터가 없습니다</td>
											</tr>
										</c:when>
										<c:when test="${not empty boardList}">
											<c:forEach var="list" items="${boardList}">
												<tr>
													<td>
														<c:out value="${list.bid}" />
														<div style="margin-top: 10px;">
															<a href="${pageContext.request.contextPath}/board/getBoardList?bg_no=${list.bg_no}" style="font-size: 12px;">
																<c:out value="[" /> 
																<c:out value="${list.bg_name}"/>
																<c:out value="]" />
															</a>
														</div>
													</td>
													<td>
														<c:if test="${searchCriteria.keyword ne null && searchCriteria.keyword ne '' }">
															<a href="${pageContext.request.contextPath}/board/getBoardContent${pagination.makeAllSearch(pagination.criteria.page)}&bid=${list.bid}&boardSection=${searchCriteria.boardSection}"><c:out
																value="${list.getShortTitle(30)}" /> </a>
														</c:if> 
														<c:if test="${searchCriteria.keyword eq null || searchCriteria.keyword eq '' }">
															<a href="${pageContext.request.contextPath}/board/getBoardContent${pagination.makeAllQuery(pagination.criteria.page)}&bid=${list.bid}&boardSection=${searchCriteria.boardSection}"><c:out
																value="${list.getShortTitle(30)}" /> </a>
														</c:if> &nbsp; <c:if test="${list.reply_view_cnt > 0}">
															<span class="badge bg-teal">
															<i class="fa fa-comment-o"></i>[${list.reply_view_cnt}]</span>
														</c:if>
													</td>
													<td>
														<c:if test="${list.reg_id eq loginUser.id}">
															<a href="${pageContext.request.contextPath}/mypage/getBoardListPagingFromId">
																<c:out value="${list.reg_nickname}" />
															</a>
														</c:if>
														<c:if test="${list.reg_id ne loginUser.id}">
															<a href="${pageContext.request.contextPath}/userpage/getBoardListPagingUser?nickname=${list.reg_nickname}">
																<c:out value="${list.reg_nickname}" />
															</a>
														</c:if>
													</td>
													<td><c:out value="${list.view_cnt}" /></td>
													<td><c:out value="${list.reg_dt}" /></td>
												</tr>
											</c:forEach>
										</c:when>
									</c:choose>
								</tbody>
							</table>
						</div>
					</div>

					<!-- pagination(start) -->
					<div id="paginationBox" style="text-align: center;">
						<c:if test="${searchCriteria.keyword ne null && searchCriteria.keyword ne '' }">
							<ul class="pagination" style="margin-top: 10px">
								<c:if test="${pagination.prevPage}">
									<li class="page"><a class="button"
										href="${pageContext.request.contextPath}/userpage/getBoardListPagingUser${pagination.makeAllSearch(pagination.startPage - 1)}&nickname=${user.nickname} ">이전</a>
									</li>
								</c:if>
	
								<c:forEach begin="${pagination.startPage}"
									end="${pagination.endPage}" var="idx">
									<li><a
										class="page <c:out value="${pagination.criteria.page == idx ? 'active' : ''}"/>"
										href="${pageContext.request.contextPath}/userpage/getBoardListPagingUser${pagination.makeAllSearch(idx)}&nickname=${user.nickname}">${idx}</a>
									</li>
								</c:forEach>
	
								<c:if test="${pagination.nextPage && pagination.endPage > 0}">
									<li class="page"><a class="button"
										href="${pageContext.request.contextPath}/userpage/getBoardListPagingUser${pagination.makeAllSearch(pagination.endPage + 1)}&nickname=${user.nickname}">다음</a></li>
								</c:if>
							</ul>
						</c:if>
						
						<c:if test="${searchCriteria.keyword eq null || searchCriteria.keyword eq '' }">
							<ul class="pagination" style="margin-top: 10px">
								<c:if test="${pagination.prevPage}">
									<li class="page"><a class="button"
										href="${pageContext.request.contextPath}/userpage/getBoardListPagingUser${pagination.makeAllQuery(pagination.startPage - 1)}&nickname=${user.nickname}">이전</a>
									</li>
								</c:if>
	
								<c:forEach begin="${pagination.startPage}"
									end="${pagination.endPage}" var="idx">
									<li><a
										class="page <c:out value="${pagination.criteria.page == idx ? 'active' : ''}"/>"
										href="${pageContext.request.contextPath}/userpage/getBoardListPagingUser${pagination.makeAllQuery(idx)}&nickname=${user.nickname}">${idx}</a>
									</li>
								</c:forEach>
	
								<c:if test="${pagination.nextPage && pagination.endPage > 0}">
									<li class="page"><a class="button"
										href="${pageContext.request.contextPath}/userpage/getBoardListPagingUser${pagination.makeAllQuery(pagination.endPage + 1)}&nickname=${user.nickname}">다음</a></li>
								</c:if>
							</ul>
						</c:if>
					</div>
					<!-- pagination(end) -->

					<!-- search(start) -->
					<div class="form-group row justify-content-center">
						<div class="w100" style="padding-right: 10px">
							<select class="form-control" id="searchType" name="searchType">
								<option value="title"
									<c:out value="${searchCriteria.searchType eq 'title' ? 'selected' : ''}"/>>제목</option>
								<option value="content"
									<c:out value="${searchCriteria.searchType eq 'content' ? 'selected' : ''}"/>>내용</option>
								<option value="title_content"
									<c:out value="${searchCriteria.searchType eq 'title_content' ? 'selected' : ''}"/>>제목
									+ 내용</option>
							</select>
						</div>
						<div class="w300" style="padding-right: 10px">
							<input type="text" class="form-control" id="keyword"
								name="keyword" value="${searchCriteria.keyword}">
						</div>
						<div>
							<button type="button" id="btnSearch">검색</button>
						</div>

						<!-- selectPage -->
						<div class="form-group row" style="text-align: right;">
							<div class="w100">
								<select class="form-control" id="selectPageCnt">
									<option value="10">10</option>
									<option value="20">20</option>
									<option value="30">30</option>
								</select>
							</div>
						</div>
					</div>
					<!-- search(end) -->
				</div>

				<br />
				<br />
				<br />

			</div>
		</div>
		<!-- Sidebar -->
		<%@ include file="/WEB-INF/views/layout/sidebar.jsp"%>
		<%@ include file="/WEB-INF/views/layout/messageModalUserPage.jsp"%>	
	</div>
	<%@ include file="/WEB-INF/views/layout/main_plugins.jsp"%>
</body>

<script>
	$(document).ready(function() {
		setPageCnt();
		setSearchPage();
		
		//light box
		var lastTem = $(this);
		lastTem.find(".mailbox-attachment-name").attr("data-lightbox", "uploadImages");
		
		
	});


	//페이지당 게시글 수
	function setPageCnt() {
		var listSize = "${pagination.criteria.listSize}";
		var $selectPageCnt = $('#selectPageCnt');
		var page = "${pagination.criteria.page}";

		$selectPageCnt.val(listSize).prop("selected", true);
		$selectPageCnt.on('change', function() {
			location.href = "${pageContext.request.contextPath}/userpage/getBoardListPagingUser?nickname=${user.nickname}"
					+ "&page="
					+ page
					+ "&listSize="
					+ $selectPageCnt.val();
		});
	}

	//검색 버튼 이벤트
	function setSearchPage() {
		var $searchType = $("#searchType");
		var $keyword = $("#keyword");

		$(document).on('click', '#btnSearch', function() {

			var searchType = $searchType.val();
			var keyword = $keyword.val();

			var url = "${pageContext.request.contextPath}/userpage/getBoardListPagingUser${pagination.makeAllQuery(1)}&nickname=${user.nickname}"
					+ "&searchType="
					+ searchType
					+ "&keyword="
					+ encodeURIComponent(keyword);

			location.href = url;

		});
	}

	//페이지 전환	
	function moveUserReplyPage() {
		location.href = "${pageContext.request.contextPath}/userpage/getReplyListPagingUser?nickname=${user.nickname}";
	}

	function moveUserBoardPage() {
		location.href = "${pageContext.request.contextPath}/userpage/getBoardListPagingUser?nickname=${user.nickname}";
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