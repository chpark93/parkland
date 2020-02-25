<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html> 
<head>
<%@ include file="/WEB-INF/views/layout/main_head.jsp"%>
<title>Board List</title>
<style type="text/css">
	#table a {
		font: Normal 14px sans-serif; 
		color:#333; 
		text-decoration:none;
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
				<br/><br/>
				
				<div class="">
					<span style="font-size:20px; color: black;"><c:out value="${boardGroupInfo.bg_name}"/></span>
					&nbsp;&nbsp; <c:out value="[" /> &nbsp;
					<span><c:out value="${boardGroupInfo.bg_info}"/></span>
					&nbsp; <c:out value="]" />
				</div>
				<br/>
				<div class="row" style="flex-direction: row-reverse;"> 
					
					<!-- Write -->
					<div>
						<button type="button" class="btn btn-sm btn-primary" id="btnWriteForm">글 작성</button>
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
									
					<div class="form-group">
						<div class="custom-control custom-checkbox small" style="float:left; margin-top: 8px;">
							<input type="checkbox" class="custom-control-input" id="noticeCheck" name="noticeCheck"> 
							<label class="custom-control-label" for="noticeCheck">공지 숨기기</label>
						</div>
					</div>
				</div>
				<br/>
				
				<!-- Admin 글 삭제 -->
				<c:if test="${loginUser.authority eq 'ROLE_ADMIN'}">
					<div style="text-align: right;">
						<input type="checkbox" name="allCheck" id="allCheck" />
						<label for="allCheck">모두 선택</label>					
						<button type="button" id="deleteBtnChecked">선택 삭제</button>
					</div>
					<br/>
				</c:if>
				
				<div class="table-wrapper" id="table">
					<table class="table table-striped table-sm">
						<colgroup>
							<c:if test="${loginUser.authority eq 'ROLE_ADMIN'}">
								<col style="width: 5%" />
							</c:if>
							<col style="width: 10%;" />
							<col style="width: auto;" />
							<col style="width: 15%;" />
							<col style="width: 10%;" />
							<col style="width: 20%;" />
						</colgroup>
						<thead>
							<tr>
								<c:if test="${loginUser.authority eq 'ROLE_ADMIN'}">
									<th></th>
								</c:if>
								<th>NO</th>
								<th>글제목</th>
								<th>작성자</th>
								<th>조회수</th>
								<th>작성일</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${empty boardList }">
									<tr>
										<td colspan="6" align="center">데이터가 없습니다</td>
									</tr>
								</c:when>
								
								<c:when test="${not empty boardList}">
									
									<!-- 유저 게시판 뷰 -->
									<c:if test="${loginUser.authority ne 'ROLE_ADMIN'}">
								
										<c:forEach var="list" items="${boardList}">
											
											<!-- 일반 글 -->			
											<c:if test="${list.notice eq '0'}">
												<tr>
													<td><c:out value="${list.bid}" /></td>
													<td>
														<c:if test="${searchCriteria.keyword ne null && searchCriteria.keyword ne '' }">										
															<a href="${pageContext.request.contextPath}/board/getBoardContent${pagination.makeSearch(pagination.criteria.page)}&bid=${list.bid}">
																<c:out value="${list.getShortTitle(30)}" />
															</a>												
														</c:if>
														<c:if test="${searchCriteria.keyword eq null || searchCriteria.keyword eq '' }">
															<a href="${pageContext.request.contextPath}/board/getBoardContent${pagination.makeQuery(pagination.criteria.page)}&bid=${list.bid}">
																<c:out value="${list.getShortTitle(30)}" />
															</a>
														</c:if>
														&nbsp;
														<c:if test="${list.reply_view_cnt > 0}">
															<span class="badge bg-teal"><i class="fa fa-comment-o"></i>[${list.reply_view_cnt}]</span>										
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
											</c:if>
											
											<!-- 공지글 -->
											<c:if test="${list.notice eq '1'}">
												<tr class="noticeList" style="">
													<td><c:out value="공지" /></td>
													<td>
														<c:if test="${searchCriteria.keyword ne null && searchCriteria.keyword ne '' }">										
															<a href="${pageContext.request.contextPath}/board/getBoardContent${pagination.makeSearch(pagination.criteria.page)}&bid=${list.bid}" style="font-weight: bold;">
																<c:out value="${list.getShortTitle(30)}" />
															</a>												
														</c:if>
														<c:if test="${searchCriteria.keyword eq null || searchCriteria.keyword eq '' }">
															<a href="${pageContext.request.contextPath}/board/getBoardContent${pagination.makeQuery(pagination.criteria.page)}&bid=${list.bid}" style="font-weight: bold;">
																<c:out value="${list.getShortTitle(30)}" />
															</a>
														</c:if>
														&nbsp;
														<c:if test="${list.reply_view_cnt > 0}">
															<span class="badge bg-teal"><i class="fa fa-comment-o"></i>[${list.reply_view_cnt}]</span>										
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
											</c:if>
										</c:forEach>
									</c:if>
									
									<!-- 관리자 게시판 뷰 -->
									<c:if test="${loginUser.authority eq 'ROLE_ADMIN'}">
										<form id="deleteCheckedBoxForm" method="post" action="${pageContext.request.contextPath}/board/deleteBoardAdmin">
											
											<input type="hidden" name="bg_no" value="${searchCriteria.bg_no}">
											<input type="hidden" name="page" value="${searchCriteria.page}">
				                            <input type="hidden" name="listSize" value="${searchCriteria.listSize}">
				                            <input type="hidden" name="searchType" value="${searchCriteria.searchType}">
				                            <input type="hidden" name="keyword" value="${searchCriteria.keyword}">
									
									
											<c:forEach var="list" items="${boardList}">
												
												<!-- 일반글 -->
												<c:if test="${list.notice eq '0'}">
													<tr>
														<td>
															<input type="checkbox" name="deleteCheckBox" class="deleteCheckBox" id="deleteCheckBox${list.bid}" value="${list.bid}" data-checkbox="${list.bid}"/>
															<label for="deleteCheckBox${list.bid}"></label>
														</td>
														<td><c:out value="${list.bid}" /></td>
														<td>
															<c:if test="${searchCriteria.keyword ne null && searchCriteria.keyword ne '' }">
																<a href="${pageContext.request.contextPath}/board/getBoardContent${pagination.makeSearch(pagination.criteria.page)}&bid=${list.bid}">
																	<c:out value="${list.getShortTitle(30)}" /> 
																</a>
															</c:if> 
															<c:if test="${searchCriteria.keyword eq null || searchCriteria.keyword eq '' }">
																<a href="${pageContext.request.contextPath}/board/getBoardContent${pagination.makeQuery(pagination.criteria.page)}&bid=${list.bid}">
																	<c:out value="${list.getShortTitle(30)}" /> 
																</a>
															</c:if> 
															&nbsp; 
															<c:if test="${list.reply_view_cnt > 0}">
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
												</c:if>
															
												<!-- 공지글 -->
												<c:if test="${list.notice eq '1'}">
													<tr class="noticeList" style="">
														<td>
															<input type="checkbox" name="deleteCheckBox" class="deleteCheckBox" id="deleteCheckBox${list.bid}" value="${list.bid}" data-checkbox="${list.bid}"/>
															<label for="deleteCheckBox${list.bid}"></label>
														</td>
														<td><c:out value="공지" /></td>
														<td>
															<c:if test="${searchCriteria.keyword ne null && searchCriteria.keyword ne '' }">
																<a href="${pageContext.request.contextPath}/board/getBoardContent${pagination.makeSearch(pagination.criteria.page)}&bid=${list.bid}" style="font-weight: bold;">
																	<c:out value="${list.getShortTitle(30)}" /> 
																</a>
															</c:if> 
															<c:if test="${searchCriteria.keyword eq null || searchCriteria.keyword eq '' }">
																<a href="${pageContext.request.contextPath}/board/getBoardContent${pagination.makeQuery(pagination.criteria.page)}&bid=${list.bid}" style="font-weight: bold;">
																	<c:out value="${list.getShortTitle(30)}" /> 
																</a>
															</c:if> 
															&nbsp; 
															<c:if test="${list.reply_view_cnt > 0}">
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
												</c:if>
											</c:forEach>
										</form>
									</c:if>
								</c:when>
							</c:choose>
						</tbody>
					</table>
				</div>

				<div>
					<button type="button" class="btn btn-sm btn-primary" id="btnWriteForm">글 작성</button>
				</div>
				
 
				<!-- pagination(start) -->
				<div id="paginationBox" style="text-align: center;">
					<ul class="pagination" style="margin-top: 10px">
						<c:if test="${pagination.prevPage}">
							<li class="page">
								<a class="button" href="${pageContext.request.contextPath}/board/getBoardList${pagination.makeSearch(pagination.startPage - 1)}">이전</a>
							</li>
						</c:if>
						
						<c:forEach begin="${pagination.startPage}" end="${pagination.endPage}" var="idx">
							<li>
								<a class="page <c:out value="${pagination.criteria.page == idx ? 'active' : ''}"/>" 
								href="${pageContext.request.contextPath}/board/getBoardList${pagination.makeSearch(idx)}">${idx}</a>
							</li>
						</c:forEach>

						<c:if test="${pagination.nextPage && pagination.endPage > 0}">
							<li class="page"><a class="button" href="${pageContext.request.contextPath}/board/getBoardList${pagination.makeSearch(pagination.endPage + 1)}">다음</a></li>
						</c:if>
					</ul>
				</div>
				<!-- pagination(end) -->
				
				
				<!-- search(start) -->
				<div class="form-group row justify-content-center">
					<div class="w100" style="padding-right: 10px">
						<select class="form-control form-control-sm" name="searchType" id="searchType">
							<option value="title" <c:out value="${searchCriteria.searchType eq 'title' ? 'selected' : ''}"/>>제목</option>
							<option value="content" <c:out value="${searchCriteria.searchType eq 'content' ? 'selected' : ''}"/>>내용</option>
							<option value="reg_id" <c:out value="${searchCriteria.searchType eq 'reg_id' ? 'selected' : ''}"/>>작성자</option>
							<option value="title_content" <c:out value="${searchCriteria.searchType eq 'title_content' ? 'selected' : ''}"/>>제목 + 내용</option>
						</select>
					</div>
					<div class="w300" style="padding-right: 10px">
						<input type="text" class="form-control form-control-sm" name="keyword" id="keyword" value="${searchCriteria.keyword}">
					</div>
					<div>
						<button class="btn btn-sm btn-primary" id="btnSearch">검색</button>
					</div>
					
				</div>
				<br/><br/><br/><br/>
				<!-- search(end) -->
				
			</div>
		</div>
		
	<!-- Sidebar -->
	<%@ include file="/WEB-INF/views/layout/sidebar.jsp"%> 	
	</div>
	<%@ include file="/WEB-INF/views/layout/main_plugins.jsp"%>
</body>

<!-- script(start) -->
<script>
	$(document).ready(function(){
		setPageCnt();
		setSearchPage();
		checkNotice();
	});
	
	//게시글 작성
	$(document).on('click', '#btnWriteForm', function(e){
		e.preventDefault();
		
		location.href = "${pageContext.request.contextPath}/board/boardForm?bg_no=<c:url value='${pagination.bg_no}'/>";
	});
	
	//상세 페이지
	function fn_contentView(bid){
		var url = "${pageContext.request.contextPath}/board/getBoardContent";
		url = url + "?bg_no=<c:url value='${pagination.bg_no}'/>";
		url = url + "&bid="+ bid;
		location.href = url;
	}
	
	
	//페이지당 게시글 수
	function setPageCnt() {
		var listSize = "${pagination.criteria.listSize}";
		var $selectPageCnt = $('#selectPageCnt');
		var page = "${pagination.criteria.page}";
		
		$selectPageCnt.val(listSize).prop("selected", true);
		$selectPageCnt.on('change', function(){
			location.href = "${pageContext.request.contextPath}/board/getBoardList?bg_no=${searchCriteria.bg_no}" + "&page=" + page + "&listSize=" + $selectPageCnt.val();
		});
	}
	
	
	//검색 버튼 이벤트
	function setSearchPage() {
		var $searchType = $("#searchType");
		var $keyword = $("#keyword");
		
		$(document).on('click', '#btnSearch', function(){
			
			var searchType = $searchType.val();
			var keyword = $keyword.val();
			
			var url = "${pageContext.request.contextPath}/board/getBoardList${pagination.makeQuery(1)}"
					+ "&searchType=" + searchType
					+ "&keyword=" + encodeURIComponent(keyword);
			
			location.href = url;
			
		});
	}
			
	
	//공지 숨기기
	function checkNotice() { 
		
		var noticeCheckCookie = getCookie("noticeCheckCookie");
		var noticeList = document.getElementsByClassName("noticeList");
		
		if(noticeCheckCookie == 'Y') {
	        $("#noticeCheck").prop("checked", true);
	        
	        for(i = 0; i < noticeList.length; i++) {
        		noticeList[i].style.display = "none";
        	}
	        
	    } else {
	        $("#noticeCheck").prop("checked", false);
	        
	        for(i = 0; i < noticeList.length; i++) {
            	noticeList[i].style.display = "";
            }
            
	    } 
		
		$("#noticeCheck").change(function(){
	        var noticeList = document.getElementsByClassName("noticeList");
	        
	        if($("#noticeCheck").is(":checked")) {    
	        
	        	for(i = 0; i < noticeList.length; i++) {
	        		noticeList[i].style.display = "none";
	        	}
	        	
	        	setCookie("noticeCheckCookie", "Y", 1);
	        	
	        }
	        else {
	           
	            for(i = 0; i < noticeList.length; i++) {
	            	noticeList[i].style.display = "";
	            }
	            
	            deleteCookie("noticeCheckCookie");
	    
	        }
	    });
		
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


<script>
//쿠키

//setCookie
function setCookie(cookieName, value, exdays) {
	var exdate = new Date();
	exdate.setDate(exdate.getDate() + exdays);
	var cookieValue = escape(value) + ((exdays == null) ? "" : "; expires=" + exdate.toGMTString());
	document.cookie = cookieName + "=" + cookieValue;

}

//getCookie
function getCookie(cookie_name) {
	var x, y;
	var val = document.cookie.split(';');
	
	for(var i = 0; i < val.length; i++) {
		x = val[i].substr(0, val[i].indexOf('='));
		y = val[i].substr(val[i].indexOf('=') + 1);
		
		x = x.replace(/^\s+|\s+$/g, ''); //공백 제거
		
		if(x == cookie_name) {
			return unescape(y); //디코딩 후 리턴
		}
	}
	
}

//delete cookie
function deleteCookie(cookieName) {
	var expireDate = new Date();
	expireDate.setDate(expireDate.getDate() - 1);
	document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
}

</script>

<!-- script(end) -->
</html>