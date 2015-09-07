//Contact Form validation JavaScript file

function validate(form) {
	if (document.getElementById("company").value == "") {
		alert("Enter your Company Name and must have atleast 4 characters");
		document.getElementById("company").focus();
		return false;
	} else if (!IsValidCompanyName(document.getElementById("company").value)) {
		alert("Company Name can only contain alphabets, dot(.), spaces and must have atleast 4 characters");
		document.getElementById("company").focus();
		return false;
	} else if(document.getElementById("company").value.length > 50) {
		alert("Company Name cannot be more then 50 characters");
		document.getElementById("company").focus();
		return false;		
	}

	if (document.getElementById("fname").value == "") {
		alert("Enter your First Name and must have atleast 3 characters");
		document.getElementById("fname").focus();
		return false;
	} else if (!IsValidName(document.getElementById("fname").value)) {
		alert("Please enter valid first name, it should only contains alphabets and must have atleast 3 characters");
		document.getElementById("fname").focus();
		return false;
	} else if (document.getElementById("fname").value.length > 20) {
		alert("First Name cannot be more then 20 characters");
		document.getElementById("fname").focus();
		return false;
	}

	if (document.getElementById("lname").value == "") {
		alert("Enter your Last Name and must have atleast 3 characters");
		document.getElementById("lname").focus();
		return false;
	} else if (!IsValidName(document.getElementById("lname").value)) {
		alert("Please enter valid last name, it should only contains alphabets and must have atleast 3 characters");
		document.getElementById("lname").focus();
		return false;
	} else if (document.getElementById("lname").value.length > 20) {
		alert("Last Name cannot be more then 20 characters");
		document.getElementById("lname").focus();
		return false;
	} 
	
	if (document.getElementById("address").value == "") {
		alert("Enter your Address and must have atleast 10 characters");
		document.getElementById("address").focus();
		return false;
	} else if (!IsValidAddress(document.getElementById("address").value)) {
		alert("Address can only contain alphabets and numeric characters, dash(-), dot(.), hash(#) and space and must have atleast 10 characters");
		document.getElementById("address").focus();
		return false;
	} else if (document.getElementById("address").value.length > 150) {
		alert("Address cannot be more then 150 characters");
		document.getElementById("address").focus();
		return false;
	}

	if (document.getElementById("province").value == "") {
		alert("Enter your Province and must have atleast 4 characters");
		document.getElementById("province").focus();
		return false;
	} else if (!IsValidProvinceName(document.getElementById("province").value)) {
		alert("Province can only contain alphabets, dash(-) and must have atleast 4 characters");
		document.getElementById("province").focus();
		return false;
	} else if (document.getElementById("province").value.length > 40) {
		alert("Province cannot be more then 40 characters.");
		document.getElementById("province").focus();
		return false;
	}

	if (document.getElementById("zip").value == "") {
		alert("Enter your Zip and must have atleast 4 digits");
		document.getElementById("zip").focus();
		return false;
	} else if (isNaN(document.getElementById("zip").value)) {
		alert("Enter only numbers in Zip field!");
		document.getElementById("zip").focus();
		return false;
	} else if (document.getElementById("zip").value.length > 7) {
		alert("Zip cannot be more then 7 digits");
		document.getElementById("zip").focus();
		return false;
	} else if (document.getElementById("zip").value.length < 4) {
		alert("Zip cannot be less then 4 digits");
		document.getElementById("zip").focus();
		return false;
	}

	if (document.getElementById("phone").value == "") {
		alert("Enter your Contact No. and must have atleast 8 digits");
		document.getElementById("phone").focus();
		return false;
	} else if (isNaN(document.getElementById("phone").value)) {
		alert("Enter only numbers in Contact No. field!");
		document.getElementById("phone").focus();
		return false;
	} else if (document.getElementById("phone").value.length > 15) {
		alert("Contact No. cannot be more then 15 digits");
		document.getElementById("phone").focus();
		return false;
	} else if (document.getElementById("phone").value.length < 8) {
		alert("Contact No. cannot be less then 8 digits");
		document.getElementById("phone").focus();
		return false;
	}
		
	if (document.getElementById("username").value == "") {
		alert("Enter your Username and must have atleast 8 characters");
		document.getElementById("username").focus();
		return false;
	} else if (!IsValidUsername(document.getElementById("username").value)) {
		alert("Username can only contain alphabets and numeric and must have atleast 8 characters");
		document.getElementById("username").focus();
		return false;
	} else if (document.getElementById("username").value.length > 20) {
		alert("Username cannot be more then 20 characters.");
		document.getElementById("username").focus();
		return false;
	}
	
	if (document.getElementById("password").value == "") {
		alert("Enter your Password and must have atleast 8 characters");
		document.getElementById("password").focus();
		return false;
	} else if (!IsValidPassword(document.getElementById("password").value)) {
		alert("Password can only contain alphabets and numeric and must have atleast 8 characters");
		document.getElementById("password").focus();
		return false;
	} else if (document.getElementById("password").value.length > 20) {
		alert("Password cannot be more then 20 characters.");
		document.getElementById("password").focus();
		return false;
	}
	
	if (document.getElementById("email").value == "") {
		alert("Enter your E-mail address");
		document.getElementById("email").focus();
		return false;
	} else if (!IsValidEmail(document.getElementById("email").value)) {
		alert("Please enter a valid email address.");
		document.getElementById("email").focus();
		return false;
	} else if (document.getElementById("email").value.length > 45) {
		alert("Email cannot be more then 45 characters.");
		document.getElementById("email").focus();
		return false;
	}

	if (document.getElementById("software").value == "") {
		alert("Enter your Accounting Software and must have atleast 2 characters, or write NA");
		document.getElementById("software").focus();
		return false;
	} else if (!IsValidSoftwareDetails(document.getElementById("software").value)) {
		alert("Accounting Software can only contain alphabets, dash(-) and dot(.) and must have atleast 4 characters");
		document.getElementById("software").focus();
		return false;
	} else if (document.getElementById("software").value.length > 100) {
		alert("Software details cannot be more then 100 characters.");
		document.getElementById("software").focus();
		return false;
	}

	if (document.getElementById("about").value == "") {
		alert("Please Enter About Yourself field and it must not be less then 30 characters.");
		document.getElementById("about").focus();
		return false;
	} else if (!IsValidSoftwareDetails(document.getElementById("about").value)) {
		alert("This field can only contain alphabets and numeric characters, dot(.) and dash(-) and must have atleast 30 characters");
		document.getElementById("about").focus();
		return false;
	} else if (document.getElementById("about").value.length > 350) {
		alert("Your details about yourself cannot be more then 350 characters.");
		document.getElementById("about").focus();
		return false;
	}


}

function IsValidEmail(e) {
//	var emailfilter = "^[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$";
	//var emailfilter = /^\w+[\+\.\w-]*@([\w-]+\.)*\w+[\w-]*\.([a-z]{2,4}|\d+)$/i;
	var re = /[A-Z0-9._%+-]+@[A-Z0-9.-]+.[A-Z]{2,4}/i;
	return re.test(e);
}

function IsValidName(e){
	var re = /^([a-z]{3,32})$/i;
	return re.test(e);
}

function IsValidCompanyName(e){
	var re = /^([A-Za-z.\s]{4,50})$/i; //^([A-Za-z.\s]{4,50})$
	return re.test(e);
}

function IsValidAddress(e){
	var re = /^([A-Za-z0-9-.\s#]{10,150})$/;
	return re.test(e);
}

function IsValidProvinceName(e){
	var re = /^([a-z-]{4,40})$/i;
	return re.test(e);
}

function IsValidUsername(e){
	var re = /^([a-z0-9]{8,20})$/i;
	return re.test(e);
}

function IsValidPassword(e){
	var re = /^([a-z0-9]{8,20})$/i;
	return re.test(e);
}

function IsValidSoftwareDetails(e){
	var re = /^([A-Za-z\s.-]{2,100})$/;
	return re.test(e);
}

function IsValidAboutDetails(e){
	var re = /^([A-Za-z0-9.-\s]{30,350})$/;
	return re.test(e);
}