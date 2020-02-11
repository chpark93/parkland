<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/layout/main_head.jsp"%>

<title>Menu List</title>
<style>
#paginationBox {
	padding: 10px 0px;
}
</style>
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
					
					<div class="">
			        	<label style="font-size:20px;">게시판 그룹</label>
			        </div>

					<div>
						<form:form name="form" id="form" role="form" modelAttribute="menu" method="post"
							action="${pageContext.request.contextPath}/menu/insertMenu">
							<form:hidden path="m_id" id="m_id" />

							<div class="row">
								<div class="col-md-5 mb-3">
									<label for="bg_no">Bg_no</label>
									<form:input path="bg_no" class="form-control" id="bg_no"
										placeholder="" required="" />
								</div>
							</div>
							<br/>
							
							<div class="row">	
								<div class="col-md-5 mb-3">
									<label for="bg_name">Bg_Name</label>
									<form:input path="bg_name" class="form-control"
										id="bg_name" placeholder="" value="" required="" />
								</div>
							</div>
							<br/>
							
							<div class="row">
								<div class="col-md-12 mb-6">
									<label for="upper_bg_name">Upper_bg_name</label>
									<form:input path="upper_bg_name" class="form-control"
										id="upper_bg_name" placeholder="" value="" required="" />
								</div>
							</div>
							<br/>
							
							<div class="row">
								<div class="col-md-12 mb-6">
									<label for="bg_info">bg_info</label>
									<form:input path="bg_info" class="form-control"
										id="bg_info" placeholder="" value="" required="" />
								</div>
							</div>
						</form:form>
					</div>
					<br/>
					<!-- Menu form(end) -->
					
					<div>
						<button type="button" class="btn btn-sm btn-primary" id="btnInsert">생성</button>
						<button type="button" class="btn btn-sm btn-primary" id="btnDelete">삭제</button>
						<button type="button" class="btn btn-sm btn-primary" id="btnReset">초기화</button>
					</div>
					<hr>
					
							
					<!-- List<start> -->
					<div class="mb-3">
			        	<label style="font-size:20px;">그룹 리스트</label>
			        </div>
					<br/>
					<div class="table-responsive">
						<table class="table table-striped table-sm">
							<colgroup>
								<col style="width: 10%;" />
								<col style="width: 15%;" />
								<col style="width: 20%;" />
								<col style="width: auto;" />
								<col style="width: auto;" />
							</colgroup>
							<thead>
								<tr>
									<th>No</th>
									<th>Bg_no</th>
									<th>Bg_name</th>
									<th>Upper_bg_name</th>
									<th>bg_info</th>
								</tr>
							</thead>
							
							<!-- AJAX로 처리 -->
							<tbody id="menuList"></tbody>
						</table>
					</div>
					<!-- List(end) -->


				</div>
		
		</div>
		<!-- Sidebar -->
		<%@ include file="/WEB-INF/views/layout/sidebar.jsp"%>
	</div>
	<%@ include file="/WEB-INF/views/layout/main_plugins.jsp"%>
</body>

<script>

	$(function() {
		fn_menuList();
	});
	
	//메뉴 리스트
	function fn_menuList() {

		var paramData = {};

		$.ajax({
			url : "${pageContext.request.contextPath}/restMenu/getMenuList",
			type : "POST",
			data : paramData,
			dataType : "json",
			success : function(data) {
				console.log(data);
				
				if (data.status == "OK") {
					
					if (data.menuList.length > 0) {
						
						var list = data.menuList;
						var htmls = "";
						
						$(data.menuList).each(function() {
							htmls += '<tr>';
							htmls += '<td>' + this.m_id + '</td>';
							htmls += '<td>';
							htmls += '<a href="#" onclick="fn_menuInfo('
									+ this.m_id + ',\'' + this.bg_no + '\' ,\''
									+ this.bg_name + '\' ,\'' + this.upper_bg_name + '\' ,\''
									+ this.bg_info + '\'	)">';
							htmls += this.bg_no;
							htmls += '</a>';
							htmls += '</td>';
							htmls += '<td>' + this.bg_name + '</td>';
							htmls += '<td>' + this.upper_bg_name + '</td>';
							htmls += '<td>' + this.bg_info + '</td>';
							htmls += '</tr>';

						});
					}
				} else {
					console.log("조회 실패");
				}

				$('#menuList').html(htmls);
			}

		});
	}

	//작성 이벤트
	$(document).on('click', '#btnInsert', function(e) {
		e.preventDefault();

		var url = "${pageContext.request.contextPath}/restMenu/insertMenu";
		var paramData = {
			"bg_name" : $("#bg_name").val(),
			"bg_no" : $("#bg_no").val(),
			"upper_bg_name" : $("#upper_bg_name").val(),
			"bg_info" : $("#bg_info").val()
		};

		//수정 이벤트
		if ($("#m_id").val() != 0) {
			if(confirm("수정 하시겠습니까?")) {			
				var url = "${pageContext.request.contextPath}/restMenu/updateMenu";
			}
		}
		
		$.ajax({
			url : url,
			type : "POST",
			data : paramData,
			dataType : "json",
			success : function(data) {
				fn_menuList()
				
				//초기화
				$("#btnReset").trigger('click');
			}
		});
	});
	
	
	//초기화
	$(document).on('click', '#btnReset', function(e) {
		e.preventDefault();
		
		$("#m_id").val('');
		$("#bg_no").val('');
		$("#bg_name").val('');
		$("#upper_bg_name").val('');
		$("#bg_info").val('');

		$("#bg_no").attr("readonly", false);
	});
	
	
	//메뉴 정보 (bg_no 클릭시 )
	function fn_menuInfo(m_id, bg_no, bg_name, upper_bg_name, bg_info) {
		$("#m_id").val(m_id);
		$("#bg_no").val(bg_no);
		$("#bg_name").val(bg_name);
		$("#upper_bg_name").val(upper_bg_name);
		$("#bg_info").val(bg_info);
		
		//읽기 모드
		$("#bg_no").attr("readonly", true);
	}
	
	//삭제 이벤트
	$(document).on('click', '#btnDelete', function(e) {
		e.preventDefault();
		
		if ($("#bg_no").val() == "") {
			alert("삭제할 코드를 선택해주세요.");
			return;
		}
		var url = "${pageContext.request.contextPath}/restMenu/deleteMenu";
		
		var paramData = {
			"bg_no" : $("#bg_no").val()
		};
		
		if(confirm("삭제하시면 포함된 모든 글이 데이터에서 삭제 됩니다. 정말로 삭제 하시겠습니까?")) {
			$.ajax({
				url : url,
				type : "POST",
				data : paramData,
				dataType : "json",
				success : function(data) {
					fn_menuList()
	
					//삭제 후 초기화
					$("#btnReset").trigger('click');
				}
			});			
		}
	});
</script>
</html>