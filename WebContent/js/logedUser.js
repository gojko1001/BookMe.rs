window.onload = function(){
	$.ajax({
			method: "GET",
			url: "../TuristickaAgencija/rest/users/getUser",
			datatype: "application/json"
	}).done(function(data){
		if(!data){
			$(".logedOut").show();
		}else{
			$("#btnDropDown").html(data.username);
			$(".logedIn").show();
			if(data.role == "Host"){
				$(".addApartment").show();
				$(".viewReservations").show();
				var elementsActive = document.getElementsByClassName('filterGroup');
				$(elementsActive[0]).show();
				$(elementsActive[1]).show();
				$('.viewAllUsers').show();
			}
			if(data.role == "Administrator"){
				$(".viewReservations").show();
				$(".amenities").show();
				$("#activeApartment").show();
				var elementsActive = document.getElementsByClassName('filterGroup');
				$(elementsActive[0]).show();
				$(elementsActive[1]).show();
				$('.viewAllUsers').show();
				$('.registrationHost').show();
			}
		}
		loadContent(data);
	});
}



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
			window.location.assign(window.location.origin += "/TuristickaAgencija?logout=true");

		});
	});
	
	$(".addApartment").click(function(){
		window.location.assign(window.location.origin + "/TuristickaAgencija/addApartment.html");
	})
	
	$(".viewReservations").click(function(){
		window.location.assign(window.location.origin + "/TuristickaAgencija/reservations.html");
	})
	
	$(".amenities").click(function(){
		window.location.assign(window.location.origin + "/TuristickaAgencija/amenities.html");
	})
	
	$(".viewAllUsers").click(function(){
		window.location.assign(window.location.origin + "/TuristickaAgencija/viewUsers.html");
	})
	
	$(".registrationHost").click(function(){
		window.location.assign(window.location.origin + "/TuristickaAgencija/registrationHost.html");
	})

})

function openIndex(){
	window.location.assign(window.location.origin + "/TuristickaAgencija/");
}