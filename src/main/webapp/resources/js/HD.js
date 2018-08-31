$(document).ready(function(){
	//팀이미지 불러오기
	$.ajax({
		type : "post",
		url : "/teams"
	}).done(function(data){
		var teamL = JSON.parse(data).result;
		for(var i = 0; i < teamL.length; i++){
			var html = "<ul class='cover'>";
			html += "<li class='teamEmblems'><img class='HDimg' src='"+ teamL[i].emblems +"'></li>";
			html += "<li class='teamName' id='tn"+ i +"'></li></ul>";
			$("#container").append(html);
		}
		csvList();
		
		//각 csv리스트 클릭했을경우 
		$(".cover").on("click", function(){
			$("#layer").css("display", "block");
			$("#loading").css("display","block");
			$("#loadingMent").css("display","block");
			var fList = $(this).children("li").eq(1).html().replace("<br>"," ");
			$.ajax({
				type: "post",
				url: "/click",
				data: {"click": fList}
			}).done(function(data){
				var inputDir = data.inputDir;
				console.log("가져온 인풋 경로 : "+inputDir);
				//골 맵리듀스 시작
				$.ajax({
					type: "post",
					url: "/MRgoals",
					data: {"MRgoals": inputDir}
				}).done(function(data){
					var saveDir = data.saveDir;
					var bringDT = "goalChart";
					var urls = "/goals";
					console.log("저장된 경로 : "+saveDir);
					drawing(saveDir,bringDT,urls);
				});//골 끝
				//리바운드 맵리듀스 시작
				$.ajax({
					type: "post",
					url: "/MRrebound",
					data: {"MRrebound": inputDir}
				}).done(function(data){
					var saveDir = data.saveDir;
					var bringDT = "reboundChart";
					var urls = "/rebound";
					console.log("저장된 경로 : "+saveDir);
					drawing(saveDir,bringDT,urls);
				});//리바운드 끝
				//어시스트 맵리듀스 시작
				$.ajax({
					type: "post",
					url: "/MRassist",
					data: {"MRassist": inputDir}
				}).done(function(data){
					var saveDir = data.saveDir;
					var bringDT = "assistChart";
					var urls = "/assist";
					console.log("저장된 경로 : "+saveDir);
					drawing(saveDir,bringDT,urls);
				});//어시스트 끝
				//스틸 맵리듀스 시작
				$.ajax({
					type: "post",
					url: "/MRsteal",
					data: {"MRsteal": inputDir}
				}).done(function(data){
					var saveDir = data.saveDir;
					var bringDT = "stealChart";
					var urls = "/steal";
					console.log("저장된 경로 : "+saveDir);
					drawing(saveDir,bringDT,urls);
				});//골 끝
				
				
			});
			
		});
		
		
	});
	
	
	//차트 버튼 function
	function chartBtn(){
		$("#goalBtn").on("click", function(){
			$("#reboundChart").css("display","none");
			$("#assistChart").css("display","none");
			$("#stealChart").css("display","none");
			$("#goalChart").css("display","block");
		});
		$("#reboundBtn").on("click", function(){
			$("#reboundChart").css("display","block");
			$("#assistChart").css("display","none");
			$("#stealChart").css("display","none");
			$("#goalChart").css("display","none");
		});
		$("#assistBtn").on("click", function(){
			$("#reboundChart").css("display","none");
			$("#assistChart").css("display","block");
			$("#stealChart").css("display","none");
			$("#goalChart").css("display","none");
		});
		$("#stealBtn").on("click", function(){
			$("#reboundChart").css("display","none");
			$("#assistChart").css("display","none");
			$("#stealChart").css("display","block");
			$("#goalChart").css("display","none");
		});
	}
	
	//차트 그리기 
	function drawing(saveDir, bringDT, urls){
		$.ajax({
			type: "post",
			url: urls,
			data: {"chart": saveDir}
		}).done(function(data){
			var chart = data.resultList;
				var RBKey= chart[0][0];
				var RBVal= chart[0][1];
				var ChartID = bringDT;
				$("#loading").css("display","none");
				$("#loadingMent").css("display","none");
				$("#goalChart").css("display","block");
				$("#chartBtns").css("display","block");

				
				charts(chart,ChartID);
		});
	}

	//차트 디테일
	function charts(chart,ChartID){
		var four = chart[0][1];
		var five = chart[1][1];
		var six = chart[2][1];
		var seven = chart[3][1];
		var three = chart[4][1];
		var titles;
		if (ChartID == "goalChart"){
			titles = '시즌 별 총 득점';
		}else if(ChartID == "reboundChart"){
			titles = '시즌 별 총 리바운드';
		}else if(ChartID == "assistChart"){
			titles = '시즌 별 총 어시스트';
		}else if(ChartID == "stealChart"){
			titles = '시즌 별 총 스틸';
		}  
		AmCharts.makeChart(ChartID,
				{
					"type": "radar",
					"categoryField": "year",
					"color": "#e7e7e7",
					"startDuration": 1,
					"fontSize": 15,
					"graphs": [
						{
							"balloonText": "[[value]] 개",
							"bullet": "round",
							"id": "AmGraph-1",
							"valueField": "val",
							"fillAlphas": 0.1
						}
					],
					"colors" : ["brown","#d8854f","#eea638","#a7a737","#86a965","#8aabb0","#69c8ff","#cfd27e","#9d9888","#916b8a","#724887","#7256bc"],
					"textcolor":["#e7e7e7"],
					"guides": [],
					"valueAxes": [
						{
							"axisTitleOffset": 20,
							"gridType": "circles",
							"id": "ValueAxis-1",
							"minimum": 0,
							"axisAlpha": 0.15,
							"dashLength": 3
						}
					],
					"allLabels": [],
					"balloon": {},
					"titles": [{"text": titles,"size": "25"}],
					"dataProvider": [
						{
							"year": "2014",
							"val": four
						},
						{
							"year": "2015",
							"val": five
						},
						{
							"year": "2016",
							"val": six
						},
						{
							"year": "2017",
							"val": seven
						},
						{
							"year": "2013",
							"val": three
						}
					],
					"export": {
					    "enabled": true,
					    "menu": [ {
					    	"class": "export-main",
					        "menu": [ "PNG", "JPG", "CSV" ]
					      }]
					  }
				}
			);
	}
	chartBtn(); //차트 버튼 function
	
	//csv리스트 불러오기
	function csvList(){
		$.ajax({
			type: "get",
			url: "/fileList"
		}).done(function(data){
			var fileL = data.result;
			console.log("csv 리스트"+ fileL);
			for(var j = 0; j < fileL.length; j++){
				for(var key in fileL[j]){
					var teamFullNm = fileL[j][key].split("/")[3].split(".")[0];
					var teamNm = fileL[j][key].split("/")[3].split(".")[0].split(" ")[0];
					var teamNic = fileL[j][key].split("/")[3].split(".")[0].split(" ")[1];
					$("#tn"+j).append(teamNm+"<br>"+teamNic);
				}
			}
		});
	}
	
});//다큐먼트 레디 끝