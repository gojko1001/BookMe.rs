$(document).ready(function(){
		$.ajax({
			method:"GET",
			url:"../TuristickaAgencija/rest/users/get",	
			datatype:"application/json"
		}).done(function(data){
			var content = '<div class="data" style="min-height:70px">';
			content += '<h4 style="padding-left:15px; padding-top:15px">';
			content += 'Korisničko ime: &nbsp;';
			content += data.username;
			content += '</h4>';
			content += '</div>';
			content += '<div class="data">'
			content += '<h5 style="padding-left:15px; padding-top:15px; color:grey">';
			content += 'Detalji o korisniku </h5>';
			content += '<table style="margin:25px">';
			content += '<tr><td float="right">Ime: &nbsp';
			content += data.name;
			content += '</td></tr>';
			content += '<tr><td>Prezime: &nbsp';
			content += data.lastName;
			content += '</td></tr>';
			content += '<tr><td>Pol: &nbsp';
			content += data.male;
			content += '</td></tr>';
			content += '<tr><td>Uloga: &nbsp';
			content += data.role;
			content += '</td></tr></table></div>';
	
			$("#myInfo").append(content);
			console.log(data);
		});
})