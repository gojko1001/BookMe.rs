/*window.onload = function(){
	$.ajax({
			method: "GET",
			url: "../TuristickaAgencija/rest/users/getUser",
			datatype: "application/json"
	}).done(function(data){
		loadContent(data);
	});
}*/

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