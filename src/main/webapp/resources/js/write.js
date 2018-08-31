$(document).ready(function() {
	
	//서머노트 config
    var markupStr = '게시글을 작성 하세요.'+"<br><br>"+"비속어 작성시 탈퇴 조치";
    $('#summernote').summernote({
        width: 800,
        height: 450, 
        minHeight: 450,        
        maxHeight: 450,     
        focus: true
    });
    $('#summernote').summernote("code", markupStr);
    $('#summernote').summernote({
        toolbar: [
            ['style', ['bold', 'italic', 'underline', 'clear']],
            ['font', ['strikethrough', 'superscript', 'subscript']],
            ['fontsize', ['fontsize']],
            ['color', ['color']],
            ['para', ['ul', 'ol', 'paragraph']],
            ['height', ['height']]
        ]
    });
		
    //글쓰기 버튼 클릭 에이젝스
	$("#write_btn").on("click", function(){
    	var title = $("#write_title").val();
    	var contents = $("#summernote").val();
    	console.log("제목 : " + title);
    	console.log("내용 : " + contents);
    	$.ajax({
 	    	type : "post",
 	    	url : "/boardWrite",
 	    	data : {"title" : title, "contents" : contents}
 	    }).done(function(data){
 	    	alert("글작성이 완료 되었습니다.");
 	 	    location.href = "board.html";
 	 	    console.log(data);
 	    });
    });	
		

    
    
}); //다큐먼트 레디 끄으으슷스

