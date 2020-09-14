function loadContent(data){
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