//window.onload = function(){
function loadContent(data){
/*	$.ajax({
			method: "GET",
			url: "../TuristickaAgencija/rest/users/getUser",
			datatype: "application/json"
	}).done(function(data){*/
		if(!data){
			alert("Morate biti ulogovani!");
			window.history.back();
		}
		$("#name").append(data.name);
		$("#lastName").append(data.lastName);
		$(".username").append(data.username);
		if(data.male){
			$("#sex").append("Muški");
		}else{
			$("#sex").append("Ženski");
		}
		switch(data.role){
			case "Administrator":
				$("#role").append("Administrator");
				break;
			case "Host":
				$("#role").append("Domaćin");
				break;
			case "Guest":
				$("#role").append("Gost");
				break;
		}
	//	$("#btnDropDown").html(data.username);
	//})
}


function loadContentHost(data){
			if(!data){
				alert("Morate biti ulogovani!");
				window.history.back();
			}
			$("#name").append(data.name);
			$("#lastName").append(data.lastName);
			$(".username").append(data.username);
			if(data.male){
				$("#sex").append("Muški");
			}else{
				$("#sex").append("Ženski");
			}
			switch(data.role){
				case "Administrator":
					$("#role").append("Administrator");
					break;
				case "Host":
					$("#role").append("Domaćin");
					break;
				case "Guest":
					$("#role").append("Gost");
					break;
			}
}