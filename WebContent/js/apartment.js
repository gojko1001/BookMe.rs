function loadContent(user){	
	$.ajax({
		method: "GET",
		url: "../TuristickaAgencija/rest/apartments/getById" + window.location.search,
		datatype: "application/json",
	}).done(function(data){
		console.log(data);
		
		$("#apartId").html(data.id);
		$("#loc").append(data.location.address.street + " " + data.location.address.number + 
				", " + data.location.address.postalCode + " " + data.location.address.place + 
				", " + data.location.address.country);
		$("#roomNum").append(data.numberOfRooms);
		$("#guestNum").append(data.numberOfGuests);
		if(data.type == "wholeApartment"){
			$("#type").append('Ceo apartman');
		}else{
			$("#type").append('Jedna soba');
		}
		$("#host").append(data.host.username);
		$("#checkIn").append(data.checkInTime);
		$("#checkOut").append(data.checkOutTime);
		$("#price").append(data.price + " RSD");
		
		let x;
		for(x of data.amenities){
			//if(!x.deleted)
				$("#amenities").append(x.name + ", ");
		}
		
		for(x of data.photos){
			content = '<img class="gallery" ';
			content += 'src="Resources/ApartmentPhotos/';
			content += data.id;
			content += x;
			content += '">'
			$("#pic").append(content);
		}
		
		var c;
		var contentC = '<table border="2" style="margin:25px; margin-left: auto; margin-right: auto;">';
		contentC += '<tr> <th>Gost</th><th>Komentar</th> <th>Ocena</th></tr>';
		for(c=0; c<data.commentsDTO.length; c++){
			contentC += '<tr><td>';
			contentC += data.commentsDTO[c].guestUsername;
			contentC += '</td><td>';
			contentC += data.commentsDTO[c].text;
			contentC += '</td><td>';
			contentC += data.commentsDTO[c].rate;
			contentC += '</td></tr>'
		}
		contentC += '</table>';
		$('#comments').append(contentC);
	});
}