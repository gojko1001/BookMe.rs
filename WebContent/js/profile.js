function loadContent(user){
		if(!user){
			alert("Morate biti ulogovani!");
			window.history.back();
		}
		$("#name").append(user.name);
		$("#lastName").append(user.lastName);
		$(".username").append(user.username);
		if(user.male){
			$("#sex").append("Muški");
		}else{
			$("#sex").append("Ženski");
		}
		switch(user.role){
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