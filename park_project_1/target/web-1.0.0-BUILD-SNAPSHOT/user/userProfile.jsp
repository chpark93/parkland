<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>      
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- Custom CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/common/css/common.css?ver=1.1" />
<%@ include file="/WEB-INF/views/layout/main_head.jsp"%>
<title>User Profile</title>

</head>
<body>
					
	<!-- Wrapper -->
	<div id="wrapper" style="height: 1200px;">	
	
	<div id="main" >
		<br/><br/>
		
		<div class="inner" style="width: 50%; height: 50%;">	       
			
			<!-- Header -->	
			<div class="align-center">
	        	<label style="margin-bottom: 50px;">
	        		<a href="${pageContext.request.contextPath}/main/mainPage">
	        			<img src="${pageContext.request.contextPath}/resources/img/chparklandImg.png" alt="Logo">
	        		</a>
	        	</label>
	        	<label style="font-size:24px;">프로필 이미지 변경</label>
	        </div>
	      
	        <div class="box">
				
				<form:form id="form" role="form" method="post" modelAttribute="userVO" enctype="multipart/form-data"
					action="${pageContext.request.contextPath}/memberShip/updateUserProfile">
						<div class="">
							<input type="hidden" name="id" value="${user.id}"/>
							<input type="hidden" name="profileImg" value="${profile}"/>
							
							<div class="box" style="display: table; margin:0 auto;">
								<div id="radius-box">
									<ul class="uploadedFileList" style="margin-right: 12px;">
									<c:if test="${profile ne null && profile ne '' }">
										<div>
									        <span class="attachment-icon has-img">
									 			<a href="../file/displayFile?fileName=${profile}" class="attachment-name">
									                <img src="../file/displayFile?fileName=${profile}" alt="Attachment"> 
									            </a>           
									        </span>
									        <div class="attachment-info">
									            <a href="${profile}" class="btn btn-default btn-xs pull-right deleteFileBtn">
									                <i class="fa fa-fw fa-remove" style="color: black;"></i>
									            </a>
									        </div>
									    </div>    
									</c:if>
									</ul>	
								</div>
							</div>
							<br/><br/>
							
							<!-- Upload Button -->
							<table style="margin-left: auto; margin-right: auto;" >
								<tbody>
									<tr>
										<td style="width : 150px;"><span>이미지 업로드</span></td>
										<td>
											<input type="file" id="userProfile" accept='image/*'/> 
										</td>
									</tr>
								</tbody>
							</table>
							
							<!-- Drop -->
							<div class="form-group">
								<div class="fileDropProfile" id="fileDropProfile" style="width: 100%; height: 150px; border: 1px dotted #0b58a2;">
									<br/>
									<br/>
									<br/>
									<p class="align-center"><i class="fa fa-paperclip"></i> 파일을 올려주세요.</p>
								</div>
							</div>
							<br/><hr>
							
						</div>
						
					<!-- button -->
					<div class="" align="right">
						<button type="submit" id="btnSubmit" class="btn btn-primary">회원 프로필 수정</button>
						&nbsp;
						<button type="button" id="btnCancel" class="btn btn-primary">취소</button>
					</div>
						
				</form:form>
			</div>
			<br/><br/><br/>
		</div>
	</div>
	</div>
<%@ include file="/WEB-INF/views/layout/main_plugins.jsp"%>
</body>

<!-- 파일 업로드 -->
<script id="fileTemplate" type="text/x-handlebars-template">
    <div>
        <span class="attachment-icon has-img">
 			<a href="{{ori_fileUrl}}" class="attachment-name">
                <img src="{{imgSrc}}" alt="Attachment"> 
            </a>           
        </span>
        <div class="attachment-info">
			<a href="{{ori_fileUrl}}" class="attachment-name" style="display: none;">
                <i class="fa fa-paperclip"></i> {{ori_fileName}} 
            </a>
            <a href="{{fullName}}" class="btn btn-default btn-xs pull-right deleteFileBtn">
                <i class="fa fa-fw fa-remove" style="color: black;"></i>
            </a>
        </div>
    </div>
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/common/js/file_upload.js?ver=1.3"></script>

<script>

$(document).ready(function(e) {
	
	//light box
	var lastTem = $(this);
	lastTem.find(".attachment-name").attr("data-lightbox", "uploadImages");

});

//단일 프로필 업로드 이벤트
$("#userProfile").change(function(e) {
	e.preventDefault();
	
	var size = $(".uploadedFileList div span").length;
	
	if(size > 0) {
		alert('프로필 사진은 한장만 업로드 가능 합니다.');
		document.getElementById("userProfile").value="";
		return false;
	}
	else if(size <= 0) {
		
		if(checkImage() == true) {
			//단일 데이터 => 첫번째 파일 저장
			var file = $('input[type=file]')[0].files[0];
			//formData 객체 생성, 저장
			var formData = new FormData();
			formData.append("file", file);
			
			//메소드 호출
			uploadFile(formData);				
			
		}
		else {
			return false;
		}		
	}
});
//이미지 체크
function checkImage() {
    var type = document.getElementById("userProfile").value;
    var pattern = /jpg$|gif$|png$|jpeg$/i;
    var res = type.match(pattern);

    if(res) {
        alert("프로필 이미지를 업로드 했습니다.");
        return true;
    }
    else {
        alert("이미지 파일만 업로드 가능합니다");
        document.getElementById("userProfile").value=""; 
    	return false;
    }
    
};

//프로필 이미지 등록
$("#form").submit(function(e){
	e.preventDefault();
	
	var lastTem = $(this);
	filesSubmit(lastTem);
});

//취소
$(document).on('click', '#btnCancel', function(e) {
	e.preventDefault();
	
	location.href = "${pageContext.request.contextPath}/mypage/getBoardListPagingFromId";
});

//파일 삭제
$(document).on('click', '.deleteFileBtn', function(e){
	e.preventDefault();
	var mode = '<c:out value="${mode}" />';
	
	if(mode == 'edit') {
		if(confirm("파일을 삭제 하시겠습니까?")){
			var lastTem = $(this);
			deleteFileModPage(lastTem, bid);
		}
	}
	else {
		var lastTem = $(this);
		deleteFilePage(lastTem);	
	}
});



	
</script>

</html>