var logedUser;

function loadContent(user){
	console.log(user);
	logedUser = user;
	$.ajax({
		method:"GET",
		url:"../TuristickaAgencija/rest/reservations/all",
		datatype:"application/json"
	}).done(function(data){
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
		content += '<tr><td><button class="delete" style="display:none"/></td>';
		content += '<tr><td><button class="accept" style="display:none"/></td>';
		content += '</table></div>';
		content += '</div></div>';
		
		$("#viewReservations").append(content);
		if(logedUser.role == "Guest"){
			$(".delete").val("Odustani");
			$(".delete").show();
		} 
		if(logedUser.role == "Host"){
			$(".delete").val("Odbij");
			$(".delete").show();
			$(".accept").val("Prihvati");
			$(".accept").show();
		}
	
	}	
}


