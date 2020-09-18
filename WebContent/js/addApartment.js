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
		let dateFrom = $("#dateFrom").val();
		let dateTo = $("#dateTo").val();
		let valid = true;
		
		// VALIDACIJA PODATAKA
		if(!numberOfRooms){
			$("#numberOfRooms").css("border-color", "red");
			$("#invalidNumberOfRooms").css("font-size", "10px");
			valid = false;
		}else{
			$("#numberOfRooms").css("border-color", "grey");
			$("#invalidNumberOfRooms").css("font-size", "0px");
		}
		if(!numberOfGuests){
			$("#numberOfGuests").css("border-color", "red");
			$("#invalidNumberOfGuests").css("font-size", "10px");
			valid = false;
		}else{
			$("#numberOfGuests").css("border-color", "grey");
			$("#invalidNumberOfGuests").css("font-size", "0px");
		}
		if(!country){
			$("#country").css("border-color", "red");
			$("#invalidCountry").css("font-size", "10px");
			valid = false;
		}else{
			$("#country").css("border-color", "grey");
			$("#invalidCountry").css("font-size", "0px");
		}
		if(!place){
			$("#place").css("border-color", "red");
			$("#invalidPlace").css("font-size", "10px");
			valid = false;
		}else{
			$("#place").css("border-color", "grey");
			$("#invalidPlace").css("font-size", "0px");
		}
		if(!postalCode){
			$("#postalCode").css("border-color", "red");
			$("#invalidPostalCode").css("font-size", "10px");
			valid = false;
		}else{
			$("#postalCode").css("border-color", "grey");
			$("#invalidPostalCode").css("font-size", "0px");
		}
		if(!street){
			$("#street").css("border-color", "red");
			$("#invalidStreet").css("font-size", "10px");
			valid = false;
		}else{
			$("#street").css("border-color", "grey");
			$("#invalidStreet").css("font-size", "0px");
		}
		if(!number){
			$("#number").css("border-color", "red");
			$("#invalidNumber").css("font-size", "10px");
			valid = false;
		}else{
			$("#number").css("border-color", "grey");
			$("#invalidNumber").css("font-size", "0px");
		}
		if(!latitude){
			$("#latitude").css("border-color", "red");
			$("#invalidLatitude").css("font-size", "10px");
			valid = false;
		}else{
			$("#latitude").css("border-color", "grey");
			$("#invalidLatitude").css("font-size", "0px");
		}
		if(!longitude){
			$("#longitude").css("border-color", "red");
			$("#invalidLongitude").css("font-size", "10px");
			valid = false;
		}else{
			$("#longitude").css("border-color", "grey");
			$("#invalidLongitude").css("font-size", "0px");
		}
		if(!price){
			$("#price").css("border-color", "red");
			$("#invalidPrice").css("font-size", "10px");
			valid = false;
		}else{
			$("#price").css("border-color", "grey");
			$("#invalidPrice").css("font-size", "0px");
		}
		if(!checkInTime){
			$("#checkInTime").css("border-color", "red");
			$("#invalidCheckInTime").css("font-size", "10px");
			valid = false;
		}else{
			$("#checkInTime").css("border-color", "grey");
			$("#invalidCheckInTime").css("font-size", "0px");
		}
		if(!checkOutTime){
			$("#checkOutTime").css("border-color", "red");
			$("#invalidCheckOutTime").css("font-size", "10px");
			valid = false;
		}else{
			$("#checkOutTime").css("border-color", "grey");
			$("#invalidCheckOutTime").css("font-size", "0px");
		}
		if(new Date(dateFrom) >= new Date(dateTo) || !dateFrom || !dateTo){
			$("#invalidDates").css("font-size", "10px");
			valid = false;
		}else{
			$("#invalidDates").css("font-size", "0px");
		}
		
		$.each($("input[name='checkboxes']:checked"), function(){
        	console.log($(this).attr('id'));
        	let amenity = {
        		"id":$(this).attr('id'),
        		"name":$(this).val(),
        		"deleted":false
        	};
            amenities.push(amenity);
        });
		
		if(valid == true){
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
				"datesForRent":[dateFrom, dateTo],		//U backendu generisem
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
				"reservations":[],
				"view":true
			});
			
			console.log(jsonAdd);
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
			}).error(function(data){
				console.log(data);
			})
		}

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
		content += '" value="';
		content += data[i].name;
		content += '"><label for="';
		content += data[i].id;
		content += '">&nbsp;'
		content += data[i].name;
		content += '</label><br>';
		
		$("#viewAmenity").append(content);
	}

}

