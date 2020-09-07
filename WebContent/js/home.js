
getAllApartments();

// TODO 5: f-ja za userInfo treba

function getAllApartments(){	
	$.ajax({
		method:"GET",
		url:"../TuristickaAgencija/rest/apartments/all",
		datatype:"application/json"
	}).done(function(data){
		var i;
		for(i=0; i<data.length; i++){
			var content = "<p>";
			content += data[i].id;
			content += "</p>";
			$("#listOfApartments").append(content);
		}
		console.log(data);
	});	
}