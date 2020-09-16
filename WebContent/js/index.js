function loadContent(user){
	if(window.location.search){
		openLogin();
	}
	
	$.ajax({
		method:"GET",
		url:"../TuristickaAgencija/rest/apartments/all",
		datatype:"application/json"
	}).done(function(data){
		showApartments(data);
	});	
}

//FILTERI
$(document).ready(function(){
	$("#filterBtn").click(function(){
		$("#filterTable").slideToggle();
		$(".quickSearch").toggle();
	})
	
	document.getElementById("all").checked = true;
	
	$("#filterSearch").click(function(){
		let priceFrom = Number($("#priceFrom").val());
		if(!priceFrom){
			priceFrom = -1;
		}
		let priceTo = Number($("#priceTo").val());
		if(!priceTo){
			priceTo = -1;
		}
		let roomFrom = Number($("#roomFrom").val());
		if(!roomFrom){
			roomFrom = -1;
		}
		let roomTo = Number($("#roomTo").val());
		if(!roomTo){
			roomTo = -1;
		}
		let spotNum = Number($("#spotNum").val());
		if(!spotNum){
			spotNum = -1;
		}
		
		var activity;
		if(document.getElementById("active").checked == true){
			activity = 1;
		}else if(document.getElementById("inActive").checked == true){
			activity = 2;
		}else{
			activity = 0;
		}
		
		var jsonFilter = JSON.stringify({
			"activity":activity,
			"startDate": $("#startDate").val(),
			"dueDate": $("#dueDate").val(),
			"country": $("#country").val(),
			"city": $("#city").val(),
			"priceFrom": priceFrom,
			"priceTo": priceTo,
			"roomFrom": roomFrom,
			"roomTo": roomTo,
			"spotNum": spotNum
		});
		
		$.ajax({
			method: "POST",
			url: "../TuristickaAgencija/rest/apartments/filter",
			data: jsonFilter,
			contentType: "application/json",
			datatype: "application/json",
		}).done(function(data){
			$("#listOfApartments").html("");
			showApartments(data);
		});
	})
});

function showApartments(data){
	var i;
		for(i=0; i<data.length; i++){
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
					content +='<tr><td>Domaćin:</td><td>';
					content += data[i].host.username;
					content += '</td></tr>';
					content +='<tr><td>Aktivnost:</td><td>';
					if(data[i].active == false){
						content += 'NEAKTIVAN';
					}else{
						content += 'AKTIVAN';
					}
					content += '</td></tr>';
					content += '</table></div>';
					content += '</div></div>';
					
					$("#listOfApartments").append(content);
		}	
}

function viewApartment(event){
	window.location.assign(window.location.origin += "/TuristickaAgencija/apartment.html?id=" + event.id);
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