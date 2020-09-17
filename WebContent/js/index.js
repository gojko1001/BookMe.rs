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
		console.log(data);
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

