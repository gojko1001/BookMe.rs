function loadContent(user){
	console.log(user);
	//$("#username").val(user.username);
	
	$.ajax({
		method: "GET",
		url: "../TuristickaAgencija/rest/apartments/getById" + window.location.search,
		datatype: "application/json",
	}).done(function(dataAp){
		$.ajax({
			method:"GET",
			url:"../TuristickaAgencija/rest/amenities/all",
			datatype:"application/json"
		}).done(function(data){
			console.log(data);
			viewApartment(dataAp);
			viewAllAmenities(data, dataAp);
		});
	});
	
	
	
	
}

$(document).ready(function(){
	$("button#save").click(function(){
		let id = $("#idApartment").val();
		let type = $('#type').val();
		let username = $('#username').val();
		let numberOfRooms = $('#numberOfRooms').val();
		let numberOfGuests = $('#numberOfGuests').val();
		let country = $('#country').val();
		let place = $('#place').val();
		let postalCode = $('#postalCode').val();
		let street = $('#street').val();
		let number = $('#number').val();
		let latitude = $('#latitude').val();
		let longitude = $('#longitude').val();
		let myFile = [];
		let price = $('#price').val();
		let checkInTime = $('#checkInTime').val();
		let checkOutTime = $('#checkOutTime').val();
		let active = false;		
		let amenities = [];	
		
        $.each($("input[name='checkboxes']:checked"), function(){
        	console.log($(this).attr('id'));
        	let amenity = {
        		"id":$(this).attr('id'),
        		"name":$(this).val(),
        		"deleted":false
        	};
            amenities.push(amenity);
        });
        
		var jsonAdd = JSON.stringify({
			"id":id,
			"type":type,
			"numberOfRooms":numberOfRooms,
			"numberOfGuests":numberOfGuests,
			"location":{
				"latitude":latitude,
				"longitude":longitude,
				"address":{
					"country":country,
					"place":place,
					"postalCode":postalCode,
					"street":street,
					"number":number
				}
			},
			"datesForRent":[],				//TODO
			"freeDates":[],					//TODO
			"host":{
				"username":username
			},			
			"comments":[],					//TODO
			"photos":myFile,
			"price":price,
			"checkInTime":checkInTime,
			"checkOutTime":checkOutTime,
			"active":active,
			"amenities":amenities,
			"reservations":[]				//TODO
		});
			
		console.log(jsonAdd);
			$.ajax({
				method: "PUT",
				url: "../TuristickaAgencija/rest/apartments/update",
				contentType: "application/json",
				data: jsonAdd,
				datatype: "text"
			}).done(function(data){
				alert(data);
				if(alert == "Apartman je izmenjen."){
					window.location.assign(window.location.origin + "/TuristickaAgencija/");
				}
				
			}).error(function(data){
				console.log(data);
			})
	})
	
	$("button#btnClose").click(function(){
		window.history.back();
	})
})


function viewAllAmenities(data, dataAp){
	var i;
	for(i=0; i<data.length; i++){
		content = '<input type="checkbox" name="checkboxes" id="';
		content += data[i].id;
		content += '" value="';
		content += data[i].name;
		content += '"><label for="';
		content += data[i].id;
		content += '">&nbsp;'
		content += data[i].name;
		content += '</label><br>';
		
		for(j=0; j<dataAp.amenities.length; j++){
			if(data[i].id == dataAp.amenities[j].id){
				// TODO:
				//document.getElementById(" + data[i].id + ").checked = true;
			}
		}
		
		$("#viewAmenity").append(content);
	}
	
	
	
	
}

function viewApartment(data){
	$("#idApartment").val(data.id);
	$('#type').val(data.type);
	$('#username').val(data.host.username);
	$('#numberOfRooms').val(data.numberOfRooms);
	$('#numberOfGuests').val(data.numberOfGuests);
	$('#country').val(data.location.address.country);
	$('#place').val(data.location.address.place);
	$('#postalCode').val(data.location.address.postalCode);
	$('#street').val(data.location.address.street);
	$('#number').val(data.location.address.number);
	$('#latitude').val(data.location.latitude);
	$('#longitude').val(data.location.longitude);
	$('#price').val(data.price);
	$('#checkInTime').val(data.checkInTime);
	$('#checkOutTime').val(data.checkOutTime);
	$('#active').val(data.checkOutTime);	
	
	if(data.active == true){
		document.getElementById("active").checked = true;
	}else{
		document.getElementById("inActive").checked = true;
	}
}

