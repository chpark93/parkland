<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- modal -->
<div id="messageModalUserPage" class="modal">

	<!-- Modal content -->
	<span id="close">&times;</span>
	<div class="modal-content">
		<div>
			<span>쪽지 보내기</span>
		</div>
		<div class="" style="padding-top: 40px;">
			<div>
				<form id="messageUserForm" action="${pageContext.request.contextPath}/message/insertMessage" method="post">
					<div class="row gtr-uniform col-12">
						
						<div class="col-6">
							<input type="text" name="message_sender" id="message_sender" value="${loginUser.nickname}" readonly />
						</div>
						
						<!-- userPage -->	
						<div class="col-6">
							<input type="text" name="message_receiver" id="message_receiver" value="${user.nickname}" readonly/>
						</div>
						
						
						<!-- Content -->
						<div class="col-12">
							<textarea class="message_content_user" name="message_content" id="message_content" placeholder="내용을 입력 해주세요." rows="6"></textarea>
						</div>
						
						<!-- Button -->
						<div class="col-12">
							<ul class="actions">
								<li>
									<input type="button" id="sendMessageUser" value="Send Message" class="primary">
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
$(document).on('click', '#sendMessageUser', function(e){
		e.preventDefault();
		
		if($(".message_content_user").val() == '' ) {
			alert('내용을 입력 해주세요.')
			return false
		}
		else {
			$("#form").submit();
			
		}
	});


//오픈
function messageModalUserPage() {
	document.getElementById('messageModalUserPage').style.display = "block";
	
	$("#message_content").focus();
}

//취소
$(document).on('click', '#close', function(e) {
	document.getElementById('messageModalUserPage').style.display = "none";
});


//외부 클릭
window.onclick = function(e) {
	if (e.target == document.getElementById('messageModalUserPage')) {
		document.getElementById('messageModalUserPage').style.display = "none";
	}
}

</script>