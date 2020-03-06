<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/layout/main_head.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/common/css/messageContent.css?ver=1.1" />

<meta charset="UTF-8">
<title>Message</title>
</head>
<body class="is-preload">
	
	<!-- Wrapper -->
	<div id="wrapper" style="height: 1200px;">
	
		<!-- Main -->
		<div id="main">
		
			<div class="inner">
			
				<!-- Header -->
				<%@ include file="/WEB-INF/views/layout/header.jsp"%>
				<br/><br/><br/>
				
				<div class="">
					<div class="box">
						<div class="col-6 col-12-xsmall">
							<c:if test="${loginUser.nickname eq messageReceiveContent.message_receiver}">
								<span>보낸 사람 : <a href="${pageContext.request.contextPath}/userpage/getBoardListPagingUser?nickname=${messageReceiveContent.message_sender}">
									<c:out value="${messageReceiveContent.message_sender}"/></a>
								</span> 
								&nbsp; <c:out value="|" /> &nbsp;
							</c:if>
							<span><c:out value="${messageReceiveContent.send_dt}" /></span>
						</div>
						<hr>
						
						<!--Main Content -->
						<section id="intro" class="main">
							<div class="spotlight" style="height: auto;">
								<div class="content" id="content">
									${messageReceiveContent.message_content}
								</div>
							</div>
						</section>
					</div>	
					
					<!-- Button -->
					<div class="col-12" style="float: right;">					
						<ul class="actions">
							<li>
								<c:if test="${messageReceiveContent.message_sender ne messageReceiveContent.message_receiver}">
									<c:if test="${loginUser.nickname eq messageReceiveContent.message_receiver}">
										<input type="button" id="btnAnswer" onclick="messageModal()" class="primary" value="답장">
									</c:if>
								</c:if>
								<c:if test="${messageReceiveContent.message_sender eq messageReceiveContent.message_receiver}">
									<input type="button" id="btnAnswer" onclick="messageModal()" class="primary" value="쪽지 작성">
								</c:if>
							</li>
							<li>
								<input type="button" id="btnDelete" value="삭제">
							</li>
							<li>
								<input type="button" id="btnList" value="목록으로">
							</li>
						</ul>
					</div>		
				</div>
				<br/><br/>
			</div>
		</div>
		<!-- Sidebar -->
		<%@ include file="/WEB-INF/views/layout/sidebar.jsp"%>
		<%@ include file="/WEB-INF/views/layout/messageModal.jsp"%>
	</div>
	<%@ include file="/WEB-INF/views/layout/main_plugins.jsp"%>
</body>

<script>

//쪽지 삭제 
$(document).on('click', '#btnDelete', function(e) {
	e.preventDefault();
	
	var url = "${pageContext.request.contextPath}/message/deleteMessageReceive";
	url = url + "?mid=" + ${messageReceiveContent.mid};
	
	if(confirm("정말로 삭제 하시겠습니까?")) {	
		location.href = url;		
	}
});


//목록으로
$(document).on('click', '#btnList', function(e) {
	e.preventDefault();
	
	var nickname = "${loginUser.nickname}";
	var sender = "${messageReceiveContent.message_sender}";
	var receiver = "${messageReceiveContent.message_receiver}"
	
	if(nickname == sender) {
		
	    location.href = "${pageContext.request.contextPath}/message/getSendMessageList"	  
	}
	else if(nickname == receiver) {
		
		location.href = "${pageContext.request.contextPath}/message/getReceiveMessageList"
	}	  
		  
});

</script>
</html>