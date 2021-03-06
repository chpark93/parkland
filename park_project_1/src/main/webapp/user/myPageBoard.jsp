<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<!-- custom css -->
<%@ include file="/WEB-INF/views/layout/main_head.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/common/css/common.css?ver=1.1" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/common/css/board.css?ver=1.3" />

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
				<div class="user_info" >
					
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
						<col style="width: 20%;">
						<col style="width: 20%;">
						<col style="width: 20%;">
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
					<div id="userPageCheckbox">
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
					
					<!-- Mobile -->
					<div class="box" id="mobileTable">
						<!-- 작성글,댓글  -->
						<c:choose>		
							<c:when test="${empty boardList}">
								<tr>
									<td colspan="5" align="center">데이터가 없습니다</td>
								</tr>
							</c:when>
							<c:when test="${not empty boardList}">
								<ul class="alt">
								<c:forEach var="list" items="${boardList}">
									<li class="liClick" onclick="location.href='${pageContext.request.contextPath}/board/getBoardContent?bg_no=${list.bg_no}&bid=${list.bid}'">
										<a href="${pageContext.request.contextPath}/board/getBoardContent?bg_no=${list.bg_no}&bid=${list.bid}" style="color: black;">
											<c:out value="${list.getShortTitle(20)}" />
										</a>
						
										<span style="float: right; font-size:16px;">
											<c:if test="${list.reply_view_cnt > 0}">
												<span class="badge bg-teal">			
												<i class="fa fa-comment-o"></i>[${list.reply_view_cnt}]</span>
											</c:if>
										</span>
										<div style="font-size:10px;">
											<span>
												<c:out value="${list.reg_nickname}" />
											</span>
											&nbsp;
											<span>
												<c:out value="조회 : " />
												<c:out value="${list.view_cnt}" />
											</span>
											&nbsp;
											<span><c:out value="${list.reg_dt}" /></span>
											<div>
												<span>
													<c:out value="[" /> 
													<c:out value="${list.bg_name}"/>
													<c:out value="]" />
												</span>
											</div>												
										</div>
										
									</li>
								</c:forEach>
								</ul>
							</c:when>
						</c:choose>
					</div>
						
					<!-- Web -->
					<div class="box" id="webTable"> 
						<div class="table-wrapper" id="table">
							<table class="table table-striped table-sm">
								<colgroup>
									<col id="col0" style="width: 5%;"/>
									<col id="col1" style="width: 15%;" />
									<col id="col2" style="width: auto;" />
									<col id="col3" style="width: 15%;" />
									<col id="col4" style="width: 15%;" />
									<col id="col5" style="width: 15%;" />
								</colgroup>
								<thead>
									<tr>
										<th id="th0"></th>
										<th id="th1">글 번호</th>
										<th id="th2">글 제목</th>
										<th id="th3">작성자</th>
										<th id="th4">조회수</th>
										<th id="th5">작성일</th>
									</tr>
								</thead>    
								<tbody>
									<c:choose>
										<c:when test="${empty boardList}">
											<tr>
												<td colspan="6" align="center">데이터가 없습니다</td>
											</tr>
										</c:when>
										<c:when test="${not empty boardList}">
											<form id="deleteCheckedBoxForm" method="post" action="${pageContext.request.contextPath}/mypage/deleteMyPageBoard">
											<c:forEach var="list" items="${boardList}">
												<tr>
													<td id="td0">
														<input type="checkbox" name="deleteCheckBox" class="deleteCheckBox" id="deleteCheckBox${list.bid}" value="${list.bid}" data-checkbox="${list.bid}"/>
														<label for="deleteCheckBox${list.bid}"></label>
													</td>
													<td id="td1">
														<c:out value="${list.bid}" />
													</td>
													<td id="td2">
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
														<div style="margin-top: 10px;">
															<a href="${pageContext.request.contextPath}/board/getBoardList?bg_no=${list.bg_no}" style="font-size: 12px;">
																<c:out value="[" /> 
																<c:out value="${list.bg_name}"/>
																<c:out value="]" />
															</a>
														</div>
													</td>
													<td id="td3"><c:out value="${list.reg_nickname}" /></td>
													<td id="td4"><c:out value="${list.view_cnt}" /></td>
													<td id="td5"><c:out value="${list.reg_dt}" /></td>
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
								<li class="page">
									<a href="${pageContext.request.contextPath}/mypage/getBoardListPagingFromId${pagination.makeAllSearch(pagination.startPage - 1)}">◀</a>
								</li>
							</c:if>

							<c:forEach begin="${pagination.startPage}"
								end="${pagination.endPage}" var="idx">
								<li>
									<a class="page <c:out value="${pagination.criteria.page == idx ? 'active' : ''}"/>"
									href="${pageContext.request.contextPath}/mypage/getBoardListPagingFromId${pagination.makeAllSearch(idx)}">${idx}</a>
								</li>
							</c:forEach>

							<c:if test="${pagination.nextPage && pagination.endPage > 0}">
								<li class="page">
									<a href="${pageContext.request.contextPath}/mypage/getBoardListPagingFromId${pagination.makeAllSearch(pagination.endPage + 1)}">▶</a>
								</li>
							</c:if>
						</ul>
					</div>
					<!-- pagination(end) -->		

					<!-- search(start) -->
					<div class="form-group row">
						<div class="w100" style="padding-right: 10px; margin-bottom: 5px;">
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
	</div>
	<%@ include file="/WEB-INF/views/layout/main_plugins.jsp"%>
</body>

<script>
	$(document).ready(function() {
		setPageCnt();
		setSearchPage();
		
		//light box
		var lastTem = $(this);
		lastTem.find(".attachment-name").attr("data-lightbox", "uploadImages");
		
		
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
			location.href = "${pageContext.request.contextPath}/mypage/getBoardListPagingFromId?"
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

			var url = "${pageContext.request.contextPath}/mypage/getBoardListPagingFromId${pagination.makeAllQuery(1)}"
					+ "&searchType="
					+ searchType
					+ "&keyword="
					+ encodeURIComponent(keyword);

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