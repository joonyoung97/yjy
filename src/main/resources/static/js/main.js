$(document).ready(function () {
	$(".mySlideDiv").not(".active").hide();

	setInterval(nextSlide, 5000);
});

//이전 슬라이드
function prevSlide() {
	$(".mySlideDiv").hide();
	var allSlide = $(".mySlideDiv");
	var currentIndex = 0;


	$(".mySlideDiv").each(function(index,item){
		if($(this).hasClass("active")) {
			currentIndex = index;
		}

	});


	var newIndex = 0;

	if(currentIndex <= 0) {

		newIndex = allSlide.length-1;
	} else {

		newIndex = currentIndex-1;
	}

	$(".mySlideDiv").removeClass("active");


	$(".mySlideDiv").eq(newIndex).addClass("active");
	$(".mySlideDiv").eq(newIndex).show();

}


function nextSlide() {
	$(".mySlideDiv").hide();
	var allSlide = $(".mySlideDiv");
	var currentIndex = 0;

	$(".mySlideDiv").each(function(index,item){
		if($(this).hasClass("active")) {
			currentIndex = index;
		}

	});

	var newIndex = 0;

	if(currentIndex >= allSlide.length-1) {

		newIndex = 0;
	} else {

		newIndex = currentIndex+1;
	}

	$(".mySlideDiv").removeClass("active");
	$(".mySlideDiv").eq(newIndex).addClass("active");
	$(".mySlideDiv").eq(newIndex).show();

}
