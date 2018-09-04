// 다음주소 api
function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var fullAddr = ''; // 최종 주소 변수
            var extraAddr = ''; // 조합형 주소 변수

            // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                fullAddr = data.roadAddress;

            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                fullAddr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
            if(data.userSelectedType === 'R'){
                //법정동명이 있을 경우 추가한다.
                if(data.bname !== ''){
                    extraAddr += data.bname;
                }
                // 건물명이 있을 경우 추가한다.
                if(data.buildingName !== ''){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('sample6_postcode').value = data.zonecode; //5자리 새우편번호 사용
            document.getElementById('sample6_address').value = fullAddr;

            // 커서를 상세주소 필드로 이동한다.
            document.getElementById('sample6_address2').focus();
        }
    }).open();
}

// 다큐먼트 레디 시작 !!!
$(document).ready(function(){
	
	//중복확인 버튼
	$("#idck").off().on("click", function() {
		var id = $("#usr").val();
		console.log(id);
        $.ajax({
            type : 'POST',
            data : {"id":id},
            url : "/idcheck"
        }).done(function(data, e){
        	var status = JSON.parse(data).status;
        	console.log(status);
        	if(status == 0){
        		$("#usr").css("border","red solid 1px");
        		$("#usr").css("background-color","#faadad");
        		alert("이미 아이디가 있습니다.");
        	}else if(id == ""){
        		alert("잘못된 아이디 입니다.");
        	}else if(id != "" | status != 0){
        		alert("가입 가능한 아이디 입니다.");
        		$("#usr").css("border","#ccc solid 1px");
        		$("#usr").css("background-color","white");
        		$("#joinBtn").removeAttr("disabled");
        		//가입 버튼 클릭 
        		$("#joinBtn").on("click", function(){
        			var id = $("#usr").val();
        			var name = $("#user-Nm").val();
        			var password = $("#pwd").val();
        			var password_check = $("#pwd_check").val();
        			var postcode = $("#sample6_postcode").val();
        			var address1 = $("#sample6_address").val();
        			var address2 = $("#sample6_address2").val();
        			var phone = $("#phone").val();
        	    	
        	    	var d = {
        	    			"id" : id,
        	    			"name" : name,
        					"password" : password,
        					"postcode" : postcode,
        					"address1" : address1,
        					"address2" : address2,
        					"phone" : phone
        	    	}
        	    	
        	    	if (d.id == ""){
        	    		alert("아이디를 입력하세요.");
        	    	}else if (d.name == ""){
        	    		alert("이름을 입력하세요.");
        	    	}else if (d.password == ""){
        	    		alert("비밀번호를 입력하세요.");
        	    	}else if(d.password != password_check){
        	    		alert("비밀번호가 일치 하지 않습니다.");
        	    	}else if (d.postcode == ""){
        	    		alert("우편번호를 입력하세요.");
        	    	}else if (d.address1 == ""){
        	    		alert("주소를 입력하세요.");
        	    	}else if (d.address2 == ""){
        	    		alert("상세주소를 입력하세요.");
        	    	}else if (d.phone == ""){
        	    		alert("전화번호를 입력하세요.");
        	    	}else {
        	    		$.ajax({
        	    			type : "post",
        	    			url : "/join",
        	    			data : d
        	    		}).done(function(data){
        	    			
        	    		});
        	    		location.href = "/";
        	    		alert('"' + name + '"' + "님 환영합니다.");
        	    	}
        		});
        	}else{
        		// 중복확인 없이 가입버튼 눌렀을 경우
        		$("#joinBtn").on("click", function(e){
        			e.preventDefault(e);
        			alert("이메일 중복확인이 필요 합니다.");
        			location.href = "join.html";
        		});
        	}
        });
    });
	
	


	
	
});// 다큐먼트 레디 끄으읏!!!

//전화번호 숫자만 입력받기
function InputOnlyNumber(event){
	if (event.keyCode >= 48 && event.keyCode <= 57) { //숫자키만 입력
        return true;
    } else {
        event.returnValue = false;
    }    
} 



