function loadContent(user){
	console.log(user);
	$("#username").val(user.username);
	$.ajax({
		method:"GET",
		url:"../TuristickaAgencija/rest/amenities/all",
		datatype:"application/json"
	}).done(function(data){
		viewAllAmenities(data);
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
		amenities = [$("input:checkbox[name=checkboxes]:checked").val()];
		
		
        /*$.each($("input[name='checkboxes']:checked"), function(){            
            amenities.push($(this).val());
        });
        alert("Izabran sadrzaj je: " + amenities.join(", "));*/
		
		/*
		$("input:checkbox[name=type]:checked").each(function(){
		    amenities.push($(this).val());
		});*/
		//console.log(amenities);

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
			"datesForRent":[],
			"freeDates":[],
			"host":{
				"username":username
			},			
			"comments":[],
			"photos":myFile,
			"price":price,
			"checkInTime":checkInTime,
			"checkOutTime":checkOutTime,
			"active":active,
			"amenities":amenities,
			"reservations":[]
		});
			
			$.ajax({
				method: "POST",
				url: "../TuristickaAgencija/rest/apartments/add",
				contentType: "application/json",
				data: jsonAdd,
				datatype: "text"
			}).done(function(data){
				alert(data);
				if(data == "Apartman je dodat."){
					openApartments();
				}
			})
	})
	
	$("button#btnClose").click(function(){
		window.history.back();
	})
})

function openApartments(){
	window.location.assign(window.location.origin += "/TuristickaAgencija/");
}

function viewAllAmenities(data){
	var i;
	for(i=0; i<data.length; i++){
		content = '<input type="checkbox" name="checkboxes" id="';
		content += data[i].id;
		content += '"><label for="';
		content += data[i].id;
		content += '">&nbsp;'
		content += data[i].name;
		content += '</label><br>';
		
		$("#viewAmenity").append(content);
	}

}

