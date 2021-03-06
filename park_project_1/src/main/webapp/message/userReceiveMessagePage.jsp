<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	
<!DOCTYPE html>
<html>
<head>
<!-- custom css -->
<%@ include file="/WEB-INF/views/layout/main_head.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/common/css/messageList.css?ver=1.1" />

<meta charset="UTF-8">
<title>Receive Message</title>
<style type="text/css">
	#table a {font: Normal 14px sans-serif; color:#333; text-decoration:none;}
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
				<br/><br/><br/>
				
				<!-- 쪽지 보내기 -->
				<div style="text-align: right;">
					<button onclick="messageModal()">쪽지 보내기</button>
				</div>
				<br/>
				
				<!-- Receive - Send Page-->
				<table class="user_menu">
					<colgroup>
						<col style="40%">
						<col style="40%">
					</colgroup>
					<tbody>
						<tr>
							<td style="text-align: center;">
								<a href="#" onclick="moveReceiveMessagePage()">받은 쪽지함</a>
							</td>
							<td style="text-align: center;">
								<a href="#" onclick="moveSendMessagePage()">보낸 쪽지함</a>
							</td>
						</tr>
					</tbody>
				</table>
				
				<!-- checkBox -->
				<div id="userPageCheckbox" style="text-align: right;">
					<input type="checkbox" name="allCheck" id="allCheck" />
					<label for="allCheck">모두 선택</label>					
					<button type="button" id="deleteBtnChecked">선택 삭제</button>
				</div>
				<br/>
				
				<!-- User Message Info -->	
				<div id="user_message_info">
					
					<!-- Mobile -->
					<div class="box" id="mobileTable">
						<!-- 작성글,댓글  -->
						<c:choose>		
							<c:when test="${empty messageList}">
								<tr>
									<td align="center">데이터가 없습니다</td>
								</tr>
							</c:when>
							<c:when test="${not empty messageList}">
								<ul class="alt">
								<c:forEach var="list" items="${messageList}">
									<li class="liClick" onclick="location.href='${pageContext.request.contextPath}/message/getMessageReceiveContent${pagination.makeAllQuery(pagination.criteria.page)}&mid=${list.mid}'">
										<a href="${pageContext.request.contextPath}/message/getMessageReceiveContent${pagination.makeAllQuery(pagination.criteria.page)}&mid=${list.mid}" style="color: black;">
											<c:out value="${list.getShortMessageContent(20)}" /> 
										</a>
						
										<div style="font-size:10px; margin-top: 2px;">
											<span>보낸 사람 : <c:out value="${list.message_sender}" /></span>
											&nbsp;
											<span style="float: right;">
												<c:if test="${list.message_open == 0}">
													<span>읽지 않음</span>
												</c:if>
												<c:if test="${list.message_open == 1}">
													<span>읽음</span>
												</c:if>												
												&nbsp;
												<span><c:out value="${list.send_dt}" /></span>
											</span>
										</div>
										
									</li>
								</c:forEach>
								</ul>
							</c:when>
						</c:choose>
					</div>
					
					<!-- Web -->
					<div class="box" id="webTable">
						<!-- 작성글,댓글  -->
						<div>
							<table class="table table-striped table-sm" id="table">
								<colgroup>								
									<col id="col1" style="width: 5%;" />
									<col style="width: auto;" />
									<col style="width: auto;" />
									<col id="col4" style="width: 20%;" />
									<col style="width: 20%;" />
								</colgroup>
								<thead>
									<tr>
										<th id="th1" ></th>
										<th>보낸 사람</th>
										<th>쪽지 내용</th>
										<th id="th4" >수신일</th>
										<th>쪽지 확인</th>
									</tr>
								</thead>
								<tbody>
									<c:choose>
										<c:when test="${empty messageList}">
											<tr>
												<td colspan="5" align="center">데이터가 없습니다</td>
											</tr>
										</c:when>
										<c:when test="${not empty messageList}">
											<form id="deleteCheckedBoxForm" method="post" action="${pageContext.request.contextPath}/message/deleteCheckedMessageReceive">
											<c:forEach var="list" items="${messageList}">
												<tr>
													<td id="td1" >
														<input type="checkbox" name="deleteCheckBox" class="deleteCheckBox" id="deleteCheckBox${list.mid}" value="${list.mid}" data-checkbox="${list.mid}"/>
														<label for="deleteCheckBox${list.mid}"></label>
													</td>
													<td style="display: none;"><c:out value="${list.mid}" /></td>
													<td><c:out value="${list.message_sender}" /></td>
													<td>
														<a href="${pageContext.request.contextPath}/message/getMessageReceiveContent${pagination.makeAllQuery(pagination.criteria.page)}&mid=${list.mid}">
															<c:out value="${list.getShortMessageContent(30)}" /> 
														</a>
													</td>
													<td id="td4" ><c:out value="${list.send_dt}" /></td>
													<td>
														<c:if test="${list.message_open == 0}">
															<span>읽지 않음</span>
														</c:if>
														<c:if test="${list.message_open == 1}">
															<span>읽음</span>
														</c:if>
													</td>
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
									href="${pageContext.request.contextPath}/message/getReceiveMessageList${pagination.makeAllQuery(pagination.startPage - 1)}">이전</a>
								</li>
							</c:if>
	
							<c:forEach begin="${pagination.startPage}"
								end="${pagination.endPage}" var="idx">
								<li><a
									class="page <c:out value="${pagination.criteria.page == idx ? 'active' : ''}"/>"
									href="${pageContext.request.contextPath}/message/getReceiveMessageList${pagination.makeAllQuery(idx)}">${idx}</a>
								</li>
							</c:forEach>
	
							<c:if test="${pagination.nextPage && pagination.endPage > 0}">
								<li class="page"><a class="button"
									href="${pageContext.request.contextPath}/message/getReceiveMessageList${pagination.makeAllQuery(pagination.endPage + 1)}">다음</a></li>
							</c:if>
						</ul>
					</div>
					<!-- pagination(end) -->
					
				</div>
				<br/><br/><br/>			
			</div>
		</div>
		<!-- Sidebar -->
		<%@ include file="/WEB-INF/views/layout/sidebar.jsp"%>
		<%@ include file="/WEB-INF/views/layout/messageModal.jsp"%>	
	</div>	
	<%@ include file="/WEB-INF/views/layout/main_plugins.jsp"%>
</body>

<script>

//페이지 전환	
function moveReceiveMessagePage() {

	location.href = "${pageContext.request.contextPath}/message/getReceiveMessageList";
}
	
function moveSendMessagePage() {
	
	location.href = "${pageContext.request.contextPath}/message/getSendMessageList";
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