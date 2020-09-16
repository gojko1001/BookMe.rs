function loadContent(user){
	console.log(user);
	$.ajax({
		method:"GET",
		url:"../TuristickaAgencija/rest/users/all",
		datatype:"application/json"
	}).done(function(data){
		showUsers(data);
		console.log(data);
	});	
}


$(document).ready(function(){
	$("#filterBtn").click(function(){
		$("#filterTable").slideToggle();
	})
	
	document.getElementById("oddSex").checked = true;
	document.getElementById("oddRole").checked = true;
	
	$("#filterSearch").click(function(){
		let priceFrom = Number($("#priceFrom").val());
		if(!priceFrom){
			priceFrom = -1;
		}
		let priceTo = Number($("#priceTo").val());
		if(!priceTo){
			priceTo = -1;
		}
		let roomFrom = Number($("#roomFrom").val());
		if(!roomFrom){
			roomFrom = -1;
		}
		let roomTo = Number($("#roomTo").val());
		if(!roomTo){
			roomTo = -1;
		}
		let spotNum = Number($("#spotNum").val());
		if(!spotNum){
			spotNum = -1;
		}
		
		var activity;
		if(document.getElementById("active").checked == true){
			activity = 1;
		}else if(document.getElementById("inActive").checked == true){
			activity = 2;
		}else{
			activity = 0;
		}
		
		var jsonFilter = JSON.stringify({
			
		});
		
		/*$.ajax({
			method: "POST",
			url: "../TuristickaAgencija/rest/apartments/filter",
			data: jsonFilter,
			contentType: "application/json",
			datatype: "application/json",
		}).done(function(data){
			$("#listOfApartments").html("");
			showApartments(data);
		});*/
	})
});

function showUsers(data){
	var i;
		for(i=0; i<data.length; i++){
					content = '<div class="card" id="';
					content += data[i].username;
					content += '">';
					content += '<div class="card-body">';
					content += '<div class="data">';
					content += '<table style="margin:10px">';
					content += '<tr><td float="right">Korisničko ime:</td><td>';
					content += data[i].username;
					content += '</td></tr>';
					content +='<tr><td>Ime:</td><td>';
					content += data[i].name;
					content += '</td></tr>';
					content +='<tr><td>Prezime:</td><td>';
					content += data[i].lastName;
					content += '</td></tr>';
					content +='<tr><td>Pol:</td><td>';
					if(data[i].male == false){
						content += 'Ženski';
					}else{
						content += 'Muški';
					}
					content += '</td></tr>';
					content +='<tr><td>Uloga:</td><td>';
					if(data[i].role == 'Guest'){
						content += 'Gost';
					}else if(data[i].role == 'Host'){
						content += 'Domaćin';
					}else{
						content += 'Administrator';
					}
					content += '</td></tr>';
					content += '</table></div>';
					content += '</div></div>';
					
					$("#viewUser").append(content);
		}	
}
