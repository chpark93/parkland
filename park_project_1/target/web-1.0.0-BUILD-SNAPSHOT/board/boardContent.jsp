<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/layout/main_head.jsp"%>

<script
	src="${pageContext.request.contextPath}/resources/ckeditor/ckeditor.js?ver=1.3">
</script>

<title>Board Content</title>
</head>

<body class="is-preload">
	
	<!-- Wrapper -->
	<div id="wrapper">
		
		<!-- Main -->
		<div id="main">
		
			<div class="inner">
				
				<!-- Header -->
				<%@ include file="/WEB-INF/views/layout/header.jsp"%>
				<br/><br/>
				
					<div>
					<div class="box" id="box">
						<div class="col-6 col-12-xsmall">
							<label class="col-sm-5 control-label" style="margin-bottom : 10px; font-size : 18px"><c:out value="${boardContent.title}"/> </label>
							<hr style="margin : 10px">
						</div>
						 
						<div class="col-6 col-12-xsmall">
							<c:if test="${loginUser.id ne boardContent.reg_id}">
								<c:if test="${userInfo ne null && userInfo ne '' }">
									<span class="board_id"><a href="${pageContext.request.contextPath}/userpage/getBoardListPagingUser?nickname=${boardContent.reg_nickname}" ><c:out value="${boardContent.reg_nickname}"/></a></span> 								
								</c:if>
								<c:if test="${userInfo eq null || userInfo eq '' }">
									<span class="board_id" style="color: black;"><c:out value="${boardContent.reg_nickname}"/></span> 								
								</c:if>
							</c:if>
							<c:if test="${loginUser.id eq boardContent.reg_id}">
								<span class="board_id"><a href="${pageContext.request.contextPath}/mypage/getBoardListPagingFromId" ><c:out value="${boardContent.reg_nickname}"/></a></span> 
							</c:if>
							&nbsp; <c:out value="|" /> &nbsp;
							<span class="board_view_cnt"><c:out value="조회  : ${boardContent.view_cnt}" /></span>
							&nbsp; <c:out value="|" /> &nbsp;
							<span class="board_recommend"><c:out value="추천  : ${boardContent.recommend_cnt}" /></span>
							&nbsp; <c:out value="|" /> &nbsp;
							<span class="board_date"><c:out value="${boardContent.reg_dt }" /></span>
							
							<button id="btnRecommend" style="float: right;" >추천</button>
						</div>		
						<br/><br/>
						
						<!--Main Content -->
						<section id="intro" class="main">
							<div class="spotlight" style="height: auto;">
								<div class="content" id="content">
									${boardContent.content}
								</div>
							</div>
						</section>
						<br/><hr>
						
						<!-- 업로드  -->
						<div>
							<span><i class="far fa-save" style="font-size:20px;"></i> &nbsp; 파일 목록 </span>
						</div>
						<br/>
						
						<div class="box-footer uploadFiles row gtr-50 gtr-uniform">
							<ul class="attachments clearfix uploadedFileList" style="display: inline;"></ul>
						</div>
						
						<!-- Reply Form(start) -->
						<div class="my-3 p-3 rounded shadow-sm bg-sub box" id="replyListForm" style="padding-top: 10px;"> 			
							
							<!-- Reply List(start) -->
							<div class="my-3 p-3 rounded shadow-sm" style="padding-top: 10px; ">
								
								<!-- 댓글 갯수 -->
			                    <div class="box-header with-border">
			                        <a class="link-black text-lg">
			                        	<span><i class="fa fa-comments-o" style="color: gray; font-size: 18px;" ></i></span>
			                        	&nbsp;
			                        	<span style="color: gray;">[${boardContent.reply_view_cnt}]</span>
			                        </a>
			                    </div>
								
								<div id="replyList"></div>
								
							</div> 
							<hr>
							<!-- Reply List(end) -->				
							
							<!-- Reply Write Form -->
							<form:form name="form" id="form" role="form" modelAttribute="reply" method="post">
								<form:hidden path="bid" id="bid" />
								<input type="hidden" name="bg_no" value="${searchCriteria.bg_no}"/>								
								<input type="hidden" id="rparent_id" name="rparent_id" value=""/>
							
								<div>
									<c:if test="${loginUser.id ne null }">
										<div>
											<form:textarea path="rcontent" id="rcontent" style="resize : vertical;"
												class="form-control" rows="3" placeholder="댓글을 입력해주세요"></form:textarea>
										</div>
										<br/>
										
										<div class="row" align="right">
											<div class="col-sm-2" align="right">
												<form:input path="reg_nickname" type="hidden" id="reg_nickname" class="form-control" value="${loginUser.nickname}" />
												<form:input path="reg_id" type="hidden" id="reg_id" class="form-control" value="${loginUser.id}" />
												<button type="button" class="btn btn-sm btn-primary"
													id="btnInsertReply" style="width: 100%; margin-top: 10px" >댓글 작성</button>
											</div>
										</div>
									</c:if>
									
									<c:if test="${loginUser.id eq null }">
										<div>
											<span><a href="${pageContext.request.contextPath}/login/login">로그인</a> 후 댓글 작성이 가능합니다</span>
										</div>
									</c:if>
								</div>		
							</form:form>
							<!-- Reply Write Form(end) -->
					
							
							<!-- pagination(start) -->
							<div class="paginationBox align-center" >
								<div style="text-align: center;">
									<ul class="pagination" id="pagination" style="margin-top: 10px">
									</ul>
								</div>
							</div>
							<!-- pagination(end) -->
														
						</div>
						<!-- Reply Form(end) -->

						<!-- Button -->
						<div class="col-12" align="right">
							<div style="margin-top: 20px">
								<form role="form" method="post">
									<input type="hidden" name="bid" value="${boardContent.bid}"/>	
									
									<input type="hidden" name="bg_no" value="${searchCriteria.bg_no}"/>	
									<input type="hidden" name="page" value="${pagination.criteria.page}"/>	
									<input type="hidden" name="listSize" value="${searchCriteria.listSize}"/>	
									<input type="hidden" name="searchType" value="${searchCriteria.searchType}"/>	
									<input type="hidden" name="keyword" value="${searchCriteria.keyword}"/>		
									<input type="hidden" name="boardSection" value="${searchCriteria.boardSection}"/>		
								</form>					
								<c:if test="${loginUser.id eq boardContent.reg_id }">
									<button type="button" id="btnUpdate">수정</button>
									<button type="button" id="btnDelete">삭제</button>
								</c:if>
								<c:choose>
									<c:when test="${searchCriteria.boardSection ne '' && searchCriteria.boardSection ne null}">
										<button type="submit" id="btnList">뒤로가기</button>
									</c:when>
									<c:otherwise>
										<button type="submit" id="btnList">목록으로</button>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
					<br/><br/>
				</div>
			</div>
			</div>
			
		<!-- Sidebar -->
		<%@ include file="/WEB-INF/views/layout/sidebar.jsp"%>			
		</div>
	<%@ include file="/WEB-INF/views/layout/main_plugins.jsp"%>
</body>

<!-- script -->
<script id="fileTemplate" type="text/x-handlebars-template">
    <div style="width: 100px; float:left; margin-right: 12px;">
        <span class="attachment-icon has-img">
 			<a href="{{ori_fileUrl}}" class="attachment-name">
                <img src="{{imgSrc}}" alt="Attachment> 
            </a>           
        	<div class="attachment-info">
				<a href="{{ori_fileUrl}}" class="attachment-name" style="display: none;">
                	<i class="fa fa-paperclip"></i> {{ori_fileName}} 
           	 	</a>
        	</div>
        </span>
    </div>
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/common/js/file_upload.js?ver=1.3"></script>
<script>
	
    //목록 이동
	$(document).on('click','#btnList',function() {
			
		if('${searchCriteria.boardSection}' == '' || '${searchCriteria.boardSection}' == null) {
			
			location.href = "${pageContext.request.contextPath}/board/getBoardList?bg_no=${searchCriteria.bg_no}"
				  + "&page=" + ${searchCriteria.page}
				  + "&listSize=" + ${searchCriteria.listSize}
				  + "&searchType=${searchCriteria.searchType}"
				  + "&keyword=${searchCriteria.keyword}";			
		}	
		else {
			history.back();	
		}
	
	});

	
	//수정
	$(document).on('click', '#btnUpdate', function() {
		var url = "${pageContext.request.contextPath}/board/updateBoard";
		url = url + "?bid=" + ${boardContent.bid};
		url = url + "&mode=edit";

		location.href = url;
	});
	
	//게시글 추천 
	$(document).on('click', '#btnRecommend', function() {
		var url = "${pageContext.request.contextPath}/board/updateBoardRecommend";
		if('${searchCriteria.boardSection}' == '') {
			url = url + "?bid=" + ${boardContent.bid};			
		}
		else {
			url = url + "?bid=" + ${boardContent.bid}			
			url = url + "&boardSection=${searchCriteria.boardSection}";				
		}
		//추천 중복 검사
		var getCookie = function(name) {
			var value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
			return value? value[2] : null;
		};
 		     
		var recommendCookie = getCookie("recommend_cnt");		
	
		if(recommendCookie != null) {
			
			if(getCookie("recommend_cnt").indexOf("${boardContent.bid}") !== -1 ) {
				alert("이미 추천한 게시글 입니다.");   
				return false;
			}
			else {	
				location.href = url;
			}			
		}
		else {
			location.href = url;
		}
		
	});	
	
	//게시글 삭제
	$(document).on('click', '#btnDelete', function() {
		var url = "${pageContext.request.contextPath}/board/deleteBoard";
		url = url + "?bg_no=" + ${searchCriteria.bg_no};
		url = url + "&bid=" + ${boardContent.bid};
		
		if(confirm("정말로 삭제 하시겠습니까?")) {	
			
			//파일 전체 삭제
			var arr = [];
			$(".uploadedFileList li").each(function(){
				arr.push($(this).attr("data-src"));
			});
			
			//파일 삭제 요청
			if(arr.length > 0) {
				$.post("${pageContext.request.contextPath}/file/deleteAllFiles", {files : arr}, function(){
					
				});
			}
			
			location.href = url;		
		}
	
	});
	
	
	$(document).ready(function() {
			
			var bid = "${boardContent.bid}";
			var boardWriter = "${boardContent.reg_id}"; //글 작성자
			var page = 1; //댓글 페이지 초기화 
			
			//댓글 목록 호출
			getReplyListPaging(page);
			
			//파일 목록
			getFiles(bid);
			
			//WebSocket
			$('#btnSend').on('click', function(e) {
				e.preventDefault();
				
				if (socket.readyState !== 1) {
					return;
				}
				let msg = $('input#msg').val();
				
				socket.send(msg);
				
			});
	});
	
	/* 댓글 start */
	//댓글 리스트 페이지
	function getReplyListPaging(page) {
		
		var bid = "${boardContent.bid}";
		var replyUserInfo = "${replyUserInfo.id}";
		
		$.ajax({
			url : "${pageContext.request.contextPath}/replyBoard/" + bid + "/" + page,
			type : "GET",
			dataType : "json",
			async : false,
			success : function(data) {
		  		
			console.log(data);
			
			var htmls = "";
			$("#replyList").empty();
			
			if (data.reply.length < 1) {
				htmls = "<br>등록된 댓글이 없습니다";
			} else {
				console.log(data);
				
				$(data.reply).each(function() {	
					
					if(this.rid == this.rparent_id) {
						if(this.rdepth == 0) {
							htmls += '<hr>';	
						}
						else {
							htmls += '<br/>';
						}
						
						htmls += '<div>';
						htmls += '<div class="media text-muted pt-3 replyParent' + this.rparent_id + '" id="replyItem' + this.rid + ' " style="margin-left: ' + 5 * this.rdepth + 'px;">'; 				
						htmls += '<div class="media text-muted pt-3" id="rid' + this.rid + '">';
						htmls += '<div class="media text-muted pt-3" id="replyId" value="'  + this.reg_nickname +  '"></div>';  
						htmls += '<div style="float:left">';
						
						if(this.reg_id == '${loginUser.id}') {
							htmls += '<strong style="color: gray;">' + this.reg_nickname + '</strong>';															
						}
						else {				
							htmls += '<strong style="color: gray;"><a href="${pageContext.request.contextPath}/userpage/getBoardListPagingUser?nickname=' + this.reg_nickname + '">' + this.reg_nickname + '</a></strong>';									
							
						}
						
						htmls += '&nbsp; <span>' + this.reg_dt + '</span>';
						htmls += '</div>';
						
						if(this.reg_id == '${loginUser.id}') {
							htmls += '<div style="float:right">';
							htmls += '<a href="#" onclick="fn_editReply(' + this.rid + ', \'' + this.reg_nickname + '\', \'' + this.rcontent + '\', \'' + page + '\' ); return false;" style="padding-right:5px">수정</a>';
							htmls += '&nbsp;';
							htmls += '<a href="#" onclick="fn_deleteReply(' + this.rid + ', \'' + this.rparent_id + '\', \'' + page + '\'); return false;" >삭제</a>';
							htmls += '</div>';
							
						}
						//대댓글
						if('${loginUser.id}' != null && this.reg_nickname != '삭제 된 댓글 입니다.') {
							
							htmls += '&nbsp;';
							htmls += '<div style="float:right">';
							htmls += '<a href="#" onclick="fn_reReply(' + this.rid + ', \'' + this.reg_nickname + '\', \'' + page + '\'); return false;" style="padding-right:5px">답글</a>';
							htmls += '</div>';
						}
						
						htmls += '</div>';
						htmls += '<br>';
						htmls += '<div style="color: black;">';
						htmls += this.rcontent;
						htmls += '</div>';
						htmls += '</div>';
						htmls += '</div>';
						
					}
					else if(this.rid != this.rparent_id) {
						
						if(this.rdepth == 0) {
							htmls += '<hr>';	
						}
						else {
							htmls += '<br/>';
						}
						
						htmls += '<div>';
						htmls += '<div class="media text-muted pt-3 replyParent' + this.rparent_id + '" id="replyItem' + this.rid + ' " style="margin-left: 5px;">'; 				
						htmls += '<div class="media text-muted pt-3" id="rid' + this.rid + '">';
						htmls += '<div class="media text-muted pt-3" id="replyId" value="'  + this.reg_nickname +  '"></div>';	  
						htmls += '<div style="float:left">';
						htmls += '<i class="fas fa-reply fa-rotate-180" style="font-family: FontAwesome;"></i> &nbsp;&nbsp;';
						
						if(this.reg_id == '${loginUser.id}') {
							htmls += '<strong style="color: gray;">' + this.reg_nickname + '</strong>';															
						}
						else {
							htmls += '<strong><a href="${pageContext.request.contextPath}/userpage/getBoardListPagingUser?nickname=' + this.reg_nickname + '">' + this.reg_nickname + '</a></strong>';									
						}
						
						htmls += '&nbsp; <span>' + this.reg_dt + '</span>';
						htmls += '</div>';
						
						if(this.reg_id == '${loginUser.id}') {
							htmls += '<div style="float:right">';
							htmls += '<a href="#" onclick="fn_editReply(' + this.rid + ', \'' + this.reg_nickname + '\', \'' + this.rcontent + '\', \'' + page + '\' ); return false;" style="padding-right:5px">수정</a>';
							htmls += '&nbsp;';
							htmls += '<a href="#" onclick="fn_deleteReply(' + this.rid + ', \'' + this.rparent_id + '\', \'' + page + '\'); return false;" >삭제</a>';
							htmls += '</div>';
						}
						//대댓글
						if('${loginUser.id}' != null) {
							htmls += '&nbsp;';
							htmls += '<div style="float:right">';
							htmls += '<a href="#" onclick="fn_reReply(' + this.rid + ', \'' + this.reg_nickname + '\', \'' + page + '\'); return false;" style="padding-right:5px">답글</a>';
							htmls += '</div>';
						}
						
						htmls += '</div>';
						htmls += '<br>';
						htmls += '<div style="color: black; margin-left: 25px;">';
						
						if(this.rdepth >= 2) {
							if(this.replytarget_id == '${loginUser.nickname}') {
								htmls += '<a href="${pageContext.request.contextPath}/mypage/getBoardListPagingFromId" style="padding-right:5px";>' + this.replytarget_id + '</a>';
							}
							else {
								htmls += '<a href="${pageContext.request.contextPath}/userpage/getBoardListPagingUser?nickname=' + this.replytarget_id + '" style="padding-right:5px";>' + this.replytarget_id + '</a>';																	
							}
						}     
						
						htmls += this.rcontent;
						htmls += '</div>';
						htmls += '</div>';
						htmls += '</div>';
					}
						
				});
					
			}
			$("#replyList").html(htmls);
			$(".replyParent" + this.rparent_id).last().after(htmls);
			
			
			//페이지 호출
			getReplyPaging(data.pagination);
			
			//댓글 갯수
			getReplyCnt(data.pagination);
			
			}
		});	
		
	}
	
	//댓글 갯수
	function getReplyCnt(pagination) {
		
		var replyCnt = $("#replyCnt");
		
		if(pagination.listCnt === 0) {
			replyCnt.html("");	
			return;
		}
		replyCnt.html(" 댓글 [" + pagination.listCnt + "]");
		
	}
	
	
	//댓글 insert
	$(document).on('click', '#btnInsertReply', function() {
		
		var url = "${pageContext.request.contextPath}/replyBoard/insertReply";
		
		var replyContent = $('#rcontent').val();
		var replyReg_id = $('#reg_id').val();
		var replyReg_nickname = $('#reg_nickname').val();
		
		var boardWriter = "${boardContent.reg_id}"; //글 작성자
 
		
		var bid = "${boardContent.bid}";
		
		var paramData = JSON.stringify({
			"rcontent" : replyContent,
			"reg_id" : replyReg_id,
			"reg_nickname" : replyReg_nickname,
			"bid" : '${boardContent.bid}',
			"rdepth" : "0"
		});
		var headers = {
			"Content-Type" : "application/json",
			"X-HTTP-Method-Override" : "POST"
		};
		
		if(replyContent === null || replyContent === '') {
			alert('댓글 내용을 입력해주세요.');
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
						alert("댓글이 등록되었습니다.");
						
						page = 1; //초기화
						getReplyListPaging(page);
						
						$('#rcontent').val(""); //댓글 입력창 공백 
						
						//댓글 webSocket
						console.log("reply socket : ", socket);
						
						if(socket) {
							//websocket에 보냄(reply, 댓글 작성자, 게시글 작성자, 글 번호)
							var socketMsg = "reply," + replyReg_nickname + "," + boardWriter + "," + bid;
							console.log("socketMsg : " + socketMsg);
							
							socket.send(socketMsg);
						}
						
					}
					
				},
				error : function(error) {
					console.log("에러 : " + error);
				}
				
			});	
		}

	});
	
	
	//댓글 수정 이벤트
	function fn_editReply(rid, reg_nickname, rcontent, page) {
		
		var htmls = "";
		var modifyDiv = $("#rid" + rid).html();
		  
		
		htmls += '<div class="media text-muted pt-3" id="rid' + rid + ' ">';
		
		htmls += '<div style="float : left">';
		htmls += '<strong style="color: gray;">' + reg_nickname + '</strong>';
		htmls += '</div>';
		htmls += '<div style="float : right">';
		htmls += '<a href="#" onclick="fn_updateReply(' + rid
				+ ', \'' + reg_nickname + '\', \'' + page + '\'); return false;" style="padding-right:5px">작성</a>';
		htmls += '&nbsp;';		
		htmls += '<a href="#" onclick="getReplyListPaging(' + page + '); return false;">취소<a>';
		htmls += '</div>';
		htmls += '<br/>';
		htmls += '<textarea name="editContent" id="editContent" class="form-control" rows="3" style="color : black; resize : vertical;">';
		htmls += rcontent;
		htmls += '</textarea>';
		htmls += '</div>';
		
		
		getReplyListPaging(page);
		$('#rid' + rid).replaceWith(htmls);
		$('#rid' + rid + '#editContent').focus();
		
	}
	
	
	function fn_updateReply(rid, reg_id, page) {
		var url = "${pageContext.request.contextPath}/replyBoard/updateReply";
		
		var replyEditContent = $('#editContent').val();
		var paramData = JSON.stringify({
			"rcontent" : replyEditContent,
			"rid" : rid
		});
		var headers = {
			"Content-Type" : "application/json",
			"X-HTTP-Method-Override" : "PUT"
		};
		
		$.ajax({
			url : url,
			headers : headers,
			data : paramData,
			type : 'PUT',
			dataType : 'text',
			success : function(result) {
				console.log(result);
				if(result === "updateSuccess") {
					alert("댓글이 수정 되었습니다.");
					
					getReplyListPaging(page);
				}
					
			},
			error : function(error) {
				console.log("error : " + error);
			}
			
		});
					
	}
	
	
	//댓글 삭제 이벤트
	function fn_deleteReply(rid, rparent_id, page) {
		
		var paramData = JSON.stringify({
			"rid" : rid,
			"rparent_id" : rparent_id
		});
		
		$.ajax({
			url : "${pageContext.request.contextPath}/replyBoard/deleteReply",
			headers: {
                "Content-Type": "application/json",
                "X-HTTP-Method-Override": "DELETE"
            },
			data : paramData,
			type : 'DELETE',
			dataType : 'text',
			success : function(result) {
				if(result === "deleteSuccess") {
					alert("댓글이 삭제 되었습니다.");
		
				}
				
				getReplyListPaging(page);
				
			},
			error : function(error) {
				console.log("error : " + error);
			}

		});
		
	}
	/* 댓글 end */

	//pagination(start)  
	function getReplyPaging(pagination) {
		var str ="";
		
		if(pagination.prevPage) {
			str += '<li>';
			str += '<a class="button" href="#" onclick="getReplyListPaging(' + (pagination.startPage - 1) + '); return false;">이전</a>';
			str += '</li>';
		}	 
		
		for(var idx = pagination.startPage; idx <= pagination.endPage; idx++) { 
			var strClass = pagination.criteria.page == idx ? 'active' : '';
			
			str += '<li>';
			str += '<a class="page ' + strClass + '" href="#" onclick="getReplyListPaging(' + idx + '); return false;" >' + idx + '</li>';
			str += '</li>';
		}
		
		if(pagination.nextPage) {
			str += '<li>';
			str += '<a class="button" href="#" onclick="getReplyListPaging(' + (pagination.endPage + 1) + '); return false;">다음</a>';
			str += '</li>';
		}	
		
		$(".pagination").html(str);
	} 
	/* pagination(end) */
    	

	
	/* 대댓글  */
	function fn_reReply(rid, reg_nickname, page) {
		getReplyListPaging(page); 		
		
		var rparent_id = $("#rparent_id").val(rid);
		var reReplyReg_id = $('#reg_id').val();
		var reReplyReg_nickname = $('#reg_nickname').val();
		var reReplyTarget_id = $('div #replyId').val(reg_nickname);
		
		
		var htmls = "";
		var reReplyDiv = $("#reReplyDiv").html();
		
		
		htmls += '<div id="reReplyDiv">';
		htmls += '<div class="media text-muted pt-3" id="rid' + rid + ' ">';
		htmls += '<div class="media text-muted pt-3" id="replyId" value="'  + reReplyReg_nickname +  '"></div>';
		htmls += '<div style="float : left">';
		htmls += '<strong style="color: gray;">' + reReplyReg_nickname + '</strong>';
		htmls += '</div>';
		htmls += '<div style="float : right">';
		htmls += '<a href="#" onclick="fn_insertReReply(' + rid + ', \'' + reReplyReg_nickname + '\', \'' + page + '\'); return false;" style="padding-right:5px">작성</a>';
		htmls += '&nbsp;';		
		htmls += '<a href="#" onclick="getReplyListPaging(' + page + '); return false;">취소<a>';
		htmls += '</div>';
		htmls += '<br/>';
		htmls += '<textarea name="rcontent" id="rcontent2" class="form-control" rows="3" style="color : black; resize : vertical;">';
		htmls += '</textarea>';
		htmls += '</div>';
		htmls += '</div>';
			
		
		$('#rid' + rid).html(htmls);	
		$("#rcontent2").val("");
		$("#rcontent2").focus();
		
	}
	
	function fn_insertReReply(rid, reg_nickname, page) {
		var url = "${pageContext.request.contextPath}/replyBoard/insertReply";
		
		var boardWriter = "${boardContent.reg_id}"; //글 작성자
		var reReplyContent = $('#rcontent2').val();
		var reReplyReg_id = $('#reg_id').val();
		var reReplyReg_nickname = $('#reg_nickname').val();
		var reReplyTarget_id = $('div #replyId').val();		
		var bid = "${boardContent.bid}";
		var rparent_id = $("#rparent_id").val();
		
		    
		var paramData = JSON.stringify({
			"bid" : "${boardContent.bid}",
			"rid" : rid,		
			"page" : page,
			"rparent_id" : rparent_id,			
			"rcontent" : reReplyContent,
			"reg_id" : reReplyReg_id,
			"reg_nickname" : reReplyReg_nickname,
			"replytarget_id" : reReplyTarget_id
		});
			
		var headers = {
			"Content-Type" : "application/json",
			"X-HTTP-Method-Override" : "POST"
		};
		
		var htmls = "";
		
		if(reReplyContent === null || reReplyContent === '') {
			alert('댓글 내용을 입력해주세요.');
			return false;
		}
		else {
		$.ajax({
			url : url,
			headers : headers,
			data : paramData,
			type : 'POST', 
			async : false,
			success : function(result) {
				console.log(result);
				
				if(result === "insertSuccess") {
					alert("대댓글이 작성 되었습니다.");
					
					
					//댓글 webSocket
					console.log("reply socket : ", socket);			
					if(socket) {
						//websocket에 보냄(reply, 댓글 작성자, 게시글 작성자, 글 번호)
						var socketMsg = "reply," + reReplyReg_nickname + "," + boardWriter + "," + bid;
						console.log("socketMsg : " + socketMsg);
						
						socket.send(socketMsg);
					}
					
				}	
			},
			error : function(error) {
				console.log("error : " + error);
			}
		
		});		
		getReplyListPaging(page);
		
		}
	}
	/* 대댓글 end */
	

</script>

</html>