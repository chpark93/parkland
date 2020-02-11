<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/layout/main_head.jsp"%>
<meta charset="UTF-8">
<title>Message</title>
</head>
<body class="is-preload">
	<div id="wrapper">
	
		<!-- Main -->
		<div id="main">
			<div class="inner">
			
				<!-- Header -->
				<%@ include file="/WEB-INF/views/layout/header.jsp"%>
				<br/><br/><br/>
				
				<div class="">
					<div class="box">
						<div class="col-6 col-12-xsmall">
							<c:if test="${loginUser.nickname eq messageSendContent.message_sender}">
								<span>받는 사람 : <a href="${pageContext.request.contextPath}/userpage/getBoardListPagingUser?nickname=${messageSendContent.message_receiver}" >
									<c:out value="${messageSendContent.message_receiver}"/></a>
								</span> 
								&nbsp; <c:out value="|" /> &nbsp;
							</c:if>
							<span><c:out value="${messageSendContent.send_dt}" /></span>
						</div>
						<hr>
						
						<!--Main Content -->
						<section id="intro" class="main">
							<div class="spotlight" style="height: auto;">
								<div class="content" id="content">
									${messageSendContent.message_content}
								</div>
							</div>
						</section>
					</div>	
					
					<!-- Button -->
					<div class="col-12">					
						<ul class="actions">
							<li>
								<c:if test="${messageSendContent.message_sender ne messageSendContent.message_receiver}">
									<c:if test="${loginUser.nickname eq messageSendContent.message_sender}">
										<input type="button" id="btnAnswer" onclick="messageModal()" class="primary" value="쪽지 작성">
									</c:if>
								</c:if>
								<c:if test="${messageSendContent.message_sender eq messageSendContent.message_receiver}">
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
	
	var url = "${pageContext.request.contextPath}/message/deleteMessage";
	url = url + "?mid=" + ${messageSendContent.mid};
	
	if(confirm("정말로 삭제 하시겠습니까?")) {	
		location.href = url;		
	}
});


//목록으로
$(document).on('click', '#btnList', function(e) {
	e.preventDefault();
	
	var nickname = "${loginUser.nickname}";
	var sender = "${messageSendContent.message_sender}";
	var receiver = "${messageSendContent.message_receiver}"
	
	if(nickname == sender) {
		
	    location.href = "${pageContext.request.contextPath}/message/getSendMessageList"	  
	}
	else if(nickname == receiver) {
		
		location.href = "${pageContext.request.contextPath}/message/getReceiveMessageList"
	}	  
		  
});

</script>
</html>