//window.onload = function(event){
function loadContent(data){	
	$.ajax({
		method: "GET",
		url: "../TuristickaAgencija/rest/apartments/getById",
		contentType: "text;charset=utf-8",
		data: {
			id: window.location.search.substring(4)
		},
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