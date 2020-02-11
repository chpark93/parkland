<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
	
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/layout/main_head.jsp"%>
<meta charset="UTF-8">
<title>Message Form</title>
</head>
<body>
	<div id="wrapper">
		<div class="container">
			<div class="row justify-content-center align-items-center my-5">
				<div class="">
					<div class="box">
					
						<form:form id="form" modelAttribute="messageVO" action="${pageContext.request.contextPath}/message/insertMessage" method="post">
							
							<div class="row gtr-uniform col-12">
								<div class="col-6">
									<form:input path="message_sender" type="text" name="message_sender" id="message_sender" value="${loginUser.nickname}"
										readonly=""/>
								</div>
								<div class="col-6">
									<form:input path="message_receiver" type="text" name="message_receiver" id="message_receiver" value="" placeholder="Receiver"/>
								</div>
								
								<!-- Break -->
								<div class="col-12">
									<form:textarea path="message_content" name="message_content" id="message_content"
										placeholder="보낼 내용을 입력 해주세요." rows="6"></form:textarea>
								</div>
								
								<!-- Break -->
								<div class="col-12">
									<ul class="actions">
										<li>
											<input type="submit" id="sendMessage" value="Send Message" class="primary">
										</li>
										<li>
											<input type="reset" value="Reset">
										</li>
									</ul>
								</div>
								
							</div>
						</form:form>	
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

<script>



/*
$(document).on('click', '#sendMessage', function(e) {
	
	var url = "${pageContext.request.contextPath}/message/insertMessage";
	
	var messageContent = $('message_content').val();
	var messageSender = $('message_sender').val();
	var messageReceiver = $('message_receiver').val();
	
	var paramData = JSON.stringify({
		"message_content" : messageContent,
		"message_sender" : messageSender,
		"message_receiver" : messageReceiver
	});
	
	var headers = {
		"Content-Type" : "application/json",
		"X-HTTP-Method-Override" : "POST"
	};
	
	if(messageContent === null || messageContent === '' ) {
		alert('내용을 입력 해주세요.');
		return false;
	}
	else {
		
		$.ajax({
			url : url,
			headers : headers,
			type : 'POST',
			data : paramData,
			dataType : 'text',
			success : function(result) {
				console.log(result);
				
				if(result === "insertSuccess") {
					alert('메시지 전송이 완료 되었습니다.');
					
				}
			},
			error : function(error) {
				console.log("에러 : " + error);
			}
		});
		
	}
	
});
*/


</script>
</html>