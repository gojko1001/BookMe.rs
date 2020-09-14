$(document).ready(function(){
// ModalBox Registration/Login
	$("#aLogin").click(function(event) {
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
});

function registration(){	// preuzeti unete vrednosti iz input-a u promenljive, pretvoriti u json objekat, poslati ajax poziv i obraditi odgovor
	let username = $("#username").val();
	let name = $("#name").val();
	let lastName = $("#lastName").val();
	let sex = $("#sex").val();
	let password = $("#password").val();
	let controlPassword = $("#controlPassword").val();
	let valid = true;
	
	// VALIDACIJA POLJA

	if(!username){
		$("#username").css("border-color", "red");
		$("#invalidUser").css("font-size", "10px");
		valid = false;
	}else{
		$("#username").css("border-color", "grey");
		$("#invalidUser").css("font-size", "0px");
	}
	if(!name){
		$("#name").css("border-color", "red");
		$("#invalidName").css("font-size", "10px");
		valid = false;
	}else{
		$("#name").css("border-color", "grey");
		$("#invalidName").css("font-size", "0px");
	}
	if(!lastName){
		$("#lastName").css("border-color", "red");
		$("#invalidLastName").css("font-size", "10px");
		valid = false;
	}else{
		$("#lastName").css("border-color", "grey");
		$("#invalidLastName").css("font-size", "0px");
	}
	if(password.length < 8){
		$("#password").css("border-color", "red");
		$("#invalidPass").css("font-size", "10px");
		valid = false;
	}else{
		$("#invalidPass").css("font-size", "0px");
	}
	if(controlPassword != password){
		$("#controlPassword").css("border-color", "red");
		$("#invalidControlPass").css("font-size", "10px");
		valid = false;
	}else{
		$("#invalidControlPass").css("font-size", "0px");
		
	if(valid == true){
		var jsonRegistration = JSON.stringify({
		"username":username,
		"name":name,
		"lastName":lastName,
		"male":sex,
		"password":password,
		"role":"Guest"
	});
		
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
	});
		
		}
	}
}

function login(){	
	let username1 = $("#username1").val();
	let password1 = $("#password1").val();
	
	if(!username1){
		$("#emptyUser").css("font-size", "10px");
	}else{
		$("#emptyUser").css("font-size", "0px");
	}
	if(!password1){
		$("#emptyPass").css("font-size", "10px");
	}else{
		$("#emptyPass").css("font-size", "0px");
	}
	
	var jsonLogin = JSON.stringify({
		"username":username1,
		"password":password1
	});
	
	$.ajax({
		method:"POST",
		url:"../TuristickaAgencija/rest/users/login",
		contentType:"application/json",
		data:jsonLogin,
		datatype:"text"
	}).done(function(data){
		alert(data);
		if(data == "Uspešno ste se ulogovali."){
			window.location.reload();
		}
	});
}

function openLogin() {				// otvara se odmah posle registracije
	$(".modal").hide(200);
	$("div#logModal").slideDown("fast");
}