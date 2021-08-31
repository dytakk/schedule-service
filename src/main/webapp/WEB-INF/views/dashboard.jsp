<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<script src="resources/js/schedule.js"></script>

<script>

</script>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>SCHEDULE DASHBOARD</title>
<link rel="stylesheet" href="resources/css/calendar.css">

	
<body>

<form action="sendConfirm" method="post" enctype="multipart/form-data" name='all'>
	<div class="wideZone">

		<div class="sideForm">
			<div style="width: 100%; height: 50px;" class="infoBlank">
			${title},${location },${contents }

				<input type="button" class="sideAddBtn" value="add Event+"
					onclick="modalOpen()" />
			</div>
			<div id="sideInfo" class="infoBlank"  >
				<!-- style="width:100%; height:620px;"  -->
			</div>
				<input type="button" class="submit2" value="로그아웃" onClick="sendLogoutInfo()" />
					${title},${location },${contents }
					<input type="hidden" name="uCode" value="${uCode }" />
		</div>
		<div class="MngForm">
			<div class="calendar">
				<div class="header">
					<div class="year-month"></div>
					<div class="nav">
						<button class="nav-btn go-prev" onclick="prevMonth()">&lt;</button>
						<button class="nav-btn go-today" onclick="goToday()">Today</button>
						<button class="nav-btn go-next" onclick="nextMonth()">&gt;</button>
					</div>
				</div>
				<div class="main">
					<div class="days">
						<div class="day">일</div>
						<div class="day">월</div>
						<div class="day">화</div>
						<div class="day">수</div>
						<div class="day">목</div>
						<div class="day">금</div>
						<div class="day">토</div>
					</div>
					<div class="dates"></div>
				</div>
			</div>
		</div>
	<div id="main">
		<input type="button" class="submit" value="팀관리" onClick="team()"/> 
		<input type="button" class="submit" value="스케줄관리" onClick="schedule()"/>
		<input type="button" class="submit" value="친구추가" onClick="friends()"/>
	</div>

		<div id="modal" class="modal-overlay">
			<div class="modal-window">
				<div class="close-area" onClick="modalClose()">X</div>
				<div class="content">
					<!--팀 콤보박스, 제목,내용,날짜,사진,버튼 -->
					<div id="addData">
						<div id="teamChoice"></div>
						<div id="sdTitle">
							<input type="text" placeholder="event name" />
						</div>
						<div id="dateDiv"></div>
						<div id="content"></div>
						<div id="picture"></div>

						</div>
				</div>
				</div>
			</div>
		</div>
	

<script src="resources/js/calendar.js"></script>
	</form>
		
</body>

</html>
