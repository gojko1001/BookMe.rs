function getUsers(){
	
	$.ajax({
		url:"../TuristickaAgencija/rest/users/all",
		method: "GET"
	}).success(function(data){
		console.log(data);
	});
	
	
}


$(document).ready(function(){
	// ModalBox Registration/Login
	$("#aLogin").click(function() {
	  $("div#logModal").slideDown("fast");
	});

	$("#aRegistration").click(function() {
	  $("div#regModal").slideDown("fast");
	});

	$("#regToLog").click(function(){
		$("div#regModal").hide(50, function(){
			$("div#logModal").fadeIn();
		});
	})

	$("#logToReg").click(function(){
		$("div#logModal").hide(50, function(){
			$("div#regModal").fadeIn();
		});
	})

	$(".close").click(function() {
	  $(".modal").hide(200);
	})

/*	$(window).click(function(event) {
	  if (event.target == $(".modal")) {
	   $(".modal").hide();
	  }
	});*/
})


function registration(){	//preuzeti unete vrednosti iz input-a u promenljive, pretvoriti u json objekat, poslati ajax poziv i obraditi odgovor
	let username = $("#username").val();
	let name = $("#name").val();
	let lastName = $("#lastName").val();
	let sex = $("#sex").val();
	let password = $("#password").val();

	
	var jsonRegistration = JSON.stringify({
		"username":username,
		"name":name,
		"lastName":lastName,
		"male":sex,
		"password":password,
		"role":"Guest"
	});

	//console.log(jsonRegistration);
	
	$.ajax({
		method:"POST",
		url:"../TuristickaAgencija/rest/users/registration",
		contentType:"application/json",
		data:jsonRegistration,
		datatype:"text"
	}).done(function(data){
		console.log(data);
	});
	
	
}
