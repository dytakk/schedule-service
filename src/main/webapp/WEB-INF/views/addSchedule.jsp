<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="resources/js/schedule.js"></script>
<script>
	function Confirm() {		
		let all=document.getElementsByName("all")[0];		
		console.log(all);		
		all.submit();		
		/* var tecode1 = document.getElementsByName("teCode")[0];
		var tecode2=tecode1.options[tecode1.selectedIndex].value;
		let date1 = document.getElementsByName("date")[0];
		let title1 = document.getElementsByName("title")[0];
		let location1 = document.getElementsByName("location")[0];
		let contents1 = document.getElementsByName("contents")[0];
		let mpfile1 = document.getElementsByName("mpfile")[0];		
		let form = makeForm("schedule/sendConfirm", "post");
		form.setAttribute("enctype","multipart/form-data");		
		form.appendChild(tecode2);
		form.appendChild(date1);
		form.appendChild(title1);
		form.appendChild(location1);
		form.appendChild(contents1);
		form.appendChild(mpfile1);
		document.body.appendChild(form);
		form.submit(); */
	}
	function getTeamName(jsonData) {
		let data = "";
		for (var i = 0; i < jsonData.length; i++) {
			data = "TEAM SELECT &nbsp;<select name='teCode'>팀선택";
			data += "<option value='"+jsonData[i].teCode+"' >"+jsonData[i].teName
					+ "</option></select>";
		}
		data += "<br><br><br>Title &nbsp; <input type='text' name='title'>";
		data += "<br><br><br>Location &nbsp; <input type='text' name='location'>";
		data += "<br><br><br>Contents &nbsp; <input type='text' name='contents'>";
		data += "<br><br><br>IS PUBLIC &nbsp; <select name='open'>";
		data += "<option value='week'>PUBLIC</option>";
		data += "<option value='month'>PRIVATE</option></select>";
		data += "<br><br><br>LOOP<select name='loop' >";
		data += "<option value='week'>WEEK</option>";
		data += "<option value='month'>MONTH</option>";
		data += "<option value='year'>YEAR</option></select>";
		data += "<br><br><br>UploadFile &nbsp; <input type='file' name='mpfile' >";
		data += "<br><br><br> &nbsp; <input type='button' value='CONFIRM' class='button' onClick='Confirm()'>";
		let addData = document.getElementById("addData");
		addData.innerHTML = data;
	}

	window.onload = function() {

		function onClick() {
			document.querySelector('.modal_wrap').style.display = 'block';
			document.querySelector('.black_bg').style.display = 'block';
			//document.querySelector('#date').style.display='block';
			postAjax('schedule/teamList', '', 'getTeamName', 'json');

		}
		function offClick() {
			document.querySelector('.modal_wrap').style.display = 'none';
			document.querySelector('.black_bg').style.display = 'none';
		}
		document.getElementById('modal_btn').addEventListener('click', onClick);
		document.querySelector('.modal_close').addEventListener('click',
				offClick);
	}
</script>
<style>
#button {
	left: 30px;
}

.modal_wrap {
	display: none;
	width: 500px;
	height: 500px;
	position: absolute;
	top: 50%;
	left: 50%;
	margin: -250px 0 0 -250px;
	background: #eee;
	z-index: 2;
}

.black_bg {
	display: none;
	position: absolute;
	content: "";
	width: 100%;
	height: 100%;
	background-color: rgba(0, 0, 0, 0.5);
	top: 0;
	left: 0;
	z-index: 1;
}

.modal_close {
	width: 26px;
	height: 26px;
	position: absolute;
	top: -30px;
	right: 0;
}

.modal_close>a {
	display: block;
	width: 100%;
	height: 100%;
	background: url(https://img.icons8.com/metro/26/000000/close-window.png);
	text-indent: -9999px;
}
</style>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>


<!-- addSchedule할 contents,title등의 값을 name으로 지정 form으로 보낸다 -->
<form action='sendConfirm' method='post' enctype='multipart/form-data' name='all'>
<input type="date" name="date" />	<button type='button' id="modal_btn">선택</button>
	<div class="black_bg"></div>
	<div class="modal_wrap">
		<div class="modal_close">
			<a href="#">close</a>
		</div>
		
		<div id="addData"></div>
			
			
	</div>
	</form>

</body>
</html>