<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- modal -->
<div id="messageModal" class="modal">

	<!-- Modal content -->
	<span id="close">&times;</span>
	<div class="modal-content">
		<div>
			<span>쪽지 보내기</span>
		</div>
		<div class="" style="padding-top: 40px;">
			<div>
				<form id="messageForm" action="${pageContext.request.contextPath}/message/insertMessage" method="post">
					<div class="row gtr-uniform col-12">
						<div class="col-6">
							<input type="text" name="message_sender" id="message_sender" value="${loginUser.nickname}" readonly />
						</div>
						
						<!-- receive page -->
						<c:if test="${messageReceiveContent.message_receiver ne messageReceiveContent.message_sender}">	
							<c:if test="${messageReceiveContent.message_receiver ne null && messageReceiveContent.message_receiver ne ''}">
								<c:if test="${loginUser.nickname eq messageReceiveContent.message_receiver}">
									<div class="col-6">
										<input type="text" name="message_receiver" id="message_receiver" value="${messageReceiveContent.message_sender}" />
									</div>
								</c:if>
							</c:if>
						</c:if>
												
						<!-- send page -->
						<c:if test="${messageSendContent.message_receiver ne messageSendContent.message_sender}">
							<c:if test="${messageSendContent.message_sender ne null && messageSendContent.message_sender ne '' }">
								<c:if test="${loginUser.nickname eq messageSendContent.message_sender}">
									<div class="col-6">
										<input type="text" name="message_receiver" id="message_receiver" value="${messageSendContent.message_receiver}" />
									</div>
								</c:if>
							</c:if>
						</c:if>
							
						<!-- receiver null -->						
						<!-- 자기 자신한테 보냈을 때 -->
						<c:if test="${messageReceiveContent.message_receiver eq messageReceiveContent.message_sender}">
							<c:if test="${messageSendContent.message_receiver eq messageSendContent.message_sender}">
								<c:if test="${loginUser.nickname eq messageReceiveContent.message_receiver || loginUser.nickname eq messageSendContent.message_receiver}">
									<div class="col-6">
										<input type="text" name="message_receiver" id="message_receiver" value="${loginUser.nickname}"/>
									</div>
								</c:if>
								<c:if test="${messageReceiveContent.message_receiver eq null || messageReceiveContent.message_receiver eq '' }">
									<c:if test="${messageSendContent.message_receiver eq null || messageSendContent.message_receiver eq '' }">
										<div class="col-6">
											<input type="text" name="message_receiver" id="message_receiver" placeholder="수신자 닉네임"/>
										</div>
									</c:if>
								</c:if>
							</c:if>
						</c:if>
					
						
						
						<!-- Content -->
						<div class="col-12">
							<textarea class="message_content" name="message_content" id="message_content" placeholder="내용을 입력 해주세요." rows="6"></textarea>
						</div>
						
						<!-- Button -->
						<div class="col-12">
							<ul class="actions">
								<li>
									<input type="button" id="sendMessage" value="Send Message" class="primary">
								</li>
								<li>
									<input type="reset" value="Reset">
								</li>
							</ul>
						</div>

					</div>
				</form>
			</div>
		</div>
	</div>
</div>

<script>
//쪽지 작성
$(document).on('click', '#sendMessage', function(e){
		e.preventDefault();
					
		if($(".message_content").val() == '' ) {
			alert('내용을 입력 해주세요.')
			return false
		}
		else {
			$("#messageForm").submit();
			
		}
	});


//오픈
function messageModal() {
	document.getElementById('messageModal').style.display = "block";
	
	$("#message_content").focus();
}

//취소
$(document).on('click', '#close', function(e) {
	document.getElementById('messageModal').style.display = "none";
});


//외부 클릭
window.onclick = function(e) {
	if (e.target == document.getElementById('messageModal')) {
		document.getElementById('messageModal').style.display = "none";
	}
}

</script>