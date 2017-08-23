var months = [ "January", "February", "March", "April", "May", "June", 
               "July", "August", "September", "October", "November", "December" ];

function loadEarths() {
	
	var queryString = "/get" + window.location.pathname;
	var date = window.location.pathname.split("/"); //4 elements hopefully
	$("#timestamp > span").text(months[parseInt(date[2])-1] + " " + date[3] + " " + date[1]);
	
	$.ajax({
	  method: "GET",
	  dataType: "json",
	  url:	queryString 
	}).done(function(data) {
	    $.each(data.satellites, function (i, sat) {
    		console.log(sat.time);
    		$("<img>", { "src": "data:image/jpeg;base64," + sat.image, "width": "400px", "height": "400px"})
				.prependTo("#earthContainer");
		});

  }).fail(function(data) {
	  console.log("fail");    
  }).complete(rotateEarths);
}

function rotateEarths() {
    var interval;
    $('#earthContainer').fadeIn(300);
    
    function fadeInLastImg()
    {
        var backImg = $('#earthContainer img:last');
        backImg.hide();
        backImg.remove();
        $('#earthContainer').prepend(backImg);
        backImg.fadeIn()
    };
        
    _intervalId = setInterval(function() {
        fadeInLastImg();
    }, 1000 );
}

$(document).ready(function(){
	loadEarths();
});

