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
		$(".quickSearch").toggle();
	})
	
	document.getElementById("oddSex").checked = true;
	document.getElementById("oddRole").checked = true;
	
	$("#filterSearch").click(function(){
		var inputUsername = "";
		inputUsername = $('#inputUsername').val();
		var role;
		if(document.getElementById("guest").checked == true){
			role = "guest";
		}else if(document.getElementById("host").checked == true){
			role = "host"
		}else if(document.getElementById("administrator").checked == true){
			role = "admin";
		}else{
			role = "";
		}
		var sex;
		if(document.getElementById("male").checked == true){
			sex = "male";
		}else if(document.getElementById("female").checked == true){
			sex = "female"
		}else{
			sex = "";
		}
		
		var jsonFilter = JSON.stringify({
			"username":inputUsername,
		    "role":role,
		    "sex":sex
		});
		
		$.ajax({
			method: "POST",
			url: "../TuristickaAgencija/rest/users/filter",
			data: jsonFilter,
			contentType: "application/json",
			datatype: "application/json",
		}).done(function(data){
			$("#viewUser").html("");
			showUsers(data);
		});
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
