function loadContent(data){
	console.log(data);
}

$(document).ready(function(){
	$("button#save").click(function(){
		let id = $("#idAmenity").val();
		let name = $("#name").val();
		let deleted = false;

		var jsonUpdate = JSON.stringify({
			"id": id,
			"name":name,
			"deleted":deleted
		});
			
			$.ajax({
				method: "POST",
				url: "../TuristickaAgencija/rest/amenities/add",
				contentType: "application/json",
				data: jsonUpdate,
				datatype: "text"
			}).done(function(data){
				alert(data);
				if(data == "Dodat novi sadržaj apartmana"){
					openAmenities();
				}
			})
	})
	
	$("button#btnClose").click(function(){
		window.history.back();
	})
})



function openAmenities(){
	window.location.assign(window.location.origin += "/TuristickaAgencija/amenities.html");
}