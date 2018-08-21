$(document).ready(function(){
    /*홈으로 가기*/
   $("#back_home").on("click", function(){
       location.href = "index.html"
   });
    
    //회원탈퇴 클릭 이벤트
    $("#leave_member").on("click", function(){
        $("#layer").css("display", "block");
        $("container").css("opacity", "0.5");
        $("#really_leave").css("display", "block");
    });
    
    //박스 밖 클릭시 돌아가기 기능
    $("#layer").on("click", function(){
        layerOut();
    });
    
    //세션에 있는 유저 데이터 불러오기 
    $.ajax({
    	type : "post",
    	url : "/web/infoData"
    }).done(function(data){
    	var d = JSON.parse(data).user;
    	console.log("세션 이메일 : " + d.email);
    	console.log("세션 이름 : " + d.name);
    	console.log("세션 우편번호 : " + d.postcode);
    	console.log("세션 도로명주소 : " + d.address1);
    	console.log("세션 상세주소 : " + d.address2);
    	console.log("세션 전화번호 : " + d.phone);
    	console.log("세션 가입일 : " + d.regDate);
		if(d != null){
			$("#email_p").text(d.email);
			$("#name_p").text(d.name);
			$("#post_code").text(d.postcode);
			$("#address_1").text(d.address1);
			$("#address_2").text(d.address2);
			$("#phone").text(d.phone);
			$("#join_date").text(d.regDate);
		}
	    //비번 변경창 클릭
	    $("#change_password").on("click", function(){
	        $("#layer").css("display", "block");
	        $("container").css("opacity", "0.5");
	        $("#password_change").css("display", "block");
	    });
	    
	    //비번 변경
	    $("#submit_pw").on("click", function(e){
	    	e.preventDefault();
	        var newPw = $("#change_pw").val();
	        console.log("변경할 비밀번호 : " + newPw);
	        $.ajax({
	        		type : "post",
	        		url : "/web/changePw",
	        		data : {"password" : newPw, "userNo" : d.userNo}
	        }).done(function(data){
	        	location.href = "index.html";
	        });
	    });
	    
	    //회원 탈퇴
		$("#leave_confirm").on("click", function(e){
			e.preventDefault();
			console.log("삭제될 유저 번호 : " + d.userNo);
			$.ajax({
	    		type : "post",
	    		url : "/web/leaveMember",
	    		data : {"userNo" : d.userNo}
	    	}).done(function(data) {
	    		location.href = 'index.html';
	    		alert("잘가라 즐거웠다.");
	    	});
	    });
		
		
    });
    
}); //다큐멘트 레디 끄으읏!!!

//세션 테스트
//function seschk(){
//	alert("세션 다시 체크");
//	$.ajax({
//		type : "post",
//		url : "/web/infoData"
//	}).done(function(data){
//		var d = JSON.parse(data).user;
//		console.log("세션 이메일 : " + d.email);
//		console.log("세션 이름 : " + d.name);
//		console.log("세션 우편번호 : " + d.postcode);
//		console.log("세션 도로명주소 : " + d.address1);
//		console.log("세션 상세주소 : " + d.address2);
//		console.log("세션 전화번호 : " + d.phone);
//		console.log("세션 가입일 : " + d.regDate);
//		console.log("세션 비번 : " + d.password);
//	});
//}
// 레이어 아웃 모듈
function layerOut(){
    $("#layer").css("display", "none");
    $("container").css("opacity", "1");
    $(".pw_nm_change").css("display", "none");
}