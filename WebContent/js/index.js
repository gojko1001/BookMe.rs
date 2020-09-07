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
	let controlPassword = $("#controlPassword").val();
	
	// PROVERA POLJA
	let allTds = $('td');
	if(username == ''){
		allTds[2].append("Morate uneti Vaše korisničko ime.");
		$(allTds[2]).css("color", "red")
		$(allTds[2]).css("font-size", "10px")
	}
	if(name == ''){
		allTds[5].append("Morate uneti Vaše ime.");
		$(allTds[5]).css("color", "red")
		$(allTds[5]).css("font-size", "10px")
	}
	if(lastName == ''){
		allTds[8].append("Morate uneti Vaše prezime.");
		$(allTds[8]).css("color", "red")
		$(allTds[8]).css("font-size", "10px")
	}
	if(password == ''){
		allTds[13].append("Morate uneti Vašu jedinstvenu lozinku.");
		$(allTds[13]).css("color", "red")
		$(allTds[13]).css("font-size", "10px")
	}
	if(controlPassword == ''){
		allTds[16].append("Morate ponovo uneti Vašu jedinstvenu lozinku.");
		$(allTds[16]).css("color", "red")
		$(allTds[16]).css("font-size", "10px")
	}

	
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
		//console.log(data);
		alert(data);
		window.location.href="http://localhost:8080/TuristickaAgencija/index.html";
	});
}

function login(){	
	let username = $("#username").val();
	let password = $("#password").val();

	
	var jsonRegistration = JSON.stringify({
		"username":username,
		"password":password,
	});

	//console.log(jsonRegistration);
	
	$.ajax({
		method:"POST",
		url:"../TuristickaAgencija/rest/users/login",
		contentType:"application/json",
		data:jsonRegistration,			
		datatype:"text"
	}).done(function(data){
		console.log(data);
	});
}

