<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/layout/intro_head.jsp"%>
<title>Insert title here</title>
</head>
<body class="">


	<nav id="nav">
		<ul class="container">
			<li><a href="#top">Top</a></li>
			<li><a href="#intro">Intro</a></li>
			<li><a href="#portfolio">Portfolio</a></li>
			<li><a href="#contact">Contact</a></li>
		</ul>
	</nav>			

	
	<article id="top" class="wrapper">
		<div class="container">
			<div class="row">
			<br/>
				<div class="col-4 col-5-large col-12-medium">
					<span class="image fit"><img src="${pageContext.request.contextPath}/resources/img/pic00.jpg" alt=""></span>
				</div>
				<br/>
				<div class="col-8 col-7-large col-12-medium">
					<header>
						<h1>창희적인 개발자 <strong>박창희</strong> 입니다.</h1>
					</header>
					<br/>
					<p>
						끊임없이 개발하고 발전하며 제가 생각하는 가장 자신다운 프로그래밍을 하려고 노력하는 개발자입니다.  
					</p>
				</div>
			</div>
		</div>
	</article>


	<article id="intro" class="wrapper">
		<div class="container">
			<header>
				<h2>개발자로서의 박창희는 ...</h2>
			</header>
			<br/>
			<div class="row aln-center">
				<div class="col-4 col-6-medium col-12-small">
					<section class="box style1">
						<h3>기술 스택</h3><br/>
						<p>Language : Java , JavaScript</p>
						<p>DB : MySql </p>
						<p>WAS : Apache, Tomcat </p>
						<p>Editor : STS </p>
						<p>Dev Tools : HeidiSql </p>						
					</section>
				</div>
				<div class="col-4 col-6-medium col-12-small">
					<section class="box style1">
						<h3>가치관</h3><br/>
						<p>끊임없이 배우고 고치자</p>
						<p>모르는건 누구라도 붙잡고 물고 늘어지자</p>
						<p>피드백은 필요가 아니라 필수불가결이다</p>
						<p>1등 보다 누구나 의지하는 에이스가 되자</p>
						<p>문제에 대한 고민은 나를 성장 시킨다</p>
					</section>	
				</div>
				<div class="col-4 col-6-medium col-12-small">
					<section class="box style1">
						<h3>개발자로서 목표</h3><br/>
						<p>기록과 개선으로 같은 실수를 하지 않는</p>
						<p>새로운 배움을 두려워 하지 않는 </p>
						<p>끊임없이 의심하는 </p>
						<p>사용자의 환경에서 항상 생각하는</p>
						<p>대한민국 IT사에 이름을 남긴 개발자</p>
					</section>
				</div>		 
			</div>
		</div>
	</article>

	
	<article id="portfolio" class="wrapper">
		<div class="container">
			<header>
				<h2>개인 프로젝트</h2>
				<p>
					유저들이 자유롭게 소통할 수 있는 커뮤니티 웹 사이트 구현.
				</p>
			</header>
			<div class="row">
				<div class="col-6 col-8-medium col-12-small">
					<article class="box style2">
						<a href="#" class="image featured"><img src="images/pic01.jpg"
							alt=""></a>
						<h3>
							<a href="#">Magna feugiat</a>
						</h3>
						<p>Ornare nulla proin odio consequat.</p>
					</article>
				</div>
				<div class="col-4 col-4-medium col-12-small">
					<section class="box style1">
						<p>사용 기술 : JAVA8, HTML5, CSS, JavaScript, jQuery</p>
						<p>운영 환경 : Apache Tomcat, AWS EC2(ubuntu), AWS RDS(MySql)</p>
						<p>프레임 워크 : Spring Framework, MyBatis</p>							
						<p>DB : MariaDB</p>													
						<p>Tools : STS, HeidiSql, Putty </p>							
						<p>개발 기간 : 2019.10 ~ 2020.01 </p>							
					</section>
				</div>
			</div>
		</div>
	</article>
	
	<article id="contact" class="wrapper style2">
		<div class="container medium">
			<header>
				<h3>개발자로서 시작의 발돋움.&nbsp; 읽어주셔서 감사합니다.</h3>
			</header>		
			<h4>ckdgml1993@gmail.com</h4>
			<h4>010-5852-6952</h4>		
			<div class="row">
				<div class="col-12">
					<ul class="social">
						<li><a href="${pageContext.request.contextPath}/email/writeEmail" class="icon fas solid fa-envelope"></a></li>
						<li><a href="https://github.com/chpark93" class="icon brands fa-github"><span class="label">Github</span></a></li>
					</ul>			
				</div> 
			</div>
		</div>
		<br/><br/>
	</article>

	<%@ include file="/WEB-INF/views/layout/intro_plugins.jsp"%>
</body>
</html>