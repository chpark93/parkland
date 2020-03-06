<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<!-- custom css -->
<%@ include file="/WEB-INF/views/layout/main_head.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/common/css/userList.css?ver=1.3" />

<meta charset="UTF-8">
<title>Admin Page</title>
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
				
				<!-- User List(Admin) -->
				<article>
					<div>
						<div>
							<h2>유저 리스트</h2>
						</div>
						<br/>
						
						<!-- selectPage -->
						<div class="row" style="flex-direction: row-reverse;">
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
						
						<div class="table-responsive">
							<table class="table table-striped table-sm">
								<colgroup>
									<col style="width: auto;" />
									<col style="width: auto;" />
									<col style="width: auto;" />		
									<col id="col1" style="width: auto;" />
									<col id="col2" style="width: 15%;" />
									<col id="col3" style="width: 20%;" />
								</colgroup>
								<thead>
									<tr>
										<th>아이디</th>
										<th>이름</th>
										<th>닉네임</th>
										<th id="th1">이메일</th>
										<th id="th2">Section</th>
										<th id="th3">가입일</th>
									</tr>
								</thead>
								<tbody> 
									<c:choose>
										<c:when test="${empty userList}"> 
											<tr><td colspan="5" align="center">데이터가 없습니다</td></tr>
										</c:when>
										<c:when test="${not empty userList}">
											<c:forEach var="list" items="${userList}">
												<tr>
													<td>
													<c:if test="${searchCriteria.keyword ne null && searchCriteria.keyword ne '' }">										
														<a href="${pageContext.request.contextPath}/admin/UserInfoPageAdmin${pagination.makeAllSearch(pagination.criteria.page)}&id=${list.id}"><c:out value="${list.id}"/>
														</a>												
													</c:if>
													<c:if test="${searchCriteria.keyword eq null || searchCriteria.keyword eq '' }">
														<a href="${pageContext.request.contextPath}/admin/UserInfoPageAdmin${pagination.makeAllQuery(pagination.criteria.page)}&id=${list.id}"><c:out value="${list.id}"/>
														</a>
													</c:if>
													</td>
													<td><c:out value="${list.name}"/></td>
													<td><c:out value="${list.nickname}"/></td>
													<td id="td1"><c:out value="${list.email}"/></td>
													<td id="td2"><c:out value="${list.member_section}"/></td>
													<td id="td3"><c:out value="${list.reg_dt}"/></td>
												</tr>
											</c:forEach>
										</c:when>
									</c:choose>
								</tbody>
							</table>
						</div>
						
						<!-- pagination(start) -->
						<div id="paginationBox" style="text-align: center;">
							<ul class="pagination" style="margin-top: 10px">
								<c:if test="${pagination.prevPage}">
									<li class="page">
										<a class="button" href="${pageContext.request.contextPath}/admin/selectUserList${pagination.makeAllSearch(pagination.startPage - 1)}">이전</a>
									</li>
								</c:if>
								
								<c:forEach begin="${pagination.startPage}" end="${pagination.endPage}" var="idx">
									<li>
										<a class="page <c:out value="${pagination.criteria.page == idx ? 'active' : ''}"/>" 
										href="${pageContext.request.contextPath}/admin/selectUserList${pagination.makeAllSearch(idx)}">${idx}</a>
									</li>
								</c:forEach>
			
								<c:if test="${pagination.nextPage && pagination.endPage > 0}">
									<li class="page"><a class="button" href="${pageContext.request.contextPath}/admin/selectUserList${pagination.makeAllSearch(pagination.endPage + 1)}">다음</a></li>
								</c:if>
							</ul>
						</div>
						<!-- pagination(end) -->
						
						
						<!-- search(start) -->
						<div class="form-group row">
							<div style="padding-right:10px; margin-bottom: 5px;">
								<select class="form-control form-control-sm" name="searchType" id="searchType">
									<option value="id" <c:out value="${searchCriteria.searchType eq 'id' ? 'selected' : ''}"/>>아이디</option>
									<option value="name" <c:out value="${searchCriteria.searchType eq 'name' ? 'selected' : ''}"/>>이름</option>
									<option value="email" <c:out value="${searchCriteria.searchType eq 'email' ? 'selected' : ''}"/>>이메일</option>
								</select>   
							</div>
							<div style="padding-right:10px">
								<input type="text" class="form-control form-control-sm" name="keyword" id="keyword" value="${searchCriteria.keyword }">
							</div>
							
							<div>
								<button class="btn btn-sm btn-primary" name="btnSearch" id="btnSearch">검색</button>
							</div>
							
						</div>
						<!-- search(end) -->
						
					</div>
				</article>
				<br/><br/><br/>
			</div>
		</div>
		<!-- Sidebar -->
		<%@ include file="/WEB-INF/views/layout/sidebar.jsp"%>	
	</div>
	<%@ include file="/WEB-INF/views/layout/main_plugins.jsp"%>
</body>	
<!-- Script(start) -->
<script>		
	$(document).ready(function(){
		setPageCnt();
		setSearchUser();
	});
	
	//페이지당 게시글 수
	function setPageCnt() {
		var listSize = "${pagination.criteria.listSize}";
		var $selectPageCnt = $('#selectPageCnt');
		var page = "${pagination.criteria.page}";
		
		$selectPageCnt.val(listSize).prop("selected", true);
		$selectPageCnt.on('change', function(){
			location.href = "${pageContext.request.contextPath}/admin/selectUserList?page=" + page + "&listSize=" + $selectPageCnt.val();
		});
	}
	
	//검색 버튼 이벤트
	function setSearchUser() {
		var $searchType = $("#searchType");
		var $keyword = $("#keyword");
		
		$(document).on('click', '#btnSearch', function(){
			
			var searchType = $searchType.val();
			var keyword = $keyword.val();
			
			var url = "${pageContext.request.contextPath}/admin/selectUserList${pagination.makeAllQuery(1)}"
					+ "&searchType=" + searchType
					+ "&keyword=" + encodeURIComponent(keyword);
			
			location.href = url;
			
		});
	}
</script>
</html>