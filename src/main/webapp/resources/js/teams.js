$(document).ready(function(){
    
	//팀데이터 부르는 부분
	$.ajax({
    	type : "post",
    	url : "/teams"
    }).done(function(data){
    	var result = JSON.parse(data).result;
    	$("container").empty();
    	for(var i = 0; i < 6 ; i++){
    		var html = "<ul class='uls'>";
    		for(var j = (i*5); j <= (i*5)+4; j++){
        		html += "<li class='imgList' id="+result[j].team_id+"><img class='img' src=" + result[j].emblems + ">";
        		html += "<div class='li_disc'>";
        		html += "<p class='teamNm'>" + result[j].team_name + "</p>";
        		html += "<p class='teamNk'>" + result[j].team_nick + "</p>";
        		html += "</div></li>";
    		}
    		$("container").append(html);
    		html += "</ul>";
    	}
    	
    	//각 li 클릭시 그 값에 맞는 팀 선수 목록 불러오기 
    	$(".imgList").on("click",function(){
    		var team_id = $(this).attr('id');
    		$.ajax({
    	    	type : "post",
    	    	url : "/players",
    	    	data : {"team_id" : team_id}
    	    }).done(function(data){
    	    	$("#players").css("display","block");
    	    	$("#layer").css("display","block");
    	    	var result = JSON.parse(data).result;
    	    	console.log(result);
    	    	$("#players_data").empty();
    	    	for(var i = 0; i < result.length ; i++){
    	    		var html = "<tr><td class='name'>"+result[i].firstName+" "+result[i].lastName+"</td>";
    	    		html += "<td class='jersey'>"+result[i].jersey+"</td>";
    	    		html += "<td class='position'>"+result[i].pos+"</td>";
    	    		html += "<td class='height'>"+result[i].heightMeters+"</td>";
    	    		html += "<td class='weight'>"+result[i].weightKilograms+"</td>";
    	    		html += "<td class='birth'>"+result[i].dateOfBirthUTC+"</td></tr>";
    	    		$("#players_data").append(html);
    	    	}
    	    });
    	    	
    	});
    	
    }); //팀데이터 부르는 부분 끝
	
	
}); // 다큐먼트 레디 끝






