<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/layout/init_head.jsp"%>
<title>ChPark.com</title>

</head>
<body class="is-preload">
	<div id="wrapper">
		<div id="bg"></div>
		<div id="overlay"></div>
		<div id="main">

			<!-- Header -->
			<header id="header">
				<h1>ChParkLand</h1>
				<p>ChParkLand에 &nbsp;&nbsp; 방문 하신 것을 &nbsp;&nbsp; 환영합니다.</p>
				<nav>
					<ul>
						<li>
							<a href="${pageContext.request.contextPath}/login/login" class="fas fa-sign-in-alt" data-tooltip-text="로그인 "> 
								<span class="label">Sign in</span>
							</a>
						</li>
						<li>
							<a href="${pageContext.request.contextPath}/main/mainPage" class="fas fa-home"
								data-tooltip-text="홈페이지"> <span class="label">Home</span>
							</a>
						</li>
						<li>
							<a href="${pageContext.request.contextPath}/me/me.jsp" class="fas fa-portrait" data-tooltip-text="자기소개"> 
								<span class="label">Intro</span>
							</a>
						</li>
						<li>
							<a href="https://github.com/chpark93/parkland" class="fab fa-github" data-tooltip-text="깃허브">
								<span class="label">Github</span>
							</a>
						</li>
						<li>
							<a href="${pageContext.request.contextPath}/email/writeEmail" class="fas solid fa-envelope" data-tooltip-text="문의  메일"> 
								<span class="label">Email</span>
							</a>
						</li>
					</ul>
				</nav>
			</header>
			
		</div>
	</div>
</body>

<script>
	window.onload = function() {
		document.body.classList.remove('is-preload');
	}
	window.ontouchmove = function() {
		return false;
	}
	window.onorientationchange = function() {
		document.body.scrollTop = 0;
	}
</script>
</html>