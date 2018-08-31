$(document).ready(function(){
    /*홈으로 가기*/
   $("#back_home").on("click", function(data){
       location.href = "/"
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
    
    //프로필 이미지 
    $("#img_submit").on("click", function(e){
    	e.preventDefault();
    	
//    	console.log($("#file").val());
        $.ajax({
        	type : "post",
        	url : "/profileImg",
        	data : new FormData($("#form")[0]),
            contentType:false,
            cache:false,
            processData:false
        }).done(function(data){
        	console.log("aaaaaaaaa : "+data);
        	
        	var status = JSON.parse(data).status;
        	var userNo = JSON.parse(data).userNo;
        	if (status != 1){
        		alert("로그인 하세요.");
        	}else if (status == 1){
        		alert("프로필 이미지가 변경 되었습니다.");
        		
				console.log("뉴데이터 : " +  userNo);
				var newData = userNo;
        		$.ajax({
        	    	type : "post",
        	    	url : "/newData",
        	    	data : {"newData" : newData}
        	    }).done(function(data){
        	    	console.log("이미지를 가져와보자 : "+data);
        	    	var dns = JSON.parse(data).dns;
//        	    	URLEncoder.encode(dns, "UTF-8");
//        	    	console.log(d.user);
        	    	$("#myinfo_img").attr("src", dns);
        	    	location.href = "myinfo.html";
        	    	return false;
        	    });
        		
        	}else{
        		alert("로그인 하세요.");
        	}
        });
    });
    
    //세션에 있는 유저 데이터 불러오기 
    $.ajax({
    	type : "post",
    	url : "/infoData"
    }).done(function(data){
    	console.log("세션 유저 데이터 : "+data);
    	var d = JSON.parse(data).user;
		
    	if(d == null || d == ""){
    		console.log("디폴트 이미지");
    		return false;
    	}else if(d != null){
			$("#email_p").text(d.email);
			$("#name_p").text(d.name);
			$("#post_code").text(d.postcode);
			$("#address_1").text(d.address1);
			$("#address_2").text(d.address2);
			$("#phone").text(d.phone);
			$("#join_date").text(d.regDate);
			if(d.dns != null){
				$("#default_img").css({"background":"none"});
				$("#myinfo_img").attr("src", d.dns);
			}
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
	        		url : "/changePw",
	        		data : {"password" : newPw, "userNo" : d.userNo}
	        }).done(function(data){
	        	location.href = "/";
	        });
	    });
	    
	    //회원 탈퇴
		$("#leave_confirm").on("click", function(e){
			e.preventDefault();
			console.log("삭제될 유저 번호 : " + d.userNo);
			$.ajax({
	    		type : "post",
	    		url : "/leaveMember",
	    		data : {"userNo" : d.userNo}
	    	}).done(function(data) {
	    		location.href = '/';
	    		alert("잘가라 즐거웠다.");
	    	});
	    });
		
		
    });
    
}); //다큐멘트 레디 끄으읏!!!


// 레이어 아웃 모듈
function layerOut(){
    $("#layer").css("display", "none");
    $("container").css("opacity", "1");
    $(".pw_nm_change").css("display", "none");
}