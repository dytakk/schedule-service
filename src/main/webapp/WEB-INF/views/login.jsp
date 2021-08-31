<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link href="${pageContext.request.contextPath}/resources/css/login.css"
	type="text/css" rel="stylesheet" />

<script src="resources/js/schedule.js"></script>
<meta charset="UTF-8">
<title>LogIn Page</title>
<script>
	const message = "${message}";
	if (message != "") {
		alert(message);

	}
</script>


</head>
<!--  <body onLoad = "callMessage(message)" -->
<body
	onLoad="getAjax('https://api.ipify.org', 'format=json', 'setPublicIp')">

	<a href="https://api.ipify.org?format=jsonp&callback=getIP"></a>
	<div id="accesszone">
		<div class="title">CHO HWABAEK</div>
		<div class="sTitle">services</div>
		
		<div>
			<div>
				<input type="text" name="uCode" class="box" placeholder="아이디" />
			</div>
		</div>
		<div>
			<div>
				<input type="password" name="aCode" class="box" placeholder="비밀번호" />
			</div>
		</div>

		<div>
			<input type="button" class="submit" value="로그인"
				onClick="sendAccessInfo()" />
		</div>
		<div style="text-align: center; color: #bbbbbb" class="found">
			아이디 찾기 | 비밀번호 찾기 | <a href="JoinForm"
				style="text-decoration: none; color: #bbbbbb">회원가입</a> <input
				type="date" name="day" />
		</div>
	</div>




</body>
</html>