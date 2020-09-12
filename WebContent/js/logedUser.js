
//getAllApartments();

// TODO 5: f-ja za userInfo treba

$(document).ready(function(){
	$("#btnDropDown").click(function(){
			$("#userMenu").toggle();
		})
	
	$(".MyProfile").click(function(){
		window.location.assign(window.location.origin += "/TuristickaAgencija/profile.html");
	});
	
	$(".EditProfile").click(function(){
		window.location.assign(window.location.origin + "/TuristickaAgencija/editProfile.html");
	})
	
	$(".Logout").click(function() {
		$.ajax({
			method:"POST",
			url:"../TuristickaAgencija/rest/users/logout",	
			datatype:"text"
		}).done(function(data){
			window.location.assign(window.location.origin += "/TuristickaAgencija/");
		});
	});

})