<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link href="${pageContext.request.contextPath}/resources/css/login.css"
	type="text/css" rel="stylesheet" />

<script src="resources/js/schedule.js"></script>
<meta charset="UTF-8">
<title>Authentication Message</title>
<script>

function emailAuth(){
	const uCode = document.getElementsByName("mbId")[0];
	const teCode = document.getElementsByName("teCode")[0];
	let form = makeForm("EmailAuthConfirm","post");
	
	form.appendChild(uCode);
	form.appendChild(teCode);
	
	document.body.appendChild(form);
	
	form.submit();
	
	
}




</script>


</head>
<!--  <body onLoad = "callMessage(message)" -->
<body>

	<div id="accesszone">
		<div class="title">CHO HWABAEK</div>
		<div class="sTitle">authentication services</div>
		<div>
			<div>
				<input type="text" name="mbId" class="box" placeholder="아이디" />
			</div>
		</div>
		<div>
			<div>
				<input type="password" name="aCode" class="box" placeholder="비밀번호" />
			</div>
		</div>

		<div>
			<input type="button" class="submit" value="인증을위한 로그인하기"
				onClick="emailAuth()" />
			<input type="hidden" name="teCode" value="${param.teCode}"/>
		</div>
		<div style="text-align: center; color: #bbbbbb" class="found">
			아이디 찾기 | 비밀번호 찾기 | <a href="JoinForm"
				style="text-decoration: none; color: #bbbbbb">회원가입</a> <input
				type="date" name="day" />
		</div>
	</div>




</body>
</html>