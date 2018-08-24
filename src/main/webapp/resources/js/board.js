$(document).ready(function(){
	
	//세션 체크후 세션 있을 경우에만 글쓰기 버튼 보여주기
    //세션 체크
//	$.ajax({
//		type: "post",
//		url: "/web/userCheck"
//	}).done(function(data){
//		var d = JSON.parse(data);
//		console.log("유저 세션 체크 데이터 : " + data);
//		var list = d.list;
//		if(list != null) {
//			console.log("새로만든 : " + list);
//			
//		}
//	});
	//글쓰기 버튼 클릭시 write.html로 이동
	$("#write_button").on("click", function(){
		location.href = "write.html";
	});
	
	
}); // 다큐먼트레디 끄으으읏!


