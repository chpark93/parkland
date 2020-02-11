<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/layout/main_head.jsp"%>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body class="is-preload">
	<!-- Wrapper -->
	<div id="wrapper">

		<!-- Main -->
		<div id="main">
			<div class="inner">
				<br />

				<div class="main special">

						<!-- Reply Form(start) -->
						<div class="my-3 p-3 rounded shadow-sm bg-sub box"
							style="padding-top: 10px; background-color: WhiteSmoke;">

							<!-- Reply List(start) -->
							<div class="my-3 p-3 rounded shadow-sm" style="padding-top: 10px">
								<h5 class="border-bottom pb-2 mb-0">댓글</h5>
								<div id="replyList"></div>
							</div>
							<hr>
							<!-- Reply List(end) -->
							
							<%-- 
							<form:form name="form" id="form" role="form" modelAttribute="reply" method="post">
								<form:hidden path="bid" id="bid" />
								<div>
									<div>
										<form:textarea path="rcontent" id="rcontent" style="resize:none"
											class="form-control" rows="3" placeholder="댓글을 입력해주세요"></form:textarea>
									</div>
									<br/>
									
									<div class="row" align="right">
										<div class="col-sm-2" >
											<form:input path="reg_id" id="reg_id" class="form-control"
												placeholder="댓글 작성자"></form:input>
											<button type="button" class="btn btn-sm btn-primary"
												id="btnInsertReply" style="width: 100%; margin-top: 10px">댓글 작성</button>
										</div>
									</div>

								</div> 
							</form:form>
							 --%>							

							<!-- pagination(start) -->
							<div id="paginationBox" class="pagination">
								<ul class="pagination" style="margin-top: 10px">
									<c:if test="${pagination.prevPage }">
										<li class="page"><a class="button"
											href="javascript:void(0)"
											onclick="fn_prevPage('${pagination.curPage}', '${pagination.curRange }', '${pagination.rangeSize }')">Previous</a></li>
									</c:if>

									<c:forEach begin="${pagination.startPage }"
										end="${pagination.endPage }" var="idx">
										<li><a
											class="page <c:out value="${pagination.curPage == idx ? 'active' : ''}"/>"
											href="javascript:void(0)"
											onclick="fn_pagination('${idx}', '${pagination.curRange }', '${pagination.rangeSize }')">${idx}</a>
										</li>
									</c:forEach>

									<c:if test="${pagination.nextPage }">
										<li class="page"><a class="button"
											href="javascript:void(0)"
											onclick="fn_nextPage('${pagination.curPage}', '${pagination.curRange }', '${pagination.rangeSize }')">Next</a></li>
									</c:if>
								</ul>
							</div>
							<!-- pagination(end) -->

							<!-- search(start) -->
							<div class="form-group row justify-content-center">
								<div class="w100" style="padding-right: 10px">
									<select class="form-control form-control-sm" name="searchType"
										id="searchType">
										<option value="rcontent"
											<c:if test="${pagination.searchType eq 'rcontent' }">selected</c:if>>댓글
											내용</option>
										<option value="reg_id"
											<c:if test="${pagination.searchType eq 'reg_id' }">selected</c:if>>댓글
											작성자</option>
									</select>
								</div>
								<div class="w300" style="padding-right: 10px">
									<input type="text" class="form-control form-control-sm"
										name="keyword" id="keyword">
								</div>
								<div>
									<button class="btn btn-sm btn-primary" name="btnSearch"
										id="btnSearch">검색</button>
								</div>
							</div>
							<!-- search(end) -->

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Reply Form(end) -->

	<%@ include file="/WEB-INF/views/layout/main_plugins.jsp"%>
</body>

<script>

	//showReplyList 함수 호출
	$(document).ready(function() {
		
		showReplyList();
	});
	
	//댓글 리스트(AJAX)
	function showReplyList() {
		var url = "${pageContext.request.contextPath}/restBoard/getReplyList";
		var paramData = { "bid" : "${boardContent.bid}" };

		$.ajax({
			type : 'POST',
			url : url,
			data : paramData,
			dataType : 'json',
			beforeSend : function(xhr) { /* 데이터를 전송하기 전에 헤더에 csrf값을 설정 */
				xhr.setRequestHeader("${_csrf.headerName}","${_csrf.token}");
				},
			success : function(result) {
				var htmls = "";
				if (result.length < 1) {
					htmls = "등록된 댓글이 없습니다";
				} else {
					$(result).each(
						function() {
						htmls += '<hr>';
						htmls += '<div>';
						htmls += '<div class="media text-muted pt-3" id="rid' + this.rid + '">';
						/*htmls += '<svg class="bd-placeholder-img mr-2 rounded" width="32" height="32" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder:32x32">';
						htmls += '<title>Placeholder</title>';
						htmls += '<rect width="100%" height="100%" fill="#007bff"></rect>';
						htmls += '<text x="50%" fill="#007bff" dy=".3em">32x32</text>';
						htmls += '</svg>'; 
						 */
						htmls += '<div style="float:left">';
						htmls += '<strong class="text-gray-dark">' + this.reg_id + '</strong>';
						htmls += '</div>';
						htmls += '<div style="float:right">';
						htmls += '<a href="javascript:void(0)" onclick="fn_editReply(' + this.rid + ', \'' + this.reg_id + '\', \'' + this.rcontent + '\' )" style="padding-right:5px">수정</a>';
						htmls += '&nbsp;';
						htmls += '<a href="javascript:void(0)" onclick="fn_deleteReply(' + this.rid + ')" >삭제</a>';
						htmls += '</div>';
						htmls += '</div>';
						htmls += '<br>';
						htmls += '<div>';
						htmls += this.rcontent;
						htmls += '</div>';
						htmls += '</div>';

					}); //each end
			}
					$("#replyList").html(htmls);

					} //AJAX success end

				}); //AJAX end
	}

	//댓글 작성 이벤트(AJAX)
	$(document).on('click', '#btnInsertReply', function() {
		var url = "${pageContext.request.contextPath}/restBoard/insertReply";
		var replyContent = $('#rcontent').val();
		var replyReg_id = $('#reg_id').val();

		var paramData = JSON.stringify({
			"rcontent" : replyContent,
			"reg_id" : replyReg_id,
			"bid" : '${boardContent.bid}'
		});
		var headers = {
			"Content-Type" : "application/json",
			"X-HTTP-Method-Override" : "POST"
		};

		$.ajax({
			url : url,
			headers : headers,
			type : 'POST',
			data : paramData,
			dataType : 'text',
			beforeSend : function(xhr) { /* 데이터를 전송하기 전에 헤더에 csrf값을 설정 */
				xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
			},
			success : function(result) {
				showReplyList();

				$('#rcontent').val('');
				$('#reg_id').val('');
			},
			error : function(error) {
				console.log("에러 : " + error);
			}

		});

	});

	//댓글 수정 이벤트
	function fn_editReply(rid, reg_id, rcontent) {
		var htmls = "";
		htmls += '<div class="media text-muted pt-3" id="rid' + this.rid + ' ">';
		/* htmls += '<svg class="bd-placeholder-img mr-2 rounded" width="32" height="32" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder:32x32">';
		htmls += '<title>Placeholder</title>';
		htmls += '<rect width="100%" height="100%" fill="#007bff"></rect>';
		htmls += '<text x="50%" fill="#007bff" dy=".3em">32x32</text>';
		htmls += '</svg>'; */
		//htmls += '<p class="media-body pb-3 mb-0 small lh-125 border-bottom horder-gray">';
		htmls += '<div style="float : left">';
		htmls += '<strong class="text-gray-dark">' + reg_id + '</strong>';
		htmls += '</div>';
		htmls += '<div style="float : right">';
		htmls += '<a href="javascript:void(0)" onclick="fn_updateReply(' + rid
				+ ', \'' + reg_id + '\')" style="padding-right:5px">작성</a>';
		htmls += '&nbsp;';
		htmls += '<a href="javascript:void(0)" onclick="showReplyList()">취소<a>';
		htmls += '</div>';
		htmls += '<br/>';
		htmls += '<textarea name="editContent" id="editContent" class="form-control" rows="3">';
		htmls += rcontent;
		htmls += '</textarea>';
		//htmls += '</p>';
		htmls += '</div>';

		$('#rid' + rid).replaceWith(htmls);
		$('#rid' + rid + '#editContent').focus();
	}

	function fn_updateReply(rid, reg_id) {
		var replyEditContent = $('#editContent').val();

		var paramData = JSON.stringify({
			"rcontent" : replyEditContent,
			"rid" : rid
		});
		var headers = {
			"Content-Type" : "application/json",
			"X-HTTP-Method-Override" : "POST"
		};

		$.ajax({
			url : "${pageContext.request.contextPath}/restBoard/updateReply",
			headers : headers,
			data : paramData,
			type : 'POST',
			dataType : 'text',
			beforeSend : function(xhr) { /* 데이터를 전송하기 전에 헤더에 csrf값을 설정 */
				xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
			},
			success : function(result) {
				console.log(result);
				showReplyList();

			},
			error : function(error) {
				console.log("error : " + error);
			}

		});

	}

	//댓글 삭제
	function fn_deleteReply(rid) {
		var paramData = {
			"rid" : rid
		};

		$.ajax({
			url : "${pageContext.request.contextPath}/restBoard/deleteReply",
			data : paramData,
			type : 'POST',
			dataType : 'text',
			beforeSend : function(xhr) { /* 데이터를 전송하기 전에 헤더에 csrf값을 설정 */
				xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
			},
			success : function(result) {
				showReplyList();
			},
			error : function(error) {
				console.log("error : " + error);
			}

		});
	}
</script>




</html>