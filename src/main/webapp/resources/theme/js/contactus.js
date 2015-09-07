//Contact Form validation JavaScript file

function validate(form) {

	if (document.getElementById("name").value == "") {
		alert("Enter your Name.");
		document.getElementById("name").focus();
		return false;
	}
	if (document.getElementById("address").value == "") {
		alert("Enter your Address.");
		document.getElementById("address").focus();
		return false;
	}

	if (document.getElementById("province").value == "") {
		alert("Enter your Province.");
		document.getElementById("province").focus();
		return false;
	}

	if (document.getElementById("phone").value == "") {
		alert("Enter your Contact No.");
		document.getElementById("phone").focus();
		return false;
	} else if (isNaN(document.getElementById("phone").value)) {
		alert("Enter only numbers in Contact field!");
		document.getElementById("phone").focus();
		return false;
	}

	if (document.getElementById("email").value == "") {
		alert("Enter your E-mail address");
		document.getElementById("email").focus();
		return false;
	} else if (!IsEmailValid(document.getElementById("email").value)) {
		alert("Please enter a valid email address.");
		document.getElementById("email").focus();
		return false;
	}

	if (document.getElementById("software").value == "") {
		alert("Enter your Accounting Software.");
		document.getElementById("software").focus();
		return false;
	}

	if (document.getElementById("about").value == "") {
		alert("Please Enter About Yourself field !");
		document.getElementById("about").focus();
		return false;
	}

}

function IsEmailValid(e) {
//	var emailfilter = "^[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$";
	//var emailfilter = /^\w+[\+\.\w-]*@([\w-]+\.)*\w+[\w-]*\.([a-z]{2,4}|\d+)$/i;
	var re = /[A-Z0-9._%+-]+@[A-Z0-9.-]+.[A-Z]{2,4}/igm;
	return re.test($email.val());
//	var returnval = emailfilter.test(e.value);
/*	if (returnval == false) {
		alert("Please enter a valid email address.");
		e.select();
	}*/
//	return returnval;
}
