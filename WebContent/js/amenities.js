function loadContent(data){
	console.log(data);
	$.ajax({
		method:"GET",
		url:"../TuristickaAgencija/rest/amenities/all",
		datatype:"application/json"
	}).done(function(data){
		viewAllAmenities(data);
	});	
}

function viewAllAmenities(data){
	for(i=0; i<data.length; i++){
		content = '<div class="card" onclick="viewAmenity(this)" id="';
		content += data[i].id;
		content += '">';
		content += '<div class="card-body">';
		content += '<div class="data">';
		content += '<table style="margin:10px">';
		content += '<tr><td float="right">Id sadržaja:</td><td>';
		content += data[i].id;
		content += '</td></tr>';
		content +='<tr><td>Naziv:</td><td>';
		content += data[i].name;
		content += '</td></tr>';
		content += '</td></tr>';
		content +='<tr><td>Status:</td><td>';
		if(data[i].deleted == false){
			content += "AKTIVAN";
		}else{
			content += "OBRISAN";
		}
		content += '</td></tr>';
		// MOZDA NAPRAVITI DUGME 'IZMENI' ??
		content += '</table></div>';
		content += '</div></div>';
		
		$("#viewAmenity").append(content);
	}	
}




function viewAmenity(event){
	window.location.assign(window.location.origin += "/TuristickaAgencija/editAmenity.html?id=" + event.id);
}