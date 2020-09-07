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

	$("#filterBtn").click(function(){
			$("#filterTable").slideToggle();
	})
	
	$("#filterSearch").click(function(){
		let dateFrom = $("#dateFrom").val();
		let dateto = $("#dateTo").val();
		let country = $("#country").val();
		let city = $("#city").val();
		let priceFrom = $("#priceFrom").val();
		let priceTo = $("#priceTo").val();
		let roomFrom = $("#roomFrom").val();
		let roomTo = $("#roomTo").val();
		let spotNum = $("#spotNum").val();
	})
})

// TODO 6: slucaj nevalidnog profila
function openLogin() {				// otvara se odmah posle registracije
	$(".modal").hide(200);
	$("div#logModal").slideDown("fast");
}

function registration(){	// preuzeti unete vrednosti iz input-a u promenljive, pretvoriti u json objekat, poslati ajax poziv i obraditi odgovor
	let username = $("#username").val();
	let name = $("#name").val();
	let lastName = $("#lastName").val();
	let sex = $("#sex").val();
	let password = $("#password").val();
	let controlPassword = $("#controlPassword").val();
	
	// VALIDACIJA POLJA

	if(!username){
		$("#username").css("border-color", "red");
		$("#invalidUser").css("font-size", "10px");
	}else{
		$("#username").css("border-color", "grey");
		$("#invalidUser").css("font-size", "0px");
	}
	if(!name){
		$("#name").css("border-color", "red");
		$("#invalidName").css("font-size", "10px");
	}else{
		$("#name").css("border-color", "grey");
		$("#invalidName").css("font-size", "0px");
	}
	if(!lastName){
		$("#lastName").css("border-color", "red");
		$("#invalidLastName").css("font-size", "10px");
	}else{
		$("#lastName").css("border-color", "grey");
		$("#invalidLastName").css("font-size", "0px");
	}
	if(password.length < 8){
		$("#invalidPass").css("font-size", "10px");
	}else{
		$("#invalidPass").css("font-size", "0px");
	}
	if(controlPassword != password){
		$("#invalidControlPass").css("font-size", "10px");
	}else{
		$("#invalidControlPass").css("font-size", "0px");
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
		alert(data);
		if(data == "Uspešno ste se registrovali."){
			openLogin();
		}
		//window.location.href="http://localhost:8080/TuristickaAgencija/";
	});
}

function openHome() {				
	window.location.assign(window.location.origin += "/TuristickaAgencija/home.html");
}



function login(){	
	let username1 = $("#username1").val();
	let password1 = $("#password1").val();
	
	// TODO: PROVERA POLJA -- nakon svake potvrde se ponovi poruka...
	
	if(!username1){
		$("#emptyUser").css("font-size", "10px")
	}else{
		$("#emptyUser").css("font-size", "0px")
	}
	if(!password1){
		$("#emptyPass").css("font-size", "10px")
	}
	

	
	var jsonRegistration = JSON.stringify({
		"username":username1,
		"password":password1
	});
	
	$.ajax({
		method:"POST",
		url:"../TuristickaAgencija/rest/users/login",
		contentType:"application/json",
		data:jsonRegistration,
		datatype:"text"
	}).done(function(data){
		alert(data);
		if(data == "Uspešno ste se ulogovali."){
			openHome();
		}
		//window.location.href="http://localhost:8080/TuristickaAgencija/";
	});
}


