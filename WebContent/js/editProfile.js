$(document).ready(function(){
	$("button#save").click(function(){
		let username = $("#username").val();
		let name = $("#name").val();
		let lastName = $("#lastName").val();
		let sex = $("#sex").val();
		let password = $("#password").val();
		let controlPassword = $("#controlPassword").val();
//TODO: nije zavrseno...		
		// VALIDACIJA POLJA

		if(!name){
			$("#name").css("border-color", "red");
			$("#invalidName").css("font-size", "10px");
		}else{
			$("#name").css("border-color", "grey");
			$("#invalidName").css("font-size", "0px");
		}
		if(!lastName){
			$("#lastName").css("border-color", "red");
			$("#invalidLastName").css("font-size", "10px");
		}else{
			$("#lastName").css("border-color", "grey");
			$("#invalidLastName").css("font-size", "0px");
		}
		if(password){
			if(password.length < 8){
				$("#invalidPass").css("font-size", "10px");
			}else{
				$("#invalidPass").css("font-size", "0px");
			}
			if(controlPassword != password){
				$("#invalidControlPass").css("font-size", "10px");
			}else{
				$("#invalidControlPass").css("font-size", "0px");
			}
		}
		
		//role: Treba da se uskladi sa bazom
		var jsonRegistration = JSON.stringify({
			"username":username,
			"name":name,
			"lastName":lastName,
			"male":sex,
			"password":password,
			"role":"Guest"
		});
		
		$.ajax({
			method: "PUT",
			url: "../TuristickaAgencija/rest/users/update",
			contentType: "application/json",
			data: jsonRegistration,
			datatype: "text"
		}).done(function(data){
			if(data != null){
				alert("Profil uspešno ažuriran!");
				openProfile();
			}
		})
		
	})
})

function openProfile(){
	window.location.assign(window.location.origin += "/TuristickaAgencija/profile.html");
}