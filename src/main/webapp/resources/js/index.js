//스크롤 메뉴바 이벤트
var didScroll; 
var lastScrollTop = 0; 
var delta = 5; 
var navbarHeight = $("header").outerHeight(); 
    
$(window).scroll(function(event){
    didScroll = true; 
}); 

setInterval(function(){
    if(didScroll){
        hasScrolled(); 
        didScroll = false;
    }
}, 200); 

function hasScrolled(){
    var st = $(this).scrollTop();
    
    if(Math.abs(lastScrollTop - st) <= delta) 
        return; 
    
    if (st > lastScrollTop && st > navbarHeight){
        $("header").removeClass("nav-down").fadeOut().addClass("nav-up");
    } else {
        if(st + $(window).height() < $(document).height()){
            $("header").removeClass("nav-up").addClass("nav-down").fadeIn();
           }
    } 
    lastScrollTop = st; 
}


//상단으로 이동 버튼
$(function() {
    $(window).scroll(function() {
        if ($(this).scrollTop() > 500) {
            $('#move_top').fadeIn();
        } else {
            $('#move_top').fadeOut();
        }
    });
    
    $("#move_top").click(function() {
        $('html, body').animate({
            scrollTop : 0
        }, 400);
        return false;
    });
});

//다큐먼트 레디 !!!!!
$(document).ready(function(){
	//좋아요 갯수 
    $.ajax({
       	type : "post",
       	url : "/web/likeOne"
       }).done(function(data){
    	    var count = JSON.parse(data).likeOne.count;
			$("#like_count").empty();
			$("#like_count").append(count);
       });
    //좋아요 버튼 클릭 이벤트
    $("#like").on("click", function(){
       randomize();
       $.ajax({
       	type : "post",
       	url : "/web/like"
       }).done(function(data){
           $.ajax({
			type : "post",
			url : "/web/likeOne"
			}).done(function(data){
	    	    var count = JSON.parse(data).likeOne.count;
				$("#like_count").empty();
				$("#like_count").append(count);
			});
       });
    });
    
    //로그인
    $("#loginBtn").on("click", function(e){
    	e.preventDefault();
 	   var id = $("#email").val();
 	   var password = $("#password").val();
 	    $.ajax({
 	    	type : "post",
 	    	url : "/web/login",
 	    	data : {"id" : id, "password" : password }
 	    }).done(function(data){
 	    	console.log("로그인 데이터 : " + data);
 	    	var d = JSON.parse(data);
 	    	var name = d.name;
 	    	if(d.status!=0){
 	    		iflogin();
 	    		layerOut();
 	    		alert('"' + name + '"' + " 님 환영합니다.");
 	    	}else if(d.status!=1){
 	    		alert("정보가 맞지 않습니다.");
 	    	}
 	    });
    });

    //세션 체크
	$.ajax({
		type: "post",
		url: "/web/userCheck"
	}).done(function(data){
		var d = JSON.parse(data);
		console.log("유저 세션 체크 데이터 : " + data);
		var list = d.list;
		if(list != null) {
			iflogin();
			layerOut();
		}
	});
    
    //로그 아웃!
    $("#sign_out").on("click", function(){
    	$.ajax({
    		type: "post",
    		url: "/web/logout"
    	}).done(function(data) {
    		location.href="index.html";
    	});
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
    
}); //다큐먼트레디 끝!

// 레이어 아웃 모듈
function layerOut(){
    $("#layer").css("display", "none");
    $("container").css("opacity", "1");
    $("#login-box").css("display", "none");
}

//로그인 성공시
function iflogin(){
	$("#sign").css("display", "none");
    $("#sign_out").css("display", "inline-block");
    $("#myinfo").css("display", "inline-block");
}

//좋아요 랜덤 색 변경 모듈
function randomize() {
  document.getElementById('heart').style.color = randomColors();
}
function randomColors() {
  return '#' + Math.floor(Math.random() * 16777215).toString(16);
}




