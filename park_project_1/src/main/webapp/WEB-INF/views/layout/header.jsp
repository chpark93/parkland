<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<header id="header">
	<a href="${pageContext.request.contextPath}/main/mainPage" class="logo" style="margin-top: 14px;">The
		Flow Of<strong>&nbsp; ChParkLand</strong>
	</a>

	<!-- Alert -->
	<div style="text-align: right;">
		<a href="${pageContext.request.contextPath}/mypage/getAlarmListPagingFromId" > 
		    <span id="alarm" class="notification" ></span>
		</a>
	</div>
</header>


<style>

.notification {
    display: inline-block;
    position: relative;
    padding: 0.6em;
    background: #6CC5AF;
    border-radius: 0.2em;
    font-size: 1.3em;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
}

.notification::before, 
.notification::after {
    color: #fff;
    text-shadow: 0 1px 3px rgba(0, 0, 0, 0.3);
}	

.notification::before {
    display: block;
    content: "\f0f3";
    font-family: "FontAwesome";
    transform-origin: top center;
}

.notification::after {
    font-family: Arial;
    font-size: 0.7em;
    font-weight: 700;
    position: absolute;
    top: -15px;
    right: -15px;
    padding: 5px 8px;
    line-height: 100%;
    border: 2px #fff solid;
    border-radius: 50px;
    /* background: #3498db; */
    background: #6CC5AF;
    opacity: 0;
    content: attr(data-count);
    opacity: 0;
    transform: scale(0.5);
    transition: transform, opacity;
    transition-duration: 0.3s;
    transition-timing-function: ease-out;
}

.notification.notify::before {
    animation: ring 1.5s ease;
}

.notification.show-count::after {
    transform: scale(1);
    opacity: 1;
}

@keyframes ring {
    0% {
        transform: rotate(35deg);
    }
    12.5% {
        transform: rotate(-30deg);
    }
    25% {
        transform: rotate(25deg);
    }
    37.5% {
        transform: rotate(-20deg);
    }
    50% {
        transform: rotate(15deg);
    }
    62.5% {
        transform: rotate(-10deg);
    }
    75% {
        transform: rotate(5deg);
    }
    100% {
        transform: rotate(0deg);
    }
}

</style>


<script>

var socket = null;

$(document).ready(function() {
	
	connectWebSocket();

});
	
function connectWebSocket() {
	    
	var ws = new WebSocket("wss://chparkland.com/park_project_1/echo/websocket");
	//var ws = new WebSocket("ws://localhost:8080/web/replyEcho/websocket");
	socket = ws;
	
	ws.onopen = function (e) {
	    console.log('Info: connection opened.');
	   
	   	ws.send("${loginUser.id}");
	};
	
	ws.onmessage = function (e) {
	    console.log("Receive Message : ", e.data + '\n');
	    
		//알람 count		
		var el = document.querySelector('.notification');

	    var count = Number(el.getAttribute('data-count'));
	    el.setAttribute('data-count', e.data);
	    el.classList.remove('notify');
	    el.offsetWidth = el.offsetWidth;
	    el.classList.add('notify');
	    
	    
	    if(count === 0) {
	       
	    	el.classList.add('show-count');
	    }
	    
		
	};
	
	
	ws.onclose = function (e) { 
		console.log('Info: connection closed.'); 
	
	};
	
	ws.onerror = function (e) { 
		console.log('Info: connection closed.'); 
		
	};
	
}

</script>


