//handlebars
var fileTemplate = Handlebars.compile($("#fileTemplate").html());

//첨부 파일 드래그 영역
var fileDropDiv = $(".fileDrop");

$(".content-wrapper").on("dragenter dragover drop", function(e){
	e.preventDefault();
});

fileDropDiv.on("dragenter dragover", function(e){
	e.preventDefault();
});

//첨부파일 drag 이벤트 : 업로드  => 출력
fileDropDiv.on("drop", function(e){
	e.preventDefault();
	
	//drop시에 전달된 데이터
	var files = e.originalEvent.dataTransfer.files;
	//단일 데이터 => 첫번째 파일 저장
	var file = files[0];
	//formData 객체 생성, 저장
	var formData = new FormData();
	formData.append("file", file);
	
	//메소드 호출
	uploadFile(formData);
});


//프로필 이미지 drag 이벤트
$('.fileDropProfile').on("dragenter dragover", function(e){
	e.preventDefault();
});


$('.fileDropProfile').on("drop", function(e){
	e.preventDefault();
	
	var size = $(".uploadedFileList div span").length;
	var pattern = /jpg$|gif$|png$|jpeg$/i;
	
	if(size > 0) {
		alert('프로필 사진은 한장만 업로드 가능 합니다.');
		return false;
	}
	else if(size <= 0) {
			
		//drop 전달된 데이터
		var files = e.originalEvent.dataTransfer.files;			
		//단일 데이터 => 첫번째 파일 저장
		var file = files[0];
		//formData 객체 생성, 저장
		var formData = new FormData();
		formData.append("file", file);
		
		//메소드 호출
		uploadFile(formData);
		
	
	}
});


function uploadFile(formData) {
	
	var url = "/file/uploadFile";
	
	$.ajax({	
		url : url,
		type : "POST",
		data : formData,
		dataType : "text",
		//processData : 데이터를 일반적인 String으로 변환할 것인가
		processData : false,
		//multipart/form-data 방식으로 전송하기 위해 false
		contentType : false,
		success : function(data) {
			printFiles(data);	
			$(".noAttach").remove();
		}
		
	});
}

//출력 메서드
function printFiles(data) {
	//파일 정보 처리
	var fileInfo = getFileInfo(data);
	//handlebar 템플릿에 파일 정보 바인딩
	var html = fileTemplate(fileInfo);
	
	//handlebar 컴파일로 생성된 html을 DOM에 넣음
	$(".uploadedFileList").append(html);
	
	//이미지 파일인 경우 템플릿에 lightbox 속성 추가
	if(fileInfo.fullName.substr(12, 2) === "s_") {
		//마지막 추가 첨부파일 템플릿 
		var lastTem = $(".uploadedFileList div").last();
		//lightbox
		lastTem.find(".attachment-name").attr("data-lightbox", "uploadImages");
		//이미지 아이콘으로 변경
		lastTem.find(".fa-paperclip").attr("class", "fa fa-camera");
	}
}


//게시글 submit
function filesSubmit(lastTem) {
	var str = "";
	$(".uploadedFileList .deleteFileBtn").each(function(idx){
		str += "<input type='hidden' name='files[" + idx + "]' value='" + $(this).attr("href") + "'>"
	});
	
	lastTem.append(str);
	lastTem.get(0).submit();
}


//파일 목록(게시글)
function getFiles(bid) {
	$.getJSON("/file/getFiles/" + bid, function(list){
		 
		if(list.length === 0) {
			$(".uploadedFileList").html("<span class='noAttach'>첨부파일이 없습니다</span>");
		}
		$(list).each(function(){
			printFiles(this);
		})
	});
} 



//파일 삭제(boardForm)
function deleteFilePage(lastTem) {
	var url = "/file/deleteFile";
	
	deleteFile(url, lastTem);
}

//파일 삭제(updateBoard)
function deleteFileModPage(lastTem, bid) {
	var url = "/file/deleteFile/" + bid;
	
	deleteFile(url, lastTem);
}

function deleteFile(url, lastTem) {
	
	$.ajax({
		
		url : url,
		type : "POST",
		data : {fileName : lastTem.attr("href")},
		dataType : "text",
		success : function(result) {
			if(result === "delete") {
				alert("삭제 되었습니다.");
				
				lastTem.parents("ul div").remove();
			}
		}
	});
}


//파일 정보 
function getFileInfo(fullName) {
	
	var ori_fileName; //출력 파일명
	var imgSrc; //이미지 파일 요청 URL
	var ori_fileUrl; //원본파일 요청 URL
	var uuidFileName; //나머지 파일명(UUID_파일명.확장자)
	
	//이미지
	if(checkImgType(fullName)) {
		//썸네일 링크
		imgSrc = "/file/displayFile?fileName=" + fullName;
		uuidFileName = fullName.substr(12);
		
		var ori_img = fullName.substr(0, 12) + fullName.substr(12);
		//원본 이미지 링크
		ori_fileUrl = "/file/displayFile?fileName=" + ori_img;
	}
	else {
		imgSrc = "/resources/img/fileIcon.jpg";
		uuidFileName = fullName.substr(12);
		
		//파일 다운로드 링크
		ori_fileUrl = "../file/displayFile?fileName=" + fullName;
	}
	
	ori_fileName = uuidFileName.substr(uuidFileName.indexOf("_") + 1);
	
	return {
			ori_fileName : ori_fileName,
			imgSrc : imgSrc,
			ori_fileUrl : ori_fileUrl,
			fullName : fullName
		   };
	
}

//이미지 파일 유무
function checkImgType(fullName) {
	var pattern = /jpg$|gif$|png$|jpeg$/i;
	
	return fullName.match(pattern);
}








