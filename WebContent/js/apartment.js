var logedUser;

function loadContent(user){
	logedUser = user;
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
		$("#price").append(data.price);
		
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
		
		if(user.role == "Host" || user.role == "Administrator"){
			$('#btnEdit').show();
			$('#btnDelete').show();
		}
		
		if(user.role == "Guest"){
			$("#reservationBtn").show();
			
			for(d of data.freeDates){
				content = d;
				content += ", ";
				$("#freeDates").append(content);
			}
			/*var enableDatesArray=[];  
			var sortDatesArry = [];   
				   for (var i = 0; i < data.freeDates.length; i++) {  
						 var dt = data.freeDates[i].date.replace('-', '/').replace('-', '/');  
						 var dd, mm, yyy;  
						 if (parseInt(dt.split('/')[0]) <= 9 || parseInt(dt.split('/')[1]) <= 9) {  
								   dd = parseInt(dt.split('/')[0]);  
								  mm = parseInt(dt.split('/')[1]);  
								  yyy = dt.split('/')[2];  
								 enableDatesArray.push(dd + '/' + mm + '/' + yyy);  
								 sortDatesArry.push(new Date(yyy + '/' + dt.split('/')[1] + '/' + dt.split('/')[0]));  
							}  
							else {  
							 enableDatesArray.push(dt);  
							 sortDatesArry.push(new Date(dt.split('/')[2] + '/' + dt.split('/')[1] + '/' + dt.split('/')[0] + '/'));  
					   }  
			 }  
				   var maxDt = new Date(Math.max.apply(null, sortDatesArry));  
				   var minDt = new Date(Math.min.apply(null, sortDatesArry));  
			  
				   $('#doj').datepicker({  
						  format: "dd/mm/yyyy",  
						  autoclose: true,  
						  startDate: minDt,  
						  endDate: maxDt,  
						  beforeShowDay: function (date) {  
							 var dt_ddmmyyyy = date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear();  
							 return (enableDatesArray.indexOf(dt_ddmmyyyy) != -1);  
						  }  
					  });*/  
		}
	});
}

function viewEditApartment(){
	window.location.assign(window.location.origin += "/TuristickaAgencija/editApartment.html?id=" + $("#apartId").text());
}

function deleteApartment(){	
		$.ajax({
			method: "PUT",
			url: "../TuristickaAgencija/rest/apartments/delete?id=" + $("#apartId").text(),
			datatype: "text"
		}).done(function(data){
			if(data != null){
				alert("Apartman uspesno obrisan!");
				openIndex();
			}
		})
}


$(document).ready(function(){
	$("#reservationBtn").click(function(){
		$("#reservationBox").slideToggle();
	})
	
	$("#nights").change(function(){
		$("#totalPrice").html(Number($("#price").text()) * this.value);
	});
	
	$("#reserve").click(function(){
		var jsonReservation = JSON.stringify({
			"apartment":
				{
					"id": $("#apartId").text(),
				},
			"beginDate":$("#beginDate").val(),
			"nights": Number($("#nights").val()),
			"totalPrice": parseFloat($("#totalPrice").text()),
			"message": $("#message").val(),
			"guest":
				{
					"username": logedUser.username
				},
			"status": "created"
		});
		
		$.ajax({
			method: "POST",
			url: "../TuristickaAgencija/rest/reservations/add",
			contentType: "application/json",
			data: jsonReservation,
			datatype: "text"
		}).done(function(data){
			alert(data);
			if(data == "Uspesno kreirana rezervacija!")
				window.location.assign(window.location.origin + "/TuristickaAgencija/reservations.html");
		});
	})

});