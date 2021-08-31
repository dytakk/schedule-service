<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link
	href="${pageContext.request.contextPath}/resources/css/friendlist.css"
	type="text/css" rel="stylesheet" />


<script src="resources/js/schedule.js"></script>
<meta charset="UTF-8">
<title>FRIENDS SEARCH</title>
</head>
<body>
	<script>
function sendInviteMail(){
	let invitemail = document.getElementsByName("mailAddress")[0].value;
	let sendJsonData = {to:invitemail};
	let clientData = JSON.stringify(sendJsonData);
	postAjax('schedule/inviteMail',clientData,'getFriendSearch','json');
}
function getInviteMail(jsonD){
	let list="";
	let emailadd = document.getElementsById("email");
	let data = "<input type='email' name='emailadd'/>";
	 data+="<input type='button' value='전송' onClick=seiteMail()/>"
	email.innerHTML=data;	
}

function callFriendSearch(){
	let searchlist = document.getElementsByName("searchlist")[0].value;
	let sendJsonData = {search:searchlist};
	let clientData = JSON.stringify(sendJsonData);	
	postAjax('schedule/searchList',clientData,'getFriendSearch','json');
}
function getFriendSearch(jsonD){
	//검색결과가 없을때 여기에 초대하메일을입력하세요 검색창 + 이메일전송 버튼
	//친구가 아닌사람에게 가입초대 메일 메소드 하나+ 어제 한 메소드 하나 (  메일보내는 메소드 + 회원아닌사람들한테 보내는 메소드 )
	let searchlist = document.getElementsByName("searchlist")[0].value;
	let data = "";
	let list = document.getElementById("list");

	//다음과 같은 조건문을 쓰면 포함하는 단어가 아닌 확실하게 똑같은 단어가 아니어야만 실행문으로 넘어감 흠냐
	if(jsonD.length==0){
		data +="<input type='email' name='mailAddress' /><br>";
		data +="<input type='button' value='회원가입 초대' onClick='sendInviteMail()'/>";
	
	}else{	
	for(i=0; i<jsonD.length; i++){
	data += "<input type='checkbox' />"+(i+1)+"."+jsonD[i].ucode+","+jsonD[i].uname+"<br><br>";
	}
	data +="<input type='button' value='친구신청' onClick='초대장발송')/>";
}
	

	list.innerHTML=data;
}

</script>

	<input type="text" class="searchlist" name="searchlist"
		placeholder="찾으실 아이디를 아주 정확히 입력해주세요" />

	<div id="list"></div>
	<div id="email"></div>


	<input type="button" value="검색" onClick="callFriendSearch()" />

</body>
</html>