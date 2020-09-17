function loadContent(user){
		if(!user){
			alert("Morate biti ulogovani!");
			window.history.back();
		}
		$("#name").val(user.name);
		$("#lastName").val(user.lastName);
		$("#username").val(user.username);
		$("#username1").append(user.username);
		if(user.male){
			$("#male").attr("selected", "selected");
		}else{
			$("#female").attr("selected", "selected");
		}
		switch(user.role){
			case "Administrator":
				$("#role").val("Administrator");
				break;
			case "Host":
				$("#role").val("Domaćin");
				break;
			case "Guest":
				$("#role").val("Gost");
				break;
		}
		$("#roleVal").val(user.role);
		$("#oldPass").val(user.password);
}

$(document).ready(function(){
	$("button#save").click(function(){
		let name = $("#name").val();
		let lastName = $("#lastName").val();
		let sex = '';
		if($("#sex").val() == "MUSKI"){
			sex = "true";
		}else{
			sex = "false";
		}
		let pass = $("#pass").val();
		let controlPass = $("#controlPass").val();

		let valid = true;
		
		// VALIDACIJA POLJA
		if(!name){
			$("#name").css("border-color", "red");
			$("#invalidName").css("font-size", "10px");
			valid = false;
		}else{
			$("#name").css("border-color", "grey");
			$("#invalidName").css("font-size", "0px");
		}
		if(!lastName){
			$("#lastName").css("border-color", "red");
			$("#invalidLastName").css("font-size", "10px");
			valid = false;
		}else{
			$("#lastName").css("border-color", "grey");
			$("#invalidLastName").css("font-size", "0px");
		}
		if(pass){
			if(pass.length < 8){
				$("#invalidPass").css("font-size", "10px");
				valid = false;
			}else{
				$("#invalidPass").css("font-size", "0px");
			}
			if(controlPass != pass){
				$("#invalidConfirmPass").css("font-size", "10px");
				valid = false;
			}else{
				$("#invalidConfirmPass").css("font-size", "0px");
			}
		}else{
			pass = $("#oldPass").val();
		} 
		
		if(valid){
			var jsonUpdate = JSON.stringify({
				"username": $("#username").val(),
				"name":name,
				"lastName":lastName,
				"male":sex,
				"password":pass,
				"role": $("#roleVal").val()
			});
			
			$.ajax({
				method: "PUT",
				url: "../TuristickaAgencija/rest/users/update",
				contentType: "application/json",
				data: jsonUpdate,
				datatype: "application/json"
			}).done(function(data){
				if(data != null){
					alert("Profil uspešno ažuriran!");
					openProfile();
				}
			})
		}
	})
	
	$("button#btnClose").click(function(){
		window.history.back();
	})
})

function openProfile(){
	window.location.assign(window.location.origin += "/TuristickaAgencija/profile.html");
}