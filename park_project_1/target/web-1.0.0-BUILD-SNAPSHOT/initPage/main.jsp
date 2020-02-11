<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/layout/main_head.jsp"%>
<title>Main Page</title>
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
				
				<section>
				<div class="row">
				
					<div class="col-6 col-12-small">
						<div>
							<span style="font-size: 16px;"><i class="far fa-star"></i>&nbsp;주간 인기글</span>
						</div>
						<br/>
						<div class="box" id="box">     		
							<c:choose>
								<c:when test="${empty viewBestBoard}">
									<tr>
										<td colspan="5" align="center">데이터가 없습니다</td>
									</tr>
								</c:when>
								<c:when test="${not empty viewBestBoard}">
									<ul class="alt">
									<c:forEach var="list" items="${viewBestBoard}">
										<li>
											<a href="${pageContext.request.contextPath}/board/getBoardContent?bg_no=${list.bg_no}&bid=${list.bid}" style="color: black;"><c:out value="${list.getShortTitle(20)}" /></a>
											<div style="text-align: right; font-size:10px;">
												<span>
													<c:if test="${list.reg_id eq loginUser.id}">
														<a href="${pageContext.request.contextPath}/mypage/getBoardListPagingFromId">
															<c:out value="${list.reg_nickname}" />
														</a>
													</c:if>
													<c:if test="${list.reg_id ne loginUser.id}">
														<a href="${pageContext.request.contextPath}/userpage/getBoardListPagingUser?nickname=${list.reg_nickname}">
															<c:out value="${list.reg_nickname}" />
														</a>
													</c:if>
												</span>
												&nbsp;
												<span><c:out value="${list.reg_dt}" /></span>												
											</div>
										</li>
									</c:forEach>
									</ul>
								</c:when>
							</c:choose>
						</div>	
						<div style="text-align:right; margin-right: 10px; font-size: 16px;">
							<a href="${pageContext.request.contextPath}/board/getViewBestBoardList">More</a>
						</div>
					</div>
				              
					<div class="col-6 col-12-small">
						<div>
							<span style="font-size: 16px;"><i class="far fa-thumbs-up"></i>&nbsp;주간 추천글</span>
						</div>
						<br/>
						<div class="box" id="box">
							<c:choose>
								<c:when test="${empty recommendBoard}">
									<tr>
										<td colspan="5" align="center">데이터가 없습니다</td>
									</tr>
								</c:when>
								<c:when test="${not empty recommendBoard}">
									<ul class="alt">
									<c:forEach var="list" items="${recommendBoard}">
										<li>
											<a href="${pageContext.request.contextPath}/board/getBoardContent?bg_no=${list.bg_no}&bid=${list.bid}" style="color: black;"><c:out value="${list.getShortTitle(20)}" /></a>
											<div style="text-align: right; font-size:10px;">
												<span>
													<c:if test="${list.reg_id eq loginUser.id}">
														<a href="${pageContext.request.contextPath}/mypage/getBoardListPagingFromId?id=${loginUser.id}">
															<c:out value="${list.reg_nickname}" />
														</a>
													</c:if>
													<c:if test="${list.reg_id ne loginUser.id}">
														<a href="${pageContext.request.contextPath}/userpage/getBoardListPagingUser?nickname=${list.reg_nickname}">
															<c:out value="${list.reg_nickname}" />
														</a>
													</c:if>
												</span>
												&nbsp;
												<span><c:out value="${list.reg_dt}" /></span>												
											</div>
										</li>
									</c:forEach>
									</ul>
								</c:when>
							</c:choose>		
						</div>
						<div style="text-align:right; margin-right: 10px; font-size: 16px;">
							<a href="${pageContext.request.contextPath}/board/getRecommendBoardList" >More</a>
						</div>
					</div>
				</div>	
				</section>
				<br/><br/>
				    
				<!-- Section -->
				<section>
					<br/>			
					<div class="row">
						<div class="col-8 col-12-small">
						<div>
							<span style="font-size: 16px;"><i class="far fa-comment-alt"></i>&nbsp; 자유 게시판</span>
						</div>
						<br/>
						<div class="box" id="box">     
							<c:choose>	
								<c:when test="${empty commonBoard}">
									<tr>
										<td colspan="5" align="center">데이터가 없습니다</td>
									</tr>
								</c:when>
								<c:when test="${not empty commonBoard}">
									<ul class="alt">
									<c:forEach var="list" items="${commonBoard}">
										<li>
											<a href="${pageContext.request.contextPath}/board/getBoardContent?bg_no=${list.bg_no}&bid=${list.bid}" style="color: black;"><c:out value="${list.getShortTitle(20)}" /></a>
											<div style="text-align: right; font-size:10px;">
												<span>
													<c:if test="${list.reg_id eq loginUser.id}">
														<a href="${pageContext.request.contextPath}/mypage/getBoardListPagingFromId?id=${loginUser.id}">
															<c:out value="${list.reg_nickname}" />
														</a>
													</c:if>
													<c:if test="${list.reg_id ne loginUser.id}">
														<a href="${pageContext.request.contextPath}/userpage/getBoardListPagingUser?nickname=${list.reg_nickname}">
															<c:out value="${list.reg_nickname}" />
														</a>
													</c:if>
												</span>
												&nbsp;
												<span><c:out value="${list.reg_dt}" /></span>												
											</div>
										</li>
									</c:forEach>
									</ul>
								</c:when>
							</c:choose>	
						</div>	
						<div style="text-align:right; margin-right: 10px; font-size: 16px;">
							<a href="${pageContext.request.contextPath}/board/getBoardList?bg_no=1">More</a>
						</div>
					</div>
					
					<div class="col-4 col-12-small">
						<div>
							<span style="font-size: 16px;"><i class="fas fa-question"></i>&nbsp; 질문 게시판</span>
						</div>
						<br/>
						<div class="box" id="box">     
							<c:choose>
								<c:when test="${empty commonBoard2}">
									<tr>
										<td colspan="5" align="center">데이터가 없습니다</td>
									</tr>
								</c:when>
								<c:when test="${not empty commonBoard2}">
									<ul class="alt">
									<c:forEach var="list" items="${commonBoard2}">
										<li>
											<a href="${pageContext.request.contextPath}/board/getBoardContent?bg_no=${list.bg_no}&bid=${list.bid}" style="color: black;"><c:out value="${list.getShortTitle(20)}" /></a>
											<div style="text-align: right; font-size:10px;">
												<span>
													<c:if test="${list.reg_id eq loginUser.id}">
														<a href="${pageContext.request.contextPath}/mypage/getBoardListPagingFromId?id=${loginUser.id}">
															<c:out value="${list.reg_nickname}" />
														</a>
													</c:if>
													<c:if test="${list.reg_id ne loginUser.id}">
														<a href="${pageContext.request.contextPath}/userpage/getBoardListPagingUser?nickname=${list.reg_nickname}">
															<c:out value="${list.reg_nickname}" />
														</a>
													</c:if>
												</span>
												&nbsp;
												<span><c:out value="${list.reg_dt}" /></span>												
											</div>
										</li>
									</c:forEach>
									</ul>
								</c:when>
							</c:choose>	
						</div>	
						<div style="text-align:right; margin-right: 10px; font-size: 16px;">
							<a href="${pageContext.request.contextPath}/board/getBoardList?bg_no=2">More</a>
						</div>	
					</div>
					
					</div>
				</section>
				<br/><br/><br/>
			      	
				
				<!-- Section -->
				<section>
				<br/>
					<div class="row">
						<div class="col-4 col-12-small">
						<div>
							<span style="font-size: 16px;"><i class="fas fa-laptop-code"></i>&nbsp; IT 게시판</span>
						</div>
						<br/>
						<div class="box" id="box">     
							<c:choose>
								<c:when test="${empty commonBoard3}">
									<tr>
										<td colspan="5" align="center">데이터가 없습니다</td>
									</tr>
								</c:when>
								<c:when test="${not empty commonBoard3}">
									<ul class="alt">
									<c:forEach var="list" items="${commonBoard3}">
										<li>
											<a href="${pageContext.request.contextPath}/board/getBoardContent?bg_no=${list.bg_no}&bid=${list.bid}" style="color: black;"><c:out value="${list.getShortTitle(20)}" /></a>
											<div style="text-align: right; font-size:10px;">
												<span>
													<c:if test="${list.reg_id eq loginUser.id}">
														<a href="${pageContext.request.contextPath}/mypage/getBoardListPagingFromId?id=${loginUser.id}">
															<c:out value="${list.reg_nickname}" />
														</a>
													</c:if>
													<c:if test="${list.reg_id ne loginUser.id}">
														<a href="${pageContext.request.contextPath}/userpage/getBoardListPagingUser?nickname=${list.reg_nickname}">
															<c:out value="${list.reg_nickname}" />
														</a>
													</c:if>
												</span>
												&nbsp;
												<span><c:out value="${list.reg_dt}" /></span>												
											</div>
										</li>
									</c:forEach>
									</ul>
								</c:when>
							</c:choose>	
						</div>	
						<div style="text-align:right; margin-right: 10px; font-size: 16px;">
							<a href="${pageContext.request.contextPath}/board/getBoardList?bg_no=10">More</a>
						</div>
					</div>
					
					<div class="col-4 col-12-small">
						<div>
							<span style="font-size: 16px;"><i class="fas fa-futbol"></i>&nbsp; 축구 게시판</span>
						</div>
						<br/>
						<div class="box" id="box">     
							<c:choose>
								<c:when test="${empty commonBoard4}">
									<tr>
										<td colspan="5" align="center">데이터가 없습니다</td>
									</tr>
								</c:when>
								<c:when test="${not empty commonBoard4}">
									<ul class="alt">
									<c:forEach var="list" items="${commonBoard4}">
										<li>
											<a href="${pageContext.request.contextPath}/board/getBoardContent?bg_no=${list.bg_no}&bid=${list.bid}" style="color: black;"><c:out value="${list.getShortTitle(20)}" /></a>
											<div style="text-align: right; font-size:10px;">
												<span>
													<c:if test="${list.reg_id eq loginUser.id}">
														<a href="${pageContext.request.contextPath}/mypage/getBoardListPagingFromId?id=${loginUser.id}">
															<c:out value="${list.reg_nickname}" />
														</a>
													</c:if>
													<c:if test="${list.reg_id ne loginUser.id}">
														<a href="${pageContext.request.contextPath}/userpage/getBoardListPagingUser?nickname=${list.reg_nickname}">
															<c:out value="${list.reg_nickname}" />
														</a>
													</c:if>
												</span>
												&nbsp;
												<span><c:out value="${list.reg_dt}" /></span>												
											</div>
										</li>
									</c:forEach>
									</ul>
								</c:when>
							</c:choose>	
						</div>	
						<div style="text-align:right; margin-right: 10px; font-size: 16px;">
							<a href="${pageContext.request.contextPath}/board/getBoardList?bg_no=5">More</a>
						</div>
					</div>
					
					<div class="col-4 col-12-small">
						<div>
							<span style="font-size: 16px;"><i class="far fa-comment-alt"></i>&nbsp; 건의 게시판</span>
						</div>
						<br/>
						<div class="box" id="box">     
							<c:choose>
								<c:when test="${empty commonBoard5}">
									<tr>
										<td colspan="5" align="center">데이터가 없습니다</td>
									</tr>
								</c:when>
								<c:when test="${not empty commonBoard5}">
									<ul class="alt">
									<c:forEach var="list" items="${commonBoard5}">
										<li>
											<a href="${pageContext.request.contextPath}/board/getBoardContent?bg_no=${list.bg_no}&bid=${list.bid}" style="color: black;"><c:out value="${list.getShortTitle(20)}" /></a>
											<div style="text-align: right; font-size:10px;">
												<span>
													<c:if test="${list.reg_id eq loginUser.id}">
														<a href="${pageContext.request.contextPath}/mypage/getBoardListPagingFromId?id=${loginUser.id}">
															<c:out value="${list.reg_nickname}" />
														</a>
													</c:if>
													<c:if test="${list.reg_id ne loginUser.id}">
														<a href="${pageContext.request.contextPath}/userpage/getBoardListPagingUser?nickname=${list.reg_nickname}">
															<c:out value="${list.reg_nickname}" />
														</a>
													</c:if>
												</span>
												&nbsp;
												<span><c:out value="${list.reg_dt}" /></span>												
											</div>
										</li>
									</c:forEach>
									</ul>
								</c:when>
							</c:choose>	
						</div>	
						<div style="text-align:right; margin-right: 10px; font-size: 16px;">
							<a href="${pageContext.request.contextPath}/board/getBoardList?bg_no=15">More</a>
						</div>
					</div>
				</div>
				</section>
				<br/><br/>
			</div>
		</div>

		<!-- sidebar -->
		<%@ include file="/WEB-INF/views/layout/sidebar.jsp"%>	
	</div>

	<%@ include file="/WEB-INF/views/layout/main_plugins.jsp"%>
</body>
<script>

</script>

</html>