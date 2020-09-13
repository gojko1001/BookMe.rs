window.onload = function(event){
	$.ajax({
		method:"GET",
		url:"../TuristickaAgencija/rest/apartments/all",
		datatype:"application/json"
	}).done(function(data){
		showApartments(data);
	});
	
	$.ajax({
			method: "GET",
			url: "../TuristickaAgencija/rest/users/getUser",
			datatype: "application/json"
	}).done(function(data){
		if(!data){
			$(".logedOut").show();
		}else{
			$("#btnDropDown").html(data.username);
			$(".logedIn").show();
		}
	})
}

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

// Filteri
	$("#filterBtn").click(function(){
		$("#filterTable").slideToggle();
		$(".centerSearch").toggle();
	})
	
	$("#filterSearch").click(function(){
		var jsonFilter = {
			"startDate": $("#startDate").val(),
			"dueDate": $("#dueDate").val(),
			"country": $("#country").val(),
			"city": $("#city").val(),
			"priceFrom": $("#priceFrom").val(),
			"priceTo": $("#priceTo").val(),
			"roomFrom": $("#roomFrom").val(),
			"roomTo": $("#roomTo").val(),
			"spotNum": $("#spotNum").val()
		}
		
		$.ajax({
			method: "POST",
			url: "../TuristickaAgencija/rest/apartments/filter",
			data: jsonFilter,
			contenttype: "application/json",
			dataType: "application/json",
		}).done(function(data){
			$("#listOfApartments").html("");
			showApartments(data);
		});
	})
});

function showApartments(data){
	var i;
	
		for(i=0; i<data.length; i++){
			if(data[i].active == true){
				content = '<div class="card" onclick="viewApartment(this)" id="';
				content += data[i].id;
				content += '">';
				content += '<img class="card-img-top" src="Resources/ApartmentPhotos/';
				content += data[i].id;
				content += data[i].photos[0];
				content += '" alt="SLIKA APARTMANA">';
				content += '<div class="card-body">';
				content += '<div class="data">';
				content += '<table style="margin:10px">';
				content += '<tr><td float="right">Naziv apartmana:</td><td>';
				content += data[i].id;
				content += '</td></tr>';
				content +='<tr><td>Domacin:</td><td>';
				content += data[i].host.username;
				content += '</td></tr>';
				content += '</table></div>';
				content += '</div></div>';
			}
			$("#listOfApartments").append(content);
		}
		
		
}

function viewApartment(event){
	window.location.assign(window.location.origin += "/TuristickaAgencija/apartment.html?id=" + event.id);
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
			openHome();
		}
	});
}

function openHome() {				
	window.location.assign(window.location.origin += "/TuristickaAgencija/");
}

function openLogin() {				// otvara se odmah posle registracije
	$(".modal").hide(200);
	$("div#logModal").slideDown("fast");
}



/*$(document).ready(function(){
	$.ajax({
		method:"GET",
		url:"../TuristickaAgencija/rest/apartments/all",
		datatype:"application/json"
	}).done(function(data){
		var i;
		
		var content = '<table border="2" style="margin:25px; margin-left: auto; margin-right: auto;">';
		content += '<tr> <th>Apartman</th> <th>Tip</th> <th>Broj soba</th> <th>Broj gostiju</th> <th>Lokacija</th> <th>Domaćin</th> <th>Komentari</th> <th>Slike</th> <th>Cena (noć)</th> <th>Vreme za prijavu</th> <th>Vreme za odjavu</th> <th>Sadržaj apartmana</th></tr>';
		for(i=0; i<data.length; i++){
			if(data[i].active == true){
				content += '<tr><td>';
				content += data[i].id;
				content += '</td><td>';
				if(data[i].type == "wholeApartment"){
					content += 'ceo apartman';
				}else{
					content += 'soba';
				}
				content += '</td><td>';
				content += data[i].numberOfRooms;
				content += '</td><td>';
				content += data[i].numberOfGuests;
				content += '</td><td>';
				content += data[i].location.address.country;
				content += ', ';
				content += data[i].location.address.place;
				content += ', ';
				content += data[i].location.address.postalCode;
				content += '<br>';
				content += data[i].location.address.street;
				content += ', ';
				content += data[i].location.address.number;
				content += '<br>';
				content += data[i].location.latitude;
				content += ', ';
				content += data[i].location.longitude;
				content += '</td><td>';
				content += data[i].host.username;
				content += '</td><td>';
				
				var c;
				for(c=0; c<data[i].commentsDTO.length; c++){
					content += data[i].commentsDTO[c].guestUsername;
					content += ':&nbsp';
					content += data[i].commentsDTO[c].text;
					content += '<br>ocena:&nbsp';
					content += data[i].commentsDTO[c].rate;
				}
				content += '<br></td><td>';
		
				var p;
				for(p=0; p<data[i].photos.length; p++){
					content += '<img src=">';
					content += data[i].photos[p];
					content += '" alt="slika ';
					content += data[i].id;
					content += '"><br>';
				}

				content += '</td><td>';
				content += data[i].price;
				content += 'RSD</td><td>';
				content += data[i].checkInTime;
				content += 'h</td><td>';
				content += data[i].checkOutTime;
				content += 'h</td><td>';
				
				var a;
				let myId = data[i].id;
				for(a=0; a<data[i].amenities.length; a++){
					content += data[i].amenities[a].name;
					content += ', &nbsp';
				}

				content += '</td>';
				//content += "<td><button id=\"" +myId + "\" class=\"myButtonClass\"> Rezervisi </button></td>";
				content += '</tr>';
			}		
		}
		content += '</table>';
		
		$("#listOfApartments").append(content);

		console.log(data);
	});
});*/



/*content += '<tr><td>Tip: &nbsp';
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
			content += '</td></tr>';*/


			/*var content = '';
			content = '<div class="card" style="width: 15rem; height: 15rem;">';
			content += '<img class="card-img-top" src="..." alt="SLIKA APARTMANA">';
			content += '<div class="card-body">';
			content += '<div class="data">';
			content += '<table style="margin:25px">';
			content += '<tr><td float="right">Naziv apartmana:</td><td>';
			content += data[i].id;
			content += '</td></tr>';
			content +='<tr><td>Domaćin:</td><td>';
			content += data[i].hostUsername;
			content += '</td></tr>';
			content += '</table></div>';
			content += '<button type="button" class="i">Pogledaj</button>';
			content += '</div></div>';
			var contentModal = '';
			contentModal = '<table>';
			contentModal += '<tr><td>Broj soba:</td><td>';
			contentModal += data[i].numberOfRooms;
			contentModal += '</td></tr>';
			contentModal += '</table>';

			$("#listOfApartments").append(content);
			$('#moreAboutApartment').append(contentModal);
			
			$(".i").click(function(){
				$("div#apartmentModal").slideDown("fast");
			})*/


