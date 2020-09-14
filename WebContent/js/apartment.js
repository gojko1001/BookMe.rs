function loadContent(user){	
	$.ajax({
		method: "GET",
		url: "../TuristickaAgencija/rest/apartments/getById" + window.location.search,
		datatype: "application/json",
	}).done(function(data){
		$("#apartId").html(data.id);
		$("#loc").append(data.location.address.street + " " + data.location.address.number + 
				", " + data.location.address.postalCode + " " + data.location.address.place + 
				", " + data.location.address.country);
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
		$("#price").append(data.price + " RSD");
		
		let x;
		for(x of data.amenities){
			$("#amenities").append(x.name + "\n");
		}
		
		for(x of data.photos){
			content = '<img class="gallery" ';
			content += 'src="Resources/ApartmentPhotos/';
			content += data.id;
			content += x;
			content += '">'
			$("#pic").append(content);
		}
	});
}