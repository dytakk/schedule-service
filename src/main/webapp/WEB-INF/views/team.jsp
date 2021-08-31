<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="resources/js/schedule.js"></script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<link href="${pageContext.request.contextPath}/resources/css/team.css"
	type="text/css" rel="stylesheet" />

<script type="text/javascript">


//sendJsonData={Key:Value} 여기서는 teName은 addTeam에 있는 TeamBean의 teName에  teamName을 저장한다는 뜻!

function addTeam(){
	let teamName = prompt('팀이름 입력해주세요.');
	let sendJsonData ={teName:teamName};
	let clientData = JSON.stringify(sendJsonData);
	
	postAjax('schedule/addTeam',clientData,'getTeamList','json');
}

function callTeamList(){
	/* let sendJsonData = [];
	sendJsonData.push({mbId:"qwert8888"});
	let clientData = JSON.stringify(sendJsonData); */
	postAjax('schedule/teamList', '', 'getTeamList','json');
}

function getTeamList(jsonData){
	let data="<main class='title'>Team List</main><br><br><br>";
	for(var i=0; i<jsonData.length; i++){
		data = data + "<section class='teamList' onClick='callMemberList(" + jsonData[i].teCode  +")'>"+ (i+1) +") "+ jsonData[i].teName +"</section><br><br>";
	}
	let teamList = document.getElementById("teamlist");
	teamList.innerHTML = data;
}

//callMemberList. funciton의 parameter값이 jsonData[i].teCode이구. 그 parameter를 아래의 parameter값?으로 넣어 jsondata 전송
function callMemberList(tecode){
	//			key값(bean에 있는 값), value
	let sendJsonData ={teCode:tecode};
	let clientData = JSON.stringify(sendJsonData);
	postAjax('schedule/memberList',clientData,'getMemberList','json');
}	

function getMemberList(jsonData){
	let data="<div class='title'>Member List</div><br><br><br>";
	
	for(var i=0; i<jsonData.length;i++){
		data = data + "<span class='teamList'> "+ (i+1) +") "+ 
		jsonData[i].cgName+"멤버 : "+jsonData[i].mbName +"</span><br><br>";		
	}
	
	data +="<input type='button' class='submit' value='멤버추가' onClick='friendsList()'>";
	let memList = document.getElementById("memberList");
	memList.innerHTML = data;
}





function friendsList(){
	postAjax('schedule/friends','','getFriendsList','json');
}
//데이터랑 문자열 +로묶어.   "오라ㅓㄴ돌ㄷ ''   ㅇㄹㅇ"    ,    ' ㄴㅇㄷ"ㄴ"ㄴㅇㄴ'
function getFriendsList(jsonData){
	let data="<div class='title'>Friends List</div><br><br><br>";
	for(var i=0; i<jsonData.length; i++){
		data = data + "<input type='checkbox' name='fBox' value=' "+jsonData[i].mbId+" ' >"+jsonData[i].mbId +" : "+  jsonData[i].mbName +"</span><br><br>";
	}
	data += "<input type='button' class='submit' value='초대하기'	onClick='sendFriendsList()'>";
	let friendsList = document.getElementById("friendsList");
	friendsList.innerHTML = data;
} 
//getFriendsList에있는 makeHtml구문의 name은 fBox고, 아래의 function과 연결 해주는 거임..
function sendFriendsList(){
	let fBox=document.getElementsByName("fBox");
	let sendJsonData = [];
	let tdetail = [];
	for(var i=0; i<fBox.length; i++){
		if(fBox[i].checked){
			tdetail.push({mbId:fBox[i].value});
		}
	}
	sendJsonData.push({teCode:"210729001",tdetails:tdetail});
	let clientData = JSON.stringify(sendJsonData);
	alert(clientData);
	postAjax('schedule/addFriends',clientData,'getFriendsList','json');
}

</script>
<!-- 					RestApiController참조 -->
<body >

<nav id="main">
		<input type="button" id= "addTeam" class="submit" value="새팀등록" onClick="addTeam()"/>
		<input type="button"  class="submit" value="팀관리" onClick="callTeamList()" /> 
		<main id="teamlist"></main>
		<div id="memberList"></div>
		<input type="button" class="submit" value="친구목록" onClick="friendsList()"  /> 
		<div id="friendsList"></div>
		<div id="friendsList2"></div>	
	</nav>

</body>
</html>



