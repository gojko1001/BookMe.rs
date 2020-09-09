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
	let notRed = false;
	
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
		$("#password").css("border-color", "red");
		$("#invalidPass").css("font-size", "10px");
	}else{
		$("#invalidPass").css("font-size", "0px");
		notRed = true;
	}
	if(controlPassword != password){
		$("#controlPassword").css("border-color", "red");
		$("#invalidControlPass").css("font-size", "10px");
	}else{
		$("#invalidControlPass").css("font-size", "0px");
		
		if(notRed == true){
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

function openHome() {				
	window.location.assign(window.location.origin += "/TuristickaAgencija/home.html");
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
	});
}


$(document).ready(function(){
	$.ajax({
		method:"GET",
		url:"../TuristickaAgencija/rest/apartments/all",
		datatype:"application/json"
	}).done(function(data){
		var i;
		for(i=0; i<data.length; i++){
			var content = '<div class="data">';
			content += '<table style="margin:25px">';
			content += '<tr><td float="right">Naziv apartmana: &nbsp';
			content += data[i].id;
			content += '</td></tr>';
			content += '<tr><td>Tip: &nbsp';
			content += data[i].type;
			content += '</td></tr>';
			content += '<tr><td>Broj soba: &nbsp';
			content += data[i].numberOfRooms;
			content += '</td></tr>';
			content += '<tr><td>Broj gostiju: &nbsp';
			content += data[i].numberOfGuests;
			content += '</td></tr>';
			content += '<tr><td>Lokacija: &nbsp';
			content += data[i].location.address.country;
			content += ', ';
			content += data[i].location.address.place;
			content += ', ';
			content += data[i].location.address.postalCode;
			content += '<br>&nbsp;&nbsp;';
			content += data[i].location.address.street;
			content += ', ';
			content += data[i].location.address.number;
			content += '<br>&nbsp;&nbsp;';
			content += data[i].location.latitude;
			content += ', ';
			content += data[i].location.longitude;
			content += '</td></tr>';
			content +='<tr><td>Domaćin: &nbsp';
			content += data[i].hostUsername;
			content += '</td></tr>';
			content += '</table></div>';

			$("#listOfApartments").append(content);
		}
		console.log(data);
	});	
})

$(document).ready(function(){
	$("#no1").click(function(){
		window.location.assign(window.location.origin += "/TuristickaAgencija/profile.html");
	});	
})




