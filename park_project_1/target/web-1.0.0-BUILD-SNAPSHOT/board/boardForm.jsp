<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/layout/main_head.jsp"%>

<!-- 위지윅 에디터 -->
<script
	src="${pageContext.request.contextPath}/resources/ckeditor/ckeditor.js?ver=1.4">
</script>

<title>boardForm</title>
</head>
<body class="is-preload">

	<!-- Wrapper -->
	<div id="wrapper">

		<!-- Main -->
		<div id="main">
			<div class="inner">
				<!-- Header -->
				<%@ include file="/WEB-INF/views/layout/header.jsp"%>
				<br />
				<article>
					<div class="container" role="main">
						<br>
						<h2>board Form</h2>
						<br>

						<div>
							<form:form name="form" id="form" role="form" modelAttribute="board" method="post" style="width:70%" action="${pageContext.request.contextPath}/board/saveBoard" enctype="multipart/form-data">
								<form:hidden path="bid" />
								<input type="hidden" name="mode" />
																											
								<input type="hidden" name="bid" value="${board.bid}">
								<input type="hidden" name="bg_no" value="${searchCriteria.bg_no}">
								<input type="hidden" name="page" value="${searchCriteria.page}">
	                            <input type="hidden" name="listSize" value="${searchCriteria.listSize}">
	                            <input type="hidden" name="searchType" value="${searchCriteria.searchType}">
	                            <input type="hidden" name="keyword" value="${searchCriteria.keyword}">
								
								<div class="row gtr-uniform">
									<div class="col-6 col-12-xsmall">
										<label for="reg_id">작성자</label>
										<c:choose>
											<c:when test="${mode != 'edit'}">
												<form:input path="reg_id" type="hidden" class="form-control" id="reg_id" value="${user.id}"/>
												<form:input path="reg_nickname" type="text" class="form-control" id="reg_nickname" value="${user.nickname}" readonly="true" />												
											</c:when>
											<c:when test="${mode == 'edit'}">
												<form:input path="reg_id" type="hidden" class="form-control" id="reg_id" value="${boardContent.reg_id}"/>
												<form:input path="reg_nickname" type="text" class="form-control" id="reg_nickname" value="${boardContent.reg_nickname}" readonly="true" />
											</c:when>
										</c:choose>
									</div>

									<div class="col-6 col-12-xsmall">
										<label for="category">카테고리</label>
										<c:if test="${not empty boardGroupList}">
											<select name="category" id="category">
												<c:forEach var="list" items="${boardGroupList}" varStatus="i">
													<option value="${list.bg_no}" <c:if test="${list.bg_no eq searchCriteria.bg_no}">selected='selected'</c:if> >${list.bg_name}</option>
												</c:forEach>
											</select>
										</c:if>							
									</div>
								</div>
								<br/>
								
								<div>
									<label for="title">제목</label>
									<form:input path="title" type="text" class="form-control" id="title" value="${boardContent.title}" onkeyup="checkWord(this, 80)" placeholder="제목을 입력해 주세요" />
								</div>
								<br />
								
								<div>
									<label for="content">내용</label>
									<c:if test="${mode ne 'edit'}">
										<form:textarea path="content" class="form-control" id="content" />	
									</c:if>	
									<c:if test="${mode eq 'edit'}">	
										<form:textarea path="content" class="form-control" id="content" value="${boardContent.content}"></form:textarea> 
									</c:if>
									<script type="text/javascript">
											CKEDITOR.replace('content', {
												filebrowserUploadUrl : "${pageContext.request.contextPath}/ckeditorUpload/ckeditorUpload"
											});
									</script>
								</div>
								<br/><br/>
								
								<!-- Upload Button -->								
								<div>
									<div> 
										<input type="file" id="boardFileUpload" value="파일 업로드" multiple/> 
									</div>
								</div>
								<br/><br/>
								
								<!-- 첨부 파일 -->
								<div class="form-group">
									<div class="fileDrop" style="width: 100%; height: 200px; border: 1px dotted #0b58a2;" >
										<br/>
										<br/>
										<br/>
										<br/>
										<p class="align-center"><i class="fa fa-paperclip"></i>파일을 올려주세요.</p>
									</div>
								</div>
								<br/>
								
								<div class="box-footer">      
					                <ul class="attachments clearfix uploadedFileList" style="display: inline;"></ul>
					            </div>
					            <br/><br/>
					            
					            <!-- 게시글 버튼  -->
					            <div style="clear: both; text-align: right;">
									<button type="button" class="btn btn-sm btn-primary" id="btnSave">작성</button>
									<button type="button" class="btn btn-sm btn-primary" id="btnList">목록</button>
								</div>
								
							</form:form>
						</div>
					</div>
					
				</article>
			</div>
		</div>
		<!-- Sidebar -->
		<%@ include file="/WEB-INF/views/layout/sidebar.jsp"%>
	</div>
	<%@ include file="/WEB-INF/views/layout/main_plugins.jsp"%>
</body>

<!-- script (start) -->
<!-- 파일 업로드 -->
<script id="fileTemplate" type="text/x-handlebars-template">
    <div style="width: 100px; float:left; margin-right: 12px;">
        <span class="attachment-icon has-img">
        	<div class="attachment-info">
 				<a href="{{ori_fileUrl}}" class="attachment-name">
     	           <img src="{{imgSrc}}" alt="Attachment" style="width: 100px; height: 100px;"> 
       	     	</a>           
        	</div>
			<a href="{{fullName}}" class="btn btn-default btn-xs pull-right deleteFileBtn">
                <i class="fa fa-fw fa-remove"></i>
            </a>
        </span>
    </div>
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/common/js/file_upload.js?ver=1.5"></script>
<script>
	
$(document).ready(function() {
	
	var bid = "${board.bid}";
	var mode = '<c:out value="${mode}" />';
	
	//수정 폼 세팅	
	if (mode == 'edit') {
		
		//파일 목록
		getFiles(bid);
		
		//입력 폼 세팅
		$("#reg_id").prop('readonly', true);
		$("input:hidden[name='bid']").val('<c:out value="${boardContent.bid}" />');
		$("input:hidden[name='mode']").val('<c:out value="${mode}" />');
		$("#reg_id").val('<c:out value="${boardContent.reg_id}" />');
		$("#title").val('<c:out value="${boardContent.title}" />');
		
	}	
	
	//글 작성
	$(document).on('click', '#btnSave', function(e){
		e.preventDefault();
		
		if($("#title").val() == '' || $("#title").val() == null) {
			alert('글 제목을 입력 해주세요.')
			return false
		}
		else if(CKEDITOR.instances.content.getData() == '' || CKEDITOR.instances.content.getData().length == 0 ) {
			alert('내용을 입력해주세요.');
			return false
		}
		else {
			$("#form").submit();
			
			var lastTem = $(this);
			filesSubmit(lastTem);
		}
	});
	
	
	//목록으로
	$(document).on('click','#btnList',function(e) {
		e.preventDefault();

		location.href = "${pageContext.request.contextPath}/board/getBoardList?bg_no=${searchCriteria.bg_no}"
		 			  + "&page=" + ${searchCriteria.page}
					  + "&listSize=" + ${searchCriteria.listSize}
		  			  + "&searchType=${searchCriteria.searchType}"
		  			  + "&keyword=${searchCriteria.keyword}";	
	});
	
	//파일 삭제
	$(document).on('click', ".deleteFileBtn", function(e){
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
	
});

	//글자 수 제한 
	function checkWord(obj, maxByte) {
		
		var strValue = obj.value;
		var strLen = strValue.length;
		var totalByte = 0;
		var len = 0;
		var oneChar = "";
		var str2 = "";
		
		for(var i = 0; i < strLen; i++) {
			oneChar = strValue.charAt(i);		
			
			if(escape(oneChar).length > 4) {
				totalByte += 2;	
			}else {
				totalByte++;
			}
			
			if(totalByte <= maxByte) {
				len = i + 1;
			}
		}
	
		if (totalByte > maxByte) {
			alert( (maxByte/2) + "자를 초과하여 입력하실 수 없습니다.");
			str2 = strValue.substr(0, len);
			obj.value = str2;
			checkWord(obj, 4000);
		}
	}
</script>

<script>

//업로드 버튼 이벤트
$("#boardFileUpload").change(function(e) {
	e.preventDefault();
	
	var size = $(".uploadedFileList div span").length;
	
	if(size > 5) {
		alert('파일은 최대 5개 까지 업로드 가능 합니다.');
		document.getElementById("boardFileUpload").value="";
		return false;
	}
	else if(size <= 5) {	
	
		//단일 데이터 => 첫번째 파일 저장
		var file = $('input[type=file]')[0].files[0];
		//formData 객체 생성, 저장
		var formData = new FormData();
		formData.append("file", file);
		
		//메소드 호출
		uploadFile(formData);				
				
	}
});

//프로필 이미지 등록
$("#form").submit(function(e){
	e.preventDefault();
	
	var lastTem = $(this);
	filesSubmit(lastTem);
});


</script>
<!-- script (end) -->

</html>