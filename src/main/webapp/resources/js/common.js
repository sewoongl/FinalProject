$(document).ready(function() {
	check();
    //세션 체크
//	$.ajax({
//		type: "post",
//		url: "/userCheck"
//	}).done(function(data){
//		var list = JSON.parse(data).list;
//		console.log("유저 체크 세션 : "+list);
//		if(list != null) {
//			iflogin();
//			layerOut();
//		}
//	});
	
	//메인 타이틀 클릭시 index.html로 이동
	$("#title").on("click", function(){
		location.href = "/"
	});
	
    //sign_in 버튼 클릭 이벤트
    $("#sign").on("click", function(){
        $("#layer").css("display", "block");
        $("container").css("opacity", "0.5");
        $("#login-box").css("display", "block");
    });
    
    //박스 밖 클릭시 돌아가기 기능
    $("#layer").on("click", function(){
        layerOut();
    });
    
    //조인 버튼 클릭시 join.html 이동
    $("#join").on("click", function(){
        location.href="join.html";
    });
    
    //마이 인포 클릭시 myinfo.html 이동
    $("#myinfo").on("click", function(){
        location.href="myinfo.html";
    });
    
    //게시판 GO버튼 클릭시 board.html로 이동
    $("#link_board").on("click", function(){
    	location.href="board.html";
    });
    
    //팀별 선수정보 GO버튼 클릭시 teams.html로 이동
    $("#link_status").on("click", function(){
    	location.href="teams.html";
    });
    
    //분석 GO버튼 클릭시 hadoop.jsp로 이동
    $("#link_leader").on("click", function(){
    	location.href="/HD";
    });
	

	
    //로그인
    $("#loginBtn").on("click", function(e){
    	e.preventDefault();
 	   var id = $("#email").val();
 	   var password = $("#password").val();
 	    $.ajax({
 	    	type : "post",
 	    	url : "/login",
 	    	data : {"id" : id, "password" : password }
 	    }).done(function(data){
 	    	console.log("로그인 데이터 : " + data);
 	    	var d = JSON.parse(data);
 	    	var name = d.name;
 	    	var img = d.dns;
 	    	if(d.status!=0){
 	    		iflogin();
 	    		layerOut();
 	    		alert('"' + name + '"' + " 님 환영합니다.");
 	    		$("#like").removeAttr("disabled");
 	    	}else if(d.status!=1){
 	    		alert("정보가 맞지 않습니다.");
 	    	}
 	    });
    });
    
    //로그 아웃!
    $("#sign_out").on("click", function(){
    	$.ajax({
    		type: "post",
    		url: "/logout"
    	}).done(function(data) {
    		location.href="index.html";
    		alert("로그아웃 되었습니다.")
    	});
    });
    
	// 홈 아이콘 클릭시 index.html로 이동
	$("#home").on("click", function(){
		location.href = "/";
	});
	
});  //다큐먼트 레디 끄으으슷스

var flag = false;

//세션 체크 함수
var check = function(){
	$.ajax({
		type: "post",
		url: "/userCheck"
	}).done(function(data){
		var d = JSON.parse(data);
		var list = d.list;
		console.log("리스트 : "+list.userNo);
		if(list != null){
			flag = true;
		}
	});
}

//레이어 아웃 모듈
function layerOut(){
    $("#layer").css("display", "none");
    $("container").css("opacity", "1");
    $("#login-box").css("display", "none");
    $("#players").css("display", "none");
    $("#contents_box").css("display", "none");
    $("#goalChart").css("display", "none");
    $("#reboundChart").css("display", "none");
    $("#assistChart").css("display", "none");
    $("#stealChart").css("display", "none");
    $("#chartBtns").css("display","none");
}

//로그인 성공시
function iflogin(){
	$("#sign").css("display", "none");
    $("#sign_out").css("display", "inline-block");
    $("#myinfo").css("display", "inline-block");
    $("#write_button").css("display", "inline-block");
    $("#write_btn").removeAttr("disabled");
    $("#like").removeAttr("disabled");
}