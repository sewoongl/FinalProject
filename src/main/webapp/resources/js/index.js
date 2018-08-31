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
       	url : "/likeOne"
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
    	   url : "/like"
       }).done(function(data){
           $.ajax({
			type : "post",
			url : "/likeOne"
			}).done(function(data){
	    	    var count = JSON.parse(data).likeOne.count;
				$("#like_count").empty();
				$("#like_count").append(count);
			});
       });
    });
    

}); //다큐먼트레디 끝!

//좋아요 랜덤 색 변경 모듈
function randomize() {
  document.getElementById('heart').style.color = randomColors();
}
function randomColors() {
  return '#' + Math.floor(Math.random() * 16777215).toString(16);
}




