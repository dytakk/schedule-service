<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="${pageContext.request.contextPath}/resources/css/join.css"



	type="text/css" rel="stylesheet" />
	
	<script src="resources/js/schedule.js"></script>
<script>

function uploadFile(){
	let file = document.getElementsByName("mpfile")[0];
	let form = document.createElement("form");
	form.setAttribute("action","upload");
	form.setAttribute("method","post");
	form.enctype="multipart/form-data";
	form.appendChild(file);
	document.body.appendChild(form);
	form.submit();	
	//let form = makMultiForm("upload","post");
}
function dupCheck(obj){
	
	let uCode = document.getElementsByName("uCode")[0];
	
	if(obj.value != "재입력"){		
		//아이디 유효성 검사
		if(!isValidateCheck(1, uCode.value)){			
			uCode.value="";
			uCode.focus();		
			return;		
		}
			postAjax("IsDup","uCode="+uCode.value,"afterDupCheck",'');	
			
	
		}else{
			uCode.value="";
			uCode.readOnly = false;
			uCode.focus();
			obj.value = "중복검사";
		} 
		
	}
	
function afterDupCheck(jsonData){
	let btn = document.getElementById("dupBtn");
	let uCode = document.getElementsByName("uCode")[0];
	let msg = document.getElementById("message");
	
	if(jsonData==true){
	uCode.setAttribute("readOnly",true);
	btn.setAttribute("value", "재입력");
	msg.innerText = "사용 가능한 아이디";
	 
}else{
		uCode.value="";
		msg.innerText = "사용 불가능한 아이디";
		uCode.focus();
}
}

function pwdValidate(obj){
	let pwdMsg=document.getElementById("pwdMsg");
	
	
	
	if(charCount(obj.value,8,12)){
		if(!isValidateCheck(2,obj.value)){
			obj.value="";
			obj.focus();
			pwdMsg.innerText = "비밀번호는 영소문자,대문자,숫자,특수문자를 3가지 이상 혼합하여야 합니다.";			
		}else{
			pwdMsg.innerText="비밀번호가 .. 맞..습니다..";
		}	
	}else{
		pwdMsg.innerText = "비밀번호 글자수를 8~12이내로 입력하세요.";
	}	
}

function pwdConfirm(obj){
	let pwdMsg2=document.getElementById("pwdMsg2");
	
	let aCode=document.getElementsByName("aCode");
	if(!(aCode[0].value==aCode[1].value)){
		pwdMsg2.innerText="비번이 일치하지 않습니다.";
		aCode[1].value="";
		aCode[1].focus();
		
	}else{
		
		pwdMsg2.innerText="비밀번호가 맞... 습ㄴ..니다";
		
	}
	
}

function nameCheck(obj){
	
	if(charCount(obj.value,2,5)){
		if(!krCheck(obj.value))	{
			
			alert("한글쓰셈");
			
		}
	}else{
		alert("이름은 2-5글자로 입력해주세요");
		
	}
		
	
}


	

</script>	
<title>Join Page</title>
</head>
<!-- <body onLoad = "callMessage('${message}')">  -->
<!--  <body onLoad="isDupCheck('${message2}','${userId}')">-->

<body>

	<div id="accesszone">
		<div class="title">CHO IN</div>

		<div>
			<div id="uCode" class="item">아이디</div>
			<div class="content">
				<input type="text" name="uCode" class="box" />
			</div>
		</div>
		<div id="message"></div>
		<div class="content">
			<input type="button" id="dupBtn" class="button" value="중복검사" onClick="dupCheck(this)" />
		</div>

		<div>
		<div id="pwdMsg"></div>
			<div class="item">패스워드</div>
			<div class="content">
				<input type="password" name="aCode" class="box" onBlur="pwdValidate(this)"/>
			</div>
		</div>
		
		<div>
		<div id="pwdMsg2" ></div>
			<div class="item">패스워드 확인</div>
			<div class="content">
				<input type="password" name="aCode" class="box" onBlur="pwdConfirm()" />
			</div>
		</div>
		
	
		<div>
			<div class="item">이름</div>
			<div class="content">
				<input type="text" name="uName" class="box" onBlur="nameCheck(this)" />
			</div>
		</div>
		<div>
			<div class="item">E-mail</div>
			<div class="content">
				<input type="text" name="uMail" class="box" />
			</div>
		</div>
		
			<div> <input type="file" name="mpfile" placeholder="파일찾기" />
			<!-- <button id="upload" onClick="uploadFile()"> 업로드 </button> -->
			</div>

		<div>
			<input type="button" class="button" value="회원가입" onClick="sendJoinInfo()" />
		</div>
		<div style="text-align: center">
			<a href="/" style="text-decoration: none; color: #bbbbbb">로그인</a>
		</div>
	</div>

</body>
</html>