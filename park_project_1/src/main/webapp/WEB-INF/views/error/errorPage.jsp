<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error Page</title>
<style>
#article {
	text-align:center;
}
h1 {
	font-size: 50px;
	margin-top:10px;
}

p {
	font-size: 30px;
}

</style>
</head>
<body>
	
	<!-- 400 -->
	<c:if test="${requestScope['javax.servlet.error.status_code'] == 400}">
		<article id="article">
			<img src="${pageContext.request.contextPath}/resources/img/error.png">
			<h1>Error 400</h1>
			<p>잘못된 요청 입니다.</p>
			<div><a href="${pageContext.request.contextPath}/main/mainPage">[메인]</a>을 클릭 하시면 메인화면으로 돌아갑니다.</div>
		</article>
	</c:if>	
	
	<!-- 403 -->
	<c:if test="${requestScope['javax.servlet.error.status_code'] == 403}">
		<article id="article">
			<img src="${pageContext.request.contextPath}/resources/img/error.png">
			<h1>Error 403</h1>
			<p>접근이 금지 되었습니다.</p>
			<div><a href="${pageContext.request.contextPath}/main/mainPage">[메인]</a>을 클릭 하시면 메인화면으로 돌아갑니다.</div>
			<br/>
		</article>
	</c:if>
	
	<!-- 404 -->
	<c:if test="${requestScope['javax.servlet.error.status_code'] == 404}">
		<article id="article">
			<img src="${pageContext.request.contextPath}/resources/img/error.png">
			<h1>Error 404</h1>
			<p>페이지를 찾을 수 없습니다.</p>
			<div><a href="${pageContext.request.contextPath}/main/mainPage">[메인]</a>을 클릭 하시면 메인화면으로 돌아갑니다.</div>
			<br/>
		</article>	
	</c:if>
	
	<!-- 405 -->
	<c:if test="${requestScope['javax.servlet.error.status_code'] == 405}">
		<article id="article">
			<img src="${pageContext.request.contextPath}/resources/img/error.png">
			<h1>Error 405</h1>
			<p>요청된 메서드가 허용되지 않습니다.</p>
			<div><a href="${pageContext.request.contextPath}/main/mainPage">[메인]</a>을 클릭 하시면 메인화면으로 돌아갑니다.</div>
			<br/>
		</article>
	</c:if>
	
	<!-- 500 -->
	<c:if test="${requestScope['javax.servlet.error.status_code'] == 500}">
		<article id="article">
			<img src="${pageContext.request.contextPath}/resources/img/error.png">
			<h1>Error 500</h1>
			<p>서버 내부에서 오류가 발생했습니다.</p>
			<div><a href="${pageContext.request.contextPath}/main/mainPage">[메인]</a>을 클릭 하시면 메인화면으로 돌아갑니다.</div>
			<br/>
		</article>
	</c:if>
	
	<!-- 503 -->
	<c:if test="${requestScope['javax.servlet.error.status_code'] == 503}">
		<article id="article">
			<img src="${pageContext.request.contextPath}/resources/img/error.png">
			<h1>Error 503</h1>
			<p>서비스를 사용할 수 없습니다.</p>
			<div><a href="${pageContext.request.contextPath}/main/mainPage">[메인]</a>을 클릭 하시면 메인화면으로 돌아갑니다.</div>
			<br/>
		</article>
	</c:if>
</body>

</html>