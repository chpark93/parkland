<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    
<meta charset="UTF-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">


<!-- main CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/main/css/main.css?ver=1.3" />

<!-- modal -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/common/css/modal.css" />

<!-- lightbox CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/lightbox/css/lightbox.css" />

<!-- Font Awesome Icons -->
<link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/main/css/fontawesome-all.min.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<!-- Ionicons -->
<link href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css" rel="stylesheet" type="text/css" />

   
<!-- JQuery -->
<script src='<c:url value="/resources/common/js/jquery.js"/>'></script>
<script src='<c:url value="/resources/common/js/jquery.form.js"/>'></script>
<script src='<c:url value="/resources/common/js/handlebars.js"/>'></script>
<script src='<c:url value="/resources/common/js/moment.min.js"/>'></script> 
<script src='<c:url value="/resources/common/js/hbs.js"/>'></script>


  
<script>

//댓글  WebSocket
var socket = null;

$(document).ready(function() {
	
	connectWebSocket();

});
	
function connectWebSocket() {
	    
	//var ws = new WebSocket("ws://chparkland.com/replyEcho/websocket");
	var ws = new WebSocket("ws://localhost:8090/web/replyEcho/websocket");
	socket = ws;
	
	ws.onopen = function () {
	    console.log('Info: connection opened.');
	   
	};
	
	ws.onmessage = function (e) {
	    console.log("Receive Message : ", e.data + '\n');
	
		var $alertSocket = $("div#alertSocket");
				
		$alertSocket.html(e.data);
		$alertSocket.css('display', 'block');
		
		setTimeout( function() {
        	$alertSocket.css('display', 'none');
        }, 8000);
		
		//$(".notification").append(e.data);
	};
	
	
	ws.onclose = function (e) { 
		console.log('Info: connection closed.'); 
	
	};
	
	ws.onerror = function (e) { 
		console.log('Info: connection closed.'); 
		
	};
	
}



</script>
