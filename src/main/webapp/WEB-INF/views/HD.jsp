<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
<meta charset="UTF-8">
<title>Leader</title>
<!-- css 파일들 -->
<link rel="stylesheet" href="resources/css/common.css">
<link rel="stylesheet" href="resources/css/HD.css">
<!--사이트 아이콘-->
<link rel="shortcut icon" href="img/NAA.png">
<!--아이콘 라이브러리-->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<!--제이쿼리-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<!-- 부트스트랩 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<!-- amchart 라이브러리 -->
<script type="text/javascript" src="https://www.amcharts.com/lib/3/amcharts.js"></script>
<script type="text/javascript" src="https://www.amcharts.com/lib/3/radar.js"></script>
<!-- amchart 라이브러리 -->
<!-- ===============================export 라이브러리 ========================= -->
<link  type="text/css" href="resources/amcharts/plugins/export.css" rel="stylesheet">
<script src="resources/amcharts/plugins/export.min.js"></script>
<!--메뉴 atc_head 영어부분 구글 폰트-->
<link href="https://fonts.googleapis.com/css?family=Quicksand" rel="stylesheet">
<!-- js 파일들 -->
<script src="resources/js/common.js"></script>
<script src="resources/js/HD.js"></script>
</head>
<body>
	<div id="container"></div>
	<div id="goalChart"></div>
	<div id="reboundChart"></div>
	<div id="assistChart"></div>
	<div id="stealChart"></div>
	<div id="chartBtns">
		<button id="goalBtn" title="시즌별 총 득점 수" class="btn btn-default" type="button">Total Point</button>
		<button id="reboundBtn" title="시즌별  총 리바운드 수" class="btn btn-default" type="button">Total Rebound</button>
		<button id="assistBtn" title="시즌별  총 어시스트 수" class="btn btn-default" type="button">Total Assist</button>
		<button id="stealBtn" title="시즌별  총 스틸 수" class="btn btn-default" type="button">Total Steal</button>
	</div>
	
	<!-- 레이어 -->
    <div id="layer"></div>
    <div id="backGround"></div>
    <!-- 홈버튼 -->
    <i id="home" class="fa fa-home" title="홈으로" aria-hidden="true"></i>
    <!-- 로딩중 -->
    <img id="loading" src="resources/img/loading1.gif">
    <h1 id="loadingMent">데이터를 읽는 중 입니다</h1>
</body>
</html>