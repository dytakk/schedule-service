<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link
	href="${pageContext.request.contextPath}/resources/css/schedule.css"
	type="text/css" rel="stylesheet" />
<script>

var count=0;
function upload(){
	let file = document.getElementsByName("mpfile");
	let form = uploadForm("sendFiles","post");	
	for(i=file.length-1; i>=0; i--){	
	form.appendChild(file[i]);	
	}	
	document.body.appendChild(form);
	form.submit();	
}
function addUpload(){
	count++;
	let data="";
	let send = document.getElementById("send");
	send2="<input type='button' value='전송' onClick='upload()'/>"
	if(count>5){
		data="";		
	}else{
	let upload2 = document.getElementById("upload2");	
	data+="<div name='file'><input type='file' name='mpfile'/>";
	data+="<input type='button' value='삭제' onClick='deleteFile(this)' /></div>";	
	}	
	send.innerHTML=send2;
	upload2.innerHTML +=data;
}
function uploadForm(action,method, name=null){	
	let form = document.createElement("form");	
	if(name!=null){
		form.setAttribute("name",name); }
		form.setAttribute("action",action);
		form.setAttribute("method",method);	
		form.setAttribute("enctype","multipart/form-data");		
		return form;
}
function deleteFile(data){
	data.parentNode.remove();
}
</script>
<script src="resources/js/schedule.js"></script>
<meta charset="UTF-8">
<title>Schedule Manager</title>
</head>
<body>
	<input type="button" value="추가" onClick="addUpload()" />
	
	<div id='send'>				</div>
	<div id="uploadlist">

		<div id="upload2"></div>

	</div>
	<img src="/resources/image/${pic1}"/>

</body>
</html>