$(document).ready(function(){
	let inputElem = $("input[name=username]");
	let forma = $("form");
	forma.submit(function(event){
		//alert("Ime je " + inputElem.val());
		let username = inputElem.val();
		
		let allTds = $('td');
		if(username == ''){
			allTds[1].append("Unesite korisničko ime.");
			$(allTds[1]).css("color", "red");
		}
		 
		event.preventDefault();
	});
});