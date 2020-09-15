function loadContent(data){
	console.log(data);
	$.ajax({
		method: "GET",
		url: "../TuristickaAgencija/rest/amenities/getById" + window.location.search,
		datatype: "application/json",
	}).done(function(data){
		viewAmenity(data);
	});
}

$(document).ready(function(){
	$("button#btnClose").click(function(){
		window.history.back();
	})
})


function viewAmenity(data){
	$("#idAmenity").val(data.id);
	$("#name").val(data.name);
	
	if(data.deleted == true){
		document.getElementById("delete").checked = true;
	}else{
		document.getElementById("dontDelete").checked = true;
	}
}

$(document).ready(function(){
	$("button#save").click(function(){
		let id = $("#idAmenity").val();
		let name = $("#name").val();
		let deleted = false;
		if(document.getElementById("delete").checked == true){
			deleted = true;
		}

		var jsonUpdate = JSON.stringify({
			"id": id,
			"name":name,
			"deleted":deleted
		});
			
			$.ajax({
				method: "PUT",
				url: "../TuristickaAgencija/rest/amenities/update",
				contentType: "application/json",
				data: jsonUpdate,
				datatype: "application/json"
			}).done(function(data){
				if(data != null){
					alert("Sadržaj uspešno ažuriran!");
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