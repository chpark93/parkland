<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- Custom CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/common/css/common.css?ver=1.1" />
<%@ include file="/WEB-INF/views/layout/main_head.jsp"%>

<title>유저 페이지</title>
<style type="text/css">
#table a {
	font: Normal 14px sans-serif;
	color: #333;
	text-decoration: none;
}

Img {
	width: 180px;
    height:180px;
    border-radius: 90px;
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
				<br/>

				<!-- User Info -->
				<div class="user_info" >
				<br/><br/>	
					<h2 style="text-align: center;">현재 존재하지 않는 유저 닉네임 입니다.</h2>
				</div>
				<br/>
				<hr>

				<br/><br/><br/>

			</div>
		</div>
		<!-- Sidebar -->
		<%@ include file="/WEB-INF/views/layout/sidebar.jsp"%>
	</div>
	<%@ include file="/WEB-INF/views/layout/main_plugins.jsp"%>
</body>


</html>