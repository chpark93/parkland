<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/layout/main_head.jsp"%>
<meta charset="UTF-8">
<title>Message Form</title>
</head>
<body>
	<div id="wrapper">
		
		<div id="main" class="wrapper" >
			<div class="inner">
			<br/><br/>
			
				<div class="align-center">
					<h2>문의 메일</h2> 
				</div>
				<br/>
				
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
											placeholder="제목을 입력 해주세요."></input>
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