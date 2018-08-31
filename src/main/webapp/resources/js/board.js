$(document).ready(function(){
	
    //게시글 리스트 불러오기 
	$.ajax({
		type: "post",
		url: "/boardList"
	}).done(function(data){
		var d = JSON.parse(data).result;
		var blistc = JSON.parse(data).resultc;
		console.log(blistc);
		$("#count_p").text(blistc+" 건");
		$("tbody").empty();
		
		//첫 페이지 화면 
		for(var i = 0; i < 7; i++){
			var html = "<tr style='height:42px;' class='tr'><td class='test'>" + d[i].boardNo + "</td>";
			html += "<td id='bl"+ d[i].boardNo +"'>" + d[i].title + "</td>";
			html += "<td>" + d[i].name + "</td>";
			html += "<td>" + d[i].regDate + "</td>";
			html += "<td id='board_"+ d[i].boardNo +"'></td>";
			html += "</tr>";
			$("tbody").append(html);
			boardListClick();
		}
		
		//페이징 이동 버튼 
		var max_btn = Math.ceil(d.length/7);
		$(".pagination").append("<li class='first'><span>&lt;</span></li>");
		$(".first").hide();
		for(var y = 0; y < 5; y++){
			var html1 = "<li class='num'><span>"+(y+1)+"</span></li>";
			$(".pagination").append(html1);
		}
		$(".pagination").append("<li class='last'><span>&gt;</span></li>");
		
		//페이지 이동
		var previous = $(".num span").eq(0);
		var lastId = $(".num:last").index();
		//뒤로 버튼
		$(document).on("click",".first span", function(){
			console.log("돌아가기 시작");
			var firstP = $(".num span").eq(1).text();
			$(".pagination").empty();
			var fp = Number(firstP) - 6;
			console.log("돌아가는 첫페이지 : "+fp);
			$(".pagination").append("<li class='first'><span>&lt;</span></li>");
			for(var np = fp; np < fp+5; np++){
				var nextP = "<li class='num'><span>"+np+"</span></li>";
				$(".pagination").append(nextP);
				if(fp == 1){
					$(".first").hide();
				}
			}
			$(".pagination").append("<li class='last'><span>&gt;</span></li>");
			btnClick();
		});
		
		//다음버튼
		$(document).on("click",".last span", function(){
			//첫 페이지 화면 
//			$("tbody").empty();
//			var bf = 35;
//			for(var i = bf; i < bf+7; i++){
//				var html = "<tr style='height:42px;' class='tr'><td class='test'>" + d[i].boardNo + "</td>";
//				html += "<td id='bl"+ d[i].boardNo +"'>" + d[i].title + "</td>";
//				html += "<td>" + d[i].name + "</td>";
//				html += "<td>" + d[i].regDate + "</td>";
//				html += "<td id='board_"+ d[i].boardNo +"'></td>";
//				html += "</tr>";
//				$("tbody").append(html);
//				boardListClick();
//			}
			var lastP = $(".num span").eq(lastId-1).text();
			$(".pagination").empty();
			var lp = Number(lastP) + 1;
			$(".pagination").append("<li class='first'><span>&lt;</span></li>");
			console.log("다음 첫페이지 : "+lp);
			for(var fp = lp ;fp < lp+5 ; fp++){
				var nextP = "<li class='num'><span>"+fp+"</span></li>";
				if(fp <= max_btn) {
					$(".pagination").append(nextP);
				}
			}
			$(".pagination").append("<li class='last'><span>&gt;</span></li>");
			if(fp > max_btn) {
				$(".last").hide();
			}
			btnClick();
		});
		
		
		btnClick();
		
		//버튼 클릭했을때 
		function btnClick(){
			$(".num span").on("click",function(){
				var index = $(this).text()-1;
				console.log("인덱스 : "+index);
				$("tbody").empty();
				
				//가장 끝 페이지 갔을때 오류 수정
				var max;
				if(index*7+7 < d.length){
					max=index*7+7;
				}else{
					max=d.length;
				}
				
				// 다른페이지 이동했을때 게시글 리스트 그리기 
				for(var j = index*7; j< max; j++){
					var z =+ 1;
					var html2 = "<tr style='height:42px;' class='tr'><td class='test'>" + d[j].boardNo + "</td>";
					html2 += "<td>" + d[j].title + "</td>";
					html2 += "<td>" + d[j].name + "</td>";
					html2 += "<td>" + d[j].regDate + "</td>";
					html2 += "<td id='board_"+ d[j].boardNo +"'></td>";
					html2 += "</tr>";
					$("tbody").append(html2);
					boardListClick();
				}
			});
		}

		
		
		
		//게시글 클릭시 이벤트 
		function boardListClick(){
			$("tr>td:nth-child(2)").on("click", function(){
				$("#contents_box").css("display","block");
				$("#layer").css("display","block");
				var boardNo = $(this).closest(".tr").find(".test").text();
				$.ajax({
					type: "post",
					url: "/boardOne",
					data:{"boardNo": boardNo}
				}).done(function(data){
					var dt = JSON.parse(data).getOne;
					var boardNo = dt.boardNo;
					var loggedNum = JSON.parse(data).loggedNum;
					var delUN = dt.userNo;
					console.log("보드 유저번호 : "+boardNo);
					console.log("로그인된 유저번호  : "+loggedNum);
					$("#board_del").on("click", function(){
						if(loggedNum == delUN){
							boardDele(boardNo);
						}
						
					});
					
					$("#box_title").text(dt.title);
					$("#box_img").attr("src", dt.dns);
					$("#box_userNm").text(dt.name);
					$("#content").text(dt.contents);
				});
			});
		}//게시글 클릭시 이벤트  끝
		
	});// 게시글 에이젝스 끝
	
	function boardDele(boardNo){
		$.ajax({
			type: "post",
			url: "/boardDel",
			data:{"boardNo": boardNo}
		}).done(function(data){
//			alert("게시글이 삭제되었 습니다.");
		});
	}
	
	
	//글쓰기 버튼 클릭시 write.html로 이동
	$("#write_button").on("click", function(){
		location.href = "write.html";
	});
	
	
}); // 다큐먼트레디 끄으으읏!



