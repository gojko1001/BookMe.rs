function getUsers(){
	
	$.ajax({
		url:"../TuristickaAgencija/rest/users/all",
		method: "GET"
	}).success(function(data){
		console.log(data);
	});
	
	
}

$("#aLogin").click(function() {
  $("div#logModal").show();
});

$("#aRegistration").click(function() {
  $("div#regModal").show();
});

$(window).click(function(event) {
  if (event.target == $("div#logModal")) {
   $("div#logModal").hide();
  }
});

$(".close").click(function() {
  $("div#logModal").hide();
  $("div#regModal").hide();
})
