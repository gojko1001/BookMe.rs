window.onload = function(event){
	$.ajax({
		method: "GET",
		url: "../TuristickaAgencija/rest/apartments/getById" + window.location.search,
		contentType: "text",
		dataType: "application/json",
	}).done(function(data){
		$("#name").append(data.id);
		$("#loc").append(data.address.street + " " + data.address.number + ", " + data.address.postalCode
							+ " " + data.address.place + ", " + data.address.country);
		$("#roomNum").append(data.numberOfRooms);
		$("#guestNum").append(data.numberOfGuests);
		if(data.type == "wholeApartment"){
			$("#type").append('Ceo apartman');
		}else{
			$("#type").append('Soba');
		}
		$("#host").append(data.host.username);
		$("#checkIn").append(data.checkInTime);
		$("#checkOut").append(data.checkOutTime);
		$("#price").append(data.price);
	})
}