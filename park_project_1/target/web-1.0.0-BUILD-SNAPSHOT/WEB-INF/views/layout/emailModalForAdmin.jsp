<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!-- modal -->
<div id="emailModal" class="modal">
	
	<!-- Modal content -->
	<span id="close">&times;</span>
	<div class="modal-content">
		<div>
			<span>문의 메일</span>
		</div>
		<div class="" style="padding-top: 40px;">
			<div>
				<form id="form" action="${pageContext.request.contextPath}/email/sendEmail" method="post">
					<div class="row gtr-uniform col-12">
						<div class="col-6">
							<input type="text" name="senderMail" id="senderMail" />
						</div>
						<div class="col-6">
							<input type="text" name="receiveMail" id="receiveMail" value="qkrckdgml1993@gmail.com"
								readonly />
						</div>

						<div class="col-12">
							<input name="mailSubject" id="mailSubject" placeholder="제목을 입력 해주세요." ></input>
						</div>
					
						<div class="col-12">
							<textarea name="mailMessage" id="mailMessage" placeholder="내용을 입력 해주세요." rows="6"></textarea>
						</div>

						<div class="col-12">
							<ul class="actions">
								<li>
									<input type="submit" id="sendMail" value="Send Mail" class="primary">
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

//모달 오픈
function emailModal() {
	document.getElementById('emailModal').style.display = "block";
	
	$("#message_content").focus();
}    


//취소
$(document).on('click', '#close', function(e) {
	document.getElementById('emailModal').style.display = "none";
});

//외부 클릭
window.onclick = function(e) {
	if (e.target == document.getElementById('emailModal')) {
		document.getElementById('emailModal').style.display = "none";
	}
}

</script>
