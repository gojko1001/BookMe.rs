var logedUser;
var reservations = [];

function loadContent(user){
	console.log(user);
	logedUser = user;
	$.ajax({
		method:"GET",
		url:"../TuristickaAgencija/rest/reservations/all",
		datatype:"application/json"
	}).done(function(data){
		reservations = data;
		viewAllReservations(data);
		console.log(data);
	});	
}

function viewAllReservations(data){
	for(i=0; i<data.length; i++){
		content = '<div class="card" id="';
		content += data[i].apartmentId;
		content += '">';
		content += '<div class="card-body">';
		content += '<div class="data">';
		content += '<table style="margin:10px">';
		content += '<tr><td float="right">Id rezervacije:</td><td>';
		content += data[i].apartmentId;
		content += '</td></tr>';
		content +='<tr><td>Početni datum:</td><td>';
		content += data[i].beginDate;
		content += '</td></tr>';
		content +='<tr><td>Boj noćenja:</td><td>';
		content += data[i].nights;
		content += '</td></tr>';
		content +='<tr><td>Cena:</td><td>';
		content += data[i].totalPrice;
		content += ' RSD</td></tr>';
		content +='<tr><td>Poruka:</td><td>';
		content += data[i].message;
		content += '</td></tr>';
		content +='<tr><td>Gost:</td><td>';
		content += data[i].guestUsername;
		content += '</td></tr>';
		content +='<tr><td>Status:</td><td>';
		if(data[i].status == "created"){
			content += 'KREIRANA';
		}else if(data[i].status == "denied"){
			content += 'ODBIJEN';
		}else if(data[i].status == "withdrawal"){
			content += 'ODUSTANAK';
		}else if(data[i].status == "accepted"){
			content += 'PRIHVAĆENA';
		}else{
			content += 'ZAVRŠENA';
		}
		content += '</td></tr>';
		content += '<tr><td><button class="cancel btn btn-primary" id="0';
		content += i;
		content += '" onclick="updateStatus(this, &quot;withdrawal&quot;)">Otkazi</button></td>';
		content += '<td><button class="end btn btn-primary" id="1';
		content += i;
		content += '" onclick="updateStatus(this, &quot;ended&quot;)">Zavrsi</button></td></tr>';
		content += '<tr><td><button class="deny btn btn-primary" style="background-color:red" id="2';
		content += i;
		content += '" onclick="updateStatus(this, &quot;denied&quot;)">Odbij</button></td>';
		content += '<td><button class="accept btn btn-primary" style="background-color:green" id="3';
		content += i;
		content += '" onclick="updateStatus(this, &quot;accepted&quot;)">Prihvati</button></td></tr>';
		content += '</table></div>';
		content += '</div></div>';
		
		$("#viewReservations").append(content);
		if(logedUser.role == "Guest"){
			if(data[i].status == "created" || data[i].status == "accepted")
				$("#0" + i).show();
		} 
		if(logedUser.role == "Host" && data[i].status != "ended"){
			if(data[i].status == "created"){
				$("#2" + i).show();
				$("#3" + i).show();
			}
			if(data[i].status == "accepted"){
				$("#2" + i).show();
			}
			var today = new Date();
			var dd = String(today.getDate()).padStart(2, '0');
			var mm = String(today.getMonth() + 1).padStart(2, '0');
			var yyyy = today.getFullYear();
			
			dateArray = data[i].beginDate.split("-");
			if(yyyy > dateArray[0]){
				$("#1" + i).show();
				$("#2" + i).hide();
			}else if(yyyy == dateArray[0]) 
				if(mm > dateArray[1]){
					$("#1" + i).show();
					$("#2" + i).hide();
				}else if(mm == dateArray[1])
					if(dd > dateArray[2]){
						$("#1" + i).show();
						$("#2" + i).hide();
					}
		}
	
	}	
}

function updateStatus(event, newStatus){
	let r = reservations[event.id.charAt(1)];
	
	var jsonReservation = JSON.stringify({
		"apartment":
			{
				"id":r.apartmentId
			},
		"beginDate":r.beginDate,
		"nights":r.nights,
		"totalPrice":r.totalPrice,
		"message":r.message,
		"guest":
			{
				"username":r.guestUsername
			},
		"status":r.status
	});
	
	$.ajax({
		method: "POST",
		url: "../TuristickaAgencija/rest/reservations/updateStatus/" + newStatus,
		contentType: "application/json",
		data: jsonReservation,
		datatype: "text"
	}).done(function(data){
		alert(data);
		window.location.reload();
	});
}


