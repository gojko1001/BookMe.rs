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

$(document).ready(function(){
	$("button#btnClose").click(function(){
		window.history.back();
	})
})


function viewAllAmenities(data){
	var i;
	for(i=0; i<data.length; i++){
		content = '<input type="checkbox" id="';
		content += data[i].id;
		content += '"><label for="';
		content += data[i].id;
		content += '">&nbsp;'
		content += data[i].name;
		content += '</label><br>';
		
		$("#viewAmenity").append(content);
	}
}