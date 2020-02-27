<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- modal -->
<div id="enquireModal" class="modal">

	<!-- Modal content -->
	<span id="close">&times;</span>
	<div class="modal-content">
		<div>
			<span>문의 쪽지</span>
		</div>
		<div class="" style="padding-top: 40px;">
			<div>
				<form id="form" action="${pageContext.request.contextPath}/message/insertMessage" method="post">
					<div class="row gtr-uniform col-12">
						<div class="col-6">
							<input type="text" name="message_sender" id="message_sender" value="${loginUser.nickname}" readonly />
						</div>
						<div class="col-6">
							<input type="text" name="message_receiver" id="message_receiver" value="관리자"
								readonly />
						</div>

						<!-- Break -->
						<div class="col-12">
							<textarea class="message_content_admin" name="message_content" id="message_content" placeholder="내용을 입력 해주세요." rows="6"></textarea>
						</div>
						
						<!-- Break -->
						<div class="col-12">
							<ul class="actions">
								<li>
									<input type="button" id="sendMessageAdmin" value="Send Message" class="primary">
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
$(document).on('click', '#sendMessageAdmin', function(e){
		e.preventDefault();
		
		if($(".message_content_admin").val() == '') {
			alert('내용을 입력 해주세요.')
			return false
		}			
		else {
			$("#form").submit();
			
		}
	});

//모달 오픈
function enquireModal() {
	document.getElementById('enquireModal').style.display = "block";
	
	$("#message_content").focus();
}    


//취소
$(document).on('click', '#close', function(e) {
	document.getElementById('enquireModal').style.display = "none";
});

//외부 클릭
window.onclick = function(e) {
	if (e.target == document.getElementById('enquireModal')) {
		document.getElementById('enquireModal').style.display = "none";
	}
}

</script>
