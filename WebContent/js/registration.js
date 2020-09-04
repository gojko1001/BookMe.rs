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

function registration(){	//preuzeti unete vrednosti iz input-a u promenljive, pretvoriti u json objekat, poslati ajax poziv i obraditi odgovor
	let username = $("#username").val();
	let name = $("#name").val();
	let lastName = $("#lastName").val();
	let sex = $("#sex").val();
	let password = $("#password").val();
	let controlPassword = $("#controlPassword").val();
	
	var jsonRegistration = JSON.stringify({
		"username":username,
		"name":name,
		"lastName":lastName,
		"sex":sex,
		"password":password,
		"controlPassword":controlPassword
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
	
};


