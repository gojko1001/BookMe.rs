/*$(document).ready(function(){
	let inputElem = $("input[name=username]");
	let forma = $("form");
	forma.submit(function(event){
		//alert("Ime je " + inputElem.val());
		let username = inputElem.val();
		
		let allTds = $('td');
		if(username == ''){
			allTds[1].append("Unesite korisničko ime.");
			$(allTds[1]).css("color", "red")
		}
		 
		event.preventDefault();
	});
});*/


/*
$(document).ready(function(){
	//let inputElem = $("#username");
	let forma = $("form");
	forma.submit(function(event){
		let username = $("#username").val();
		let name = $("#name").val();
		let lastName = $("#lastName").val();
		let sex = $("#sex").val();
		let password = $("#password").val();
		alert("Korisničko ime je " + username);
		
		var jsonRegistration = JSON.stringify({
			"username":username,
			"name":name,
			"lastName":lastName,
			"sex":sex,
			"password":password,
		});
		
		$.ajax({
			method:"POST",
			url:"../TuristickaAgencija/rest/users/registration",
			contentType:"application/json",
			data:jsonRegistration,
			datatype:"text"
		}).success(function(data){
			console.log(data);
		});
		
		 
		event.preventDefault();
	});
});
*/


function registration(){	//preuzeti unete vrednosti iz input-a u promenljive, pretvoriti u json objekat, poslati ajax poziv i obraditi odgovor
	let username = $("#username").val();
	let name = $("#name").val();
	let lastName = $("#lastName").val();
	let sex = $("#sex").val();
	let password = $("#password").val();
	
	console.log("pera");

	
	var jsonRegistration = JSON.stringify({
		"username":username,
		"name":name,
		"lastName":lastName,
		"male":sex,
		"password":password,
		"role":"Guest"
	});

	console.log(jsonRegistration);
	
	$.ajax({
		method:"POST",
		url:"../TuristickaAgencija/rest/users/registration",
		contentType:"application/json",
		data:jsonRegistration,
		datatype:"text"
	}).success(function(data){
		console.log(data);
	});
	
	
}


