<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/layout/main_head.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/common/css/emailWrite.css" />

<meta charset="UTF-8">
<title>Message Form</title>
</head>
<body>
	<div id="wrapper" style="height: 1200px;">
		
		<div id="main" >
			<br/><br/>				
			<div class="inner" style="height: 50%;">			
			
				<!-- Header -->	
				<div class="align-center">
		        	<label style="margin-bottom: 50px;">
		        		<a href="${pageContext.request.contextPath}/main/mainPage">
		        			<img src="${pageContext.request.contextPath}/resources/img/chparklandImg.png" alt="Logo">
		        		</a>
		        	</label>
		        	<label style="font-size:24px;">문의 메일</label>
		        </div>
				
				<div class="">
					<div class="col-6 col-12-small">						
						<div class="box">
							<form id="form" action="${pageContext.request.contextPath}/email/sendEmail" method="post">
								<div class="row gtr-uniform col-12">
								
									<input type="hidden" name="senderMail" id="senderMail" value="qkrckdgml1993@gmail.com" />
									
									<div class="col-6">
										<input type="text" name="receiveMail" id="receiveMail"
											value="qkrckdgml1993@gmail.com" readonly />
									</div>
									<div class="col-6">
										<input type="text" name="senderName" id="senderName"
											placeholder="이름"></input>
									</div>				
	
									<div class="col-12">
										<input type="text" name="mailSubject" id="mailSubject"
											placeholder="이메일을 입력 해주세요."></input>
									</div>
									
									<div class="col-12">
										<textarea name="mailMessage" id="mailMessage"
											placeholder="내용을 입력 해주세요." rows="6" style="resize : vertical;"></textarea>
									</div>
	
									<div class="col-12">
										<ul class="actions">
											<li><input type="submit" id="sendMail" value="Send Mail"
												class="primary"></li>
											<li><input type="reset" value="Reset"></li>
										</ul>
									</div>
									
									<span style="color:red;">${message}</span>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/layout/main_plugins.jsp"%>
</body>

<script>
	
</script>
</html>