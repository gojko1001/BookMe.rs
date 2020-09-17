function loadContent(user){
	console.log(user);
	
}

function back(){
	window.history.back();
}


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
		"role":"Host"
	});
		
	$.ajax({
		method:"POST",
		url:"../TuristickaAgencija/rest/users/registrationHost",
		contentType:"application/json",
		data:jsonRegistration,
		datatype:"text"
	}).done(function(data){
		//alert(data);
		if(data == "Uspešno ste se registrovali."){
			alert("Korisnik je uspešno registrovan.");
			window.location.assign(window.location.origin + "/TuristickaAgencija/");
		}else{
			alert(data);
		}
	});
		
		}
	}
}
