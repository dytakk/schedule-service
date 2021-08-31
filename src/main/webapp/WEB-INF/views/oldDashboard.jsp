<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script>
function addSchedule()
{

	let form = makeForm("addSchedule","post");

	document.body.appendChild(form);
	form.submit();
	
}
</script>


<meta charset="UTF-8">
<script src="resources/js/schedule.js"></script>
<link
	href="${pageContext.request.contextPath}/resources/css/dashboard.css"
	type="text/css" rel="stylesheet" />
<title>My Page</title>
</head>
<body
	onLoad="getAjax('https://api.ipify.org', 'format=json', 'setPublicIp')">

	<div class="main">
		<div class="title">Schedule Manager</div>
		<div id="stitle">my page</div>
	</div>

	<div class="box">
	
	<div id="info">
	
		<img src="/resources/image/${file }" />	
	
	
	
		<div >내 프로필 </div>
		<div >____________</div>		
		<div>이름    :   ${UNAME }</div>
		<div>이메일   :  ${UMAIL }</div>

	<input type="button" class="submit2" value="로그아웃" onClick="sendLogoutInfo()" />
	<input type="hidden" name="uCode" value="${uCode }" />

	</div>
	
ㅇ
	
	<div id="main">

		<input type="button" value="날짜" onClick="addSchedule()"/>
		<input type="button" class="submit" value="팀관리" onClick="team()"/> 
		<input type="button" class="submit" value="스케줄관리" onClick="schedule()"/>
		<input type="button" class="submit" value="친구추가" onClick="friends()"/>
	</div>
</div>
	
</body>
</html>