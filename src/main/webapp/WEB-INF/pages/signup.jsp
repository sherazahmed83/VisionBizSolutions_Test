<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>

<!DOCTYPE html>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<sec:csrfMetaTags />

<title><spring:message code="label.page.title" /></title>
<link href="${pageContext.request.contextPath}/resources/css/style.css"
	rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/resources/js/signup.js"></script>
<script src="https://www.google.com/recaptcha/api.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<!--[if lte IE 7]>
		<link rel="stylesheet" href="css/ie.css" type="text/css" charset="utf-8" />	
	<![endif]-->
<!-- 
<style type="text/css">
.myTableBg { width:180px;background:#dedede url(/pix/web_graphics/free_website_graphics/background_patterns/101Emboss8.gif) repeat;border-collapse:collapse; }
.myTableBg td, .myTableBg th { border:2px solid black; }
</style>
 -->
<script>
	$(document)
			.ready(
					function() {
						
						var isError = false;
						$('#company')
						.focusout(
								function() { 
									var re = /^([A-Za-z.\s]{4,50})$/;
									var result = re.test($('#company')
											.val());

									//run the character number check  
									if ($('#company').val().length == 0) {
										$('#company_result')
												.html(
														'<span style="color: #ff0000;"> Company Name is required and must consists of atleast 4 characters</span>');
										isError = true;
									} else if ($('#company').val().length > 50) {
										$('#company_result')
										.html(
												'<span style="color: #ff0000;"> Company Name cannot be more then 50 characters</span>');
										isError = true;
									} else if (!result) {
										$('#company_result')
												.html(
														'<span style="color: #ff0000;"> Company Name can only contain alphabets and dot(.) and must have atleast 4 characters</span>');
										isError = true;
									} else {
										isError = false;
										$('#company_result').html('');
									}
								});
						
						$('#fname')
						.focusout(
								function() {
									var re = /^([a-z]{3,20})$/i;
									var result = re.test($('#fname')
											.val());

									//run the character number check  
									if ($('#fname').val().length == 0) {
										$('#fname_result')
												.html(
														'<span style="color: #ff0000;"> First Name is required and must consists of atleast 3 characters</span>');
										isError = true;
									} else if ($('#fname').val().length > 20) {
										$('#fname_result')
										.html(
												'<span style="color: #ff0000;"> First Name cannot be more then 20 characters</span>');
										isError = true;
									} else if (!result) {
										$('#fname_result')
												.html(
														'<span style="color: #ff0000;"> First Name can only contain alphabets and must have atleast 3 characters</span>');
										isError = true;
									} else {
										isError = false;
										$('#fname_result').html('');
									}
								});

				$('#lname')
						.focusout(
								function() { 
									var re = /^([a-z]{3,20})$/i;
									var result = re.test($('#lname')
											.val());

									//run the character number check  
									if ($('#lname').val().length == 0) {
										$('#lname_result')
												.html(
														'<span style="color: #ff0000;"> Last Name is required and must consists of atleast 3 characters</span>');
										isError = true;
									} else if ($('#lname').val().length > 20) {
										$('#lname_result')
										.html(
												'<span style="color: #ff0000;"> Last Name cannot be more then 20 characters</span>');
										isError = true;
									} else if (!result) {
										$('#lname_result')
												.html(
														'<span style="color: #ff0000;"> Last Name can only contain alphabets and must have atleast 3 characters.</span>');
										isError = true;
									} else {
										isError = false;
										$('#lname_result').html('');
									}
								});

				$('#address')
				.focusout(
						function() { 
							var re = /^([A-Za-z0-9-.\s#]{10,150})$/;
							var result = re.test($('#address')
									.val());
							//run the character number check  
							if ($('#address').val().length == 0) {
								$('#address_result')
										.html(
												'<span style="color: #ff0000;"> Address is required and must consists of atleast 10 characters</span>');
								isError = true;
							} else if ($('#address').val().length > 150) {
								$('#address_result')
								.html(
										'<span style="color: #ff0000;"> Address cannot be more then 150 characters</span>');
								isError = true;
							} else if (!result) {
								$('#address_result')
								.html(
										'<span style="color: #ff0000;"> Address can only contain alphabets and numeric characters, dash(-), dot(.), number-sign(#) and must have atleast 10 characters</span>');
								isError = true;
							} else {
								isError = false;
								$('#address_result').html('');
							}
						});				

				$('#province')
				.focusout(
						function() { 
							var re = /^([a-z-]{4,40})$/i;
							var result = re.test($('#province')
									.val());

							//run the character number check  
							if ($('#province').val().length == 0) {
								$('#province_result')
										.html(
												'<span style="color: #ff0000;"> Province is required and must consists of atleast 4 characters</span>');
								isError = true;
							} else if ($('#province').val().length > 40) {
								$('#province_result')
								.html(
										'<span style="color: #ff0000;"> Province cannot be more then 40 characters</span>');
								isError = true;
							} else if (!result) {
								$('#province_result')
										.html(
												'<span style="color: #ff0000;"> Province can only contain alphabets, dash(-) and must have atleast 4 characters</span>');
								isError = true;
							} else {
								isError = false;
								$('#province_result').html('');
							}
						});
				
				$('#zip')
				.focusout(
						function() {
							//run the character number check  
							if ($('#zip').val().length == 0) {
								$('#zip_result')
										.html(
												'<span style="color: #ff0000;"> Zip is required</span>');
								isError = true;
							} else if ($('#zip').val().length > 7) {
								$('#zip_result')
								.html(
										'<span style="color: #ff0000;"> Zip cannot be more then 7 digits</span>');
								isError = true;
							} else if (isNaN($('#zip').val())) {
								$('#zip_result')
										.html(
												'<span style="color: #ff0000;"> Zip can only contain numbers or digits</span>');
								isError = true;
							} else {
								isError = false;
								$('#zip_result').html('');
							}
						});				
				

				$('#phone')
				.focusout(
						function() {
							//run the character number check  
							if ($('#phone').val().length == 0) {
								$('#phone_result')
										.html(
												'<span style="color: #ff0000;"> Contact No. is required</span>');
								isError = true;
							} else if ($('#phone').val().length > 15) {
								$('#phone_result')
								.html(
										'<span style="color: #ff0000;"> Contact No. cannot be more then 15 digits</span>');
								isError = true;
							} else if (isNaN($('#phone').val())) {
								$('#phone_result')
										.html(
												'<span style="color: #ff0000;"> Contact No. can only contain numbers or digits</span>');
								isError = true;
							} else {
								isError = false;
								$('#phone_result').html('');
							}
						});

		$('#software')
				.focusout(
						function() { 
							var re = /^([A-Za-z\s.-]{2,100})$/;
							var result = re.test($('#software')
									.val());
							//run the character number check  
							if ($('#software').val().length == 0) {
								$('#software_result')
										.html(
												'<span style="color: #ff0000;"> Accounting Software is required and must consists of atleast 2 characters</span>');
								isError = true;
							} else if ($('#software').val().length > 100) {
								$('#software_result')
								.html(
										'<span style="color: #ff0000;"> Accounting Software cannot be more then 100 digits</span>');
								isError = true;
							} else if (!result) {
								$('#software_result')
								.html(
										'<span style="color: #ff0000;"> Accounting Software can only contain alphabets, dash(-) and dot(.) and must have atleast 2 characters</span>');
								isError = true;
							} else {
								isError = false;
								$('#software_result').html('');
							}
						});

		$('#about')
				.focusout(
						function() { 
							var re = /^([A-Za-z0-9.-\s]{30,350})$/;
							var result = re.test($('#about')
									.val());
							//run the character number check  
							if ($('#about').val().length == 0) {
								$('#about_result')
										.html(
												'<span style="color: #ff0000;"> Write some words about yourself, minimum is atleast 30 characters.</span>');
								isError = true;
							} else if (!result) {
								$('#about_result')
								.html(
										'<span style="color: #ff0000;"> This field can only contain alphabets and numeric characters, dot(.) and dash(-) and must have atleast 30 characters</span>');
								isError = true;
							} else {
								isError = false;
								$('#about_result').html('');
							}
						});				
				
		$('#password')
		.focusout(
				function() { 
					var re = /^([A-Za-z0-9]{8,20})$/;
					var result = re.test($('#password')
							.val());

					//run the character number check  
					if ($('#password').val().length == 0) {
						$('#password_result')
								.html(
										'<span style="color: #ff0000;"> Password is required and must consists of atleast 8 characters</span>');
						isError = true;
					} else if ($('#password').val().length > 20) {
						$('#password_result')
						.html(
								'<span style="color: #ff0000;"> Password cannot be more then 20 characters</span>');
						isError = true;
					} else if (!result) {
						$('#password_result')
								.html(
										'<span style="color: #ff0000;"> Password can only contain alphabets and numerics, and must have atleast 8 characters</span>');
						isError = true;
					} else {
						isError = false;
						$('#password_result').html('');
					}
				});		
		
		$('#username')
		.focusout(
				function() { 
					var re = /^([A-Za-z0-9]{8,20})$/;
					var result = re.test($('#username')
							.val());

					//run the character number check  
					if ($('#username').val().length == 0) {
						$('#username_result')
								.html(
										'<span style="color: #ff0000;"> Username is required and must consists of atleast 8 characters</span>');
						isError = true;
					} else if ($('#username').val().length > 20) {
						$('#username_result')
						.html(
								'<span style="color: #ff0000;"> Username cannot be more then 20 characters</span>');
						isError = true;
					} else if (!result) {
						$('#username_result')
								.html(
										'<span style="color: #ff0000;"> Username can only contain alphabets and numerics, and must have atleast 8 characters</span>');
						isError = true;
					} else {
						//else show the cheking_text and run the function to check  
						$('#username_result')
								.html('Checking...');
						check_availability();
					}
				});		

		$('#email')
		.focusout(
				function() { /*/[A-Z0-9._%+-]+@[A-Z0-9.-]+.[A-Z]{2,4}/i*/
					var re = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+.[A-Z]{2,4}$/i;
					var result = re.test($('#email')
							.val());

					//run the character number check  
					if ($('#email').val().length == 0) {
						$('#email_result')
								.html(
										'<span style="color: #ff0000;"> Email is required and must be valid.</span>');
						isError = true;
					} else if ($('#email').val().length > 45) {
						$('#email_result')
						.html(
								'<span style="color: #ff0000;"> Email cannot be more then 45 characters</span>');
						isError = true;
					} else if (!result) {
						$('#email_result')
								.html(
										'<span style="color: #ff0000;"> Email must only be a valid email, e.g. <strong>someemail@mail.com</strong></span>');
						isError = true;
					} else {
						//else show the cheking_text and run the function to check  
						check_email_availability();
					}
				});		

		
		/*				
						//the min chars for username  
						var min_chars = 8;

						//result texts  
						var characters_error = '<span style="color: #ff0000;"> Minimum number of characters is 8</span>';
						var email_required_error = '<span style="color: #ff0000;"> Email is required</span>';

						//when password field got focus
						$('#password')
								.focus(
										function() {
											//run the character number check  
											if ($('#username').val().length < min_chars) {
												//if it's bellow the minimum show characters_error text '  
												$(
														'#username_result')
														.html(characters_error);
												$('#username').focus();
											} else {
												//else show the cheking_text and run the function to check  
												$(
														'#username_result')
														.html(checking_html);
												check_availability();
											}
										});

						//when username field got focus
						$('#username').focus(
								function() {
									if ($('#email').val().length == 0) {
										//if it's empty '  
										$('#email_result').html(
												email_required_error);
										$('#email').focus();
									} else {
										check_email_availability();
									}
								});
*/
					});

	/*function to check username availability*/
	function check_availability() {
		var url = "<c:url value='/isusernameexists' />";
		//get the username  
		var username = $('#username').val();

		var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
		var csrfHeader = $("meta[name='_csrf_header']").attr("content");
		var csrfToken = $("meta[name='_csrf']").attr("content");

		var json = {
			"username" : username
		};

		$.ajax({
			url : url,
			data : JSON.stringify(json),
			type : "POST",
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
				xhr.setRequestHeader(csrfHeader, csrfToken);
			},
			success : function(result) {
				if (result) {
					//show that the username is available  
					$('#username_result').html(
							'<span style="color: #067a08;">*<strong>' + username
									+ '</strong> is Available</span>');
				} else {
					//show that the username is NOT available  
					$('#username_result').html(
							'<span style="color: #ff0000; font-weight: bold;">Sorry! ' + username
									+ ' is not Available</span>');
				}
			}
		});

	}

	/*function to check email availability*/
	function check_email_availability() {
		var url = "<c:url value='/isemailexists' />";
		//get the username  
		var email = $('#email').val();

		var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
		var csrfHeader = $("meta[name='_csrf_header']").attr("content");
		var csrfToken = $("meta[name='_csrf']").attr("content");

		var json = {
			"email" : email
		};

		$
				.ajax({
					url : url,
					data : JSON.stringify(json),
					type : "POST",
					beforeSend : function(xhr) {
						xhr.setRequestHeader("Accept", "application/json");
						xhr
								.setRequestHeader("Content-Type",
										"application/json");
						xhr.setRequestHeader(csrfHeader, csrfToken);
					},
					success : function(result) {
						if (result) {
							//do nothing
							$('#email_result').html('');
						} else {
							//show that the username is NOT available  
							$('#email_result')
									.html(
											'<span style="color: #ff0000; font-weight: bold;">Sorry! this email is already taken. Please provide another email address.</span>');
						}
					}
				});

	}
</script>
</head>

<body onload='document.contactusform.company.focus();'>
	<table>
		<tr>
			<td></td>
			<td align="right" width="100%"><a href="?locale=en"><img
					src="${pageContext.request.contextPath}/resources/images/UK_flag.jpg"
					alt="EnglishLanguage" /></a> <a href="?locale=fr"><img
					src="${pageContext.request.contextPath}/resources/images/France_flag.jpg"
					alt="FrenchLanguage" /></a></td>
		</tr>
		<tr>
			<td></td>
			<td align="right" width="100%"><sec:authorize
					access="hasRole('ROLE_USER')">
					<!-- For login user -->
					<c:url value="/logout" var="logoutUrl" />
					<form action="${logoutUrl}" method="post" id="logoutForm">
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					</form>
					<script>
						function formSubmit() {
							document.getElementById("logoutForm").submit();
						}
					</script>

					<c:if test="${pageContext.request.userPrincipal.name != null}">
						<span style="font-size: 10pt; color: #ffffff;"> Welcome
							${pageContext.request.userPrincipal.name} | <a
							style="color: #ffffff;" href="javascript:formSubmit()">
								Logout</a>
						</span>
					</c:if>
				</sec:authorize> <c:if test="${pageContext.request.userPrincipal.name == null}">
					<span style="font-size: 10pt; color: #ffffff;"> Welcome
						guest | <a style="color: #ffffff;"
						href="${pageContext.request.contextPath}/loginPage"> Login</a>
					</span>
				</c:if></td>
		</tr>
	</table>

	<div id="header">
		<a href="index.html" id="logo"><img
			src="${pageContext.request.contextPath}/resources/images/logo.gif"
			alt="LOGO" /></a>
		<div id="navigation">
			<ul>
				<li class="first"><a href="${pageContext.request.contextPath}/"><spring:message
							code="label.menu.home" /></a></li>
				<li><a href="${pageContext.request.contextPath}/about"><spring:message
							code="label.menu.aboutus" /></a></li>
				<li><a href="${pageContext.request.contextPath}/pricing"><spring:message
							code="label.menu.pricing" /></a></li>
				<li><a href="${pageContext.request.contextPath}/services"><spring:message
							code="label.menu.services" /></a></li>
				<li><a href="${pageContext.request.contextPath}/datasecurity"><spring:message
							code="label.menu.datasecurity" /></a></li>
				<li><a
					href="${pageContext.request.contextPath}/support"><spring:message
							code="label.menu.support" /></a></li>
				<li><a href="${pageContext.request.contextPath}/faqs"><spring:message
							code="label.menu.faqs" /></a></li>
				<li><a href="${pageContext.request.contextPath}/contact"><spring:message
							code="label.menu.contactus" /></a></li>
			</ul>
		</div>
		<div id="search">
			<form action="" method="">
				<input type="text" value="Search" class="txtfield"
					onblur="javascript:if(this.value==''){this.value=this.defaultValue;}"
					onfocus="javascript:if(this.value==this.defaultValue){this.value='';}" />
				<input type="submit" value="" class="button" />
			</form>
		</div>
	</div>
	<!-- /#header -->
	<div id="contents">
		<div id="background">
			<table align="center"
				style="height: 920px; background-color: #ffffff;">
				<tr>
					<td></td>
					<td width="280" valign="top"><%@include
							file="leftside-links.jsp"%></td>
					<td></td>
					<td width="720" valign="top" style="padding-right: 10px;">
						<div class="divrightmain">
							<h3 style="color: #003a75;">
								<spring:message code="label.signup.page.header" />
							</h3>
							<form
								action="${pageContext.request.contextPath}/adduser?${_csrf.parameterName}=${_csrf.token}"
								method="post" id="contactusform"
								onsubmit="return validate(this)">
								<table style="font-size: 9pt; text-align: left;">
									<tr>
										<td colspan="2">
											<div class="whyusmain">
												<spring:message code="label.signup.page.form.note" />
											</div>
										</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>${message}</td>
										<td></td>
									</tr>
									<tr>
										<td style="width: 150px;"><spring:message
												code="label.signup.page.form.field1" /></td>
										<td><input name="company" type="text" id="company"
											size="32" style="width: 200px;" /></td>
										<td><div id='company_result'
											style='text-align: left; '></div></td>
									</tr>
									<tr>
										<td style="width: 150px;"><spring:message
												code="label.signup.page.form.field2" /></td>
										<td><input name="fname" type="text" id="fname" size="32"
											style="width: 200px;" /></td>
										<td><div id='fname_result'
											style='text-align: left; '></div></td>
									</tr>
									<tr>
										<td style="width: 150px;"><spring:message
												code="label.signup.page.form.field2.1" /></td>
										<td><input name="lname" type="text" id="lname" size="32"
											style="width: 200px;" /></td>
										<td><div id='lname_result'
											style='text-align: left; '></div></td>										
									</tr>
									<tr>
										<td style="width: 150px;"><spring:message
												code="label.signup.page.form.field3" /></td>
										<td><input name="address" type="text" id="address"
											size="32" style="width: 200px;" /></td>
										<td><div id='address_result'
											style='text-align: left; '></div></td>
									</tr>
									<tr>
										<td style="width: 150px;"><spring:message
												code="label.signup.page.form.field4" /></td>
										<td><%@include file="countryOptions.jsp"%></td>
									</tr>
									<tr>
										<td style="width: 150px;"><spring:message
												code="label.signup.page.form.field5" /></td>
										<td><input name="province" type="text" id="province"
											size="32" style="width: 200px;" /></td>
										<td><div id='province_result'
											style='text-align: left; '></div></td>
									</tr>
									<tr>
										<td style="width: 150px;"><spring:message
												code="label.signup.page.form.field6" /></td>
										<td><input name="zip" type="text" id="zip" size="10" /></td>
										<td><div id='zip_result'
											style='text-align: left; '></div></td>
									</tr>
									<tr>
										<td style="width: 150px;"><spring:message
												code="label.signup.page.form.field7" /></td>
										<td><input name="phone" type="text" id="phone" size="32"
											style="width: 200px;" /></td>
										<td><div id='phone_result'
											style='text-align: left;'></div></td>
									</tr>
									<tr>
										<td style="width: 150px;"><spring:message
												code="label.signup.page.form.field8" /></td>
										<td><input name="email" type="text" id="email" size="32"
											style="width: 200px;" /></td>
										<td style='text-align: left;'><div id='email_result'
											style='text-align: left;'></div></td>
									</tr>
									<tr style="height: 5px;">
										<td style="width: 150px;"><spring:message
												code="label.signup.page.form.field7.1" /></td>
										<td><input name="username" type="text" id="username"
											size="32" style="width: 200px;" /></td>
										<td style='text-align: left;'>
											<div id='username_result'
											style='text-align: left;'></div></td>
									</tr>
									<tr>
										<td style="width: 150px;"><spring:message
												code="label.signup.page.form.field7.2" /></td>
										<td><input name="password" type="password" id="password"
											size="32" style="width: 200px;" /></td>
										<td><div id='password_result'
											style='text-align: left;'></div></td>
									</tr>
									<tr>
										<td style="width: 150px;"><spring:message
												code="label.signup.page.form.field9" /></td>
										<td><select name="preferred">
												<option><spring:message
														code="label.signup.page.form.field9.option1" /></option>
												<option><spring:message
														code="label.signup.page.form.field9.option2" /></option>
												<option selected="selected"><spring:message
														code="label.signup.page.form.field9.option3" /></option>
										</select></td>
										<td></td>
									</tr>
									<tr>
										<td style="width: 150px;"><spring:message
												code="label.signup.page.form.field10" /></td>
										<td><input type="radio" name="stage"
											value="<spring:message code="label.signup.page.form.field10.option1" />"
											checked /> <spring:message
												code="label.signup.page.form.field10.option1" /><br /> <input
											type="radio" name="stage"
											value="<spring:message code="label.signup.page.form.field10.option2" />" />
											<spring:message code="label.signup.page.form.field10.option2" /><br />
											<input type="radio" name="stage"
											value="<spring:message code="label.signup.page.form.field10.option3" />" />
											<spring:message code="label.signup.page.form.field10.option3" /></td>
										<td></td>
									</tr>
									<tr>
										<td style="width: 150px;"><spring:message
												code="label.signup.page.form.field11" /></td>
										<td><input name="software" type="text" id="software"
											size="32" style="width: 200px;" /></td>
										<td><div id='software_result'
											style='text-align: left; '></div></td>
									</tr>
									<tr>
										<td style="width: 150px;"><spring:message
												code="label.signup.page.form.field12" /></td>
										<td><label> <input type="radio" name="frequency"
												value="<spring:message code="label.signup.page.form.field12.option1" />"
												id="RadioGroup2_0" checked /> <spring:message
													code="label.signup.page.form.field12.option1" />
										</label> <label> <input type="radio" name="frequency"
												value="<spring:message code="label.signup.page.form.field12.option2" />"
												id="RadioGroup2_1" /> <spring:message
													code="label.signup.page.form.field12.option2" /><br />
										</label> <label> <input type="radio" name="frequency"
												value="<spring:message code="label.signup.page.form.field12.option3" />"
												id="RadioGroup2_2" /> <spring:message
													code="label.signup.page.form.field12.option3" />
										</label> <label> &nbsp; &nbsp; <input type="radio"
												name="frequency"
												value="<spring:message code="label.signup.page.form.field12.option4" />" id="RadioGroup2_3" />
												<spring:message
													code="label.signup.page.form.field12.option4" />
										</label></td>
										<td></td>
									</tr>
									<tr>
										<td style="width: 150px;"><spring:message
												code="label.signup.page.form.field13" /></td>
										<td><textarea name="about" id="about" cols="40" rows="5"
												style="width: 200px;"
												onkeyup="textCounter(this,'counter',350);"></textarea><span
											style="color: red;"></span> <input disabled maxlength="3"
											size="3" value="350" id="counter"> <script>
												function textCounter(field,
														field2, maxlimit) {
													var countfield = document
															.getElementById(field2);
													if (field.value.length > maxlimit) {
														field.value = field.value
																.substring(0,
																		maxlimit);
														return false;
													} else {
														countfield.value = maxlimit
																- field.value.length;
													}
												}
											</script></td>
										<td><div id='about_result'
											style='text-align: left; '></div></td>
									</tr>
									<tr>
										<td></td>
										<td colspan="2">
											<div class="g-recaptcha"
												data-sitekey="6LftYwUTAAAAAIF9rxzS1GnyjnYcd8xFk5sbjVYx"></div>
										</td>
									</tr>
									<tr>
										<td></td>
										<td style="text-align: left;" colspan="2"><input type="submit"
											value="<spring:message
												code="label.signup.page.submit" />"
											name="submit" /></td>
									</tr>
								</table>
							</form>

						</div>
					</td>
				</tr>
			</table>

		</div>
	</div>
	<!-- /#contents -->

	<div id="footer">
		<ul class="contacts">
			<h3>
				<spring:message code="label.menu.contactus" />
			</h3>
			<li><span><spring:message code="label.contactus.email" /></span>
				<p>
					:
					<spring:message code="label.contactus.email.address.value" />
				</p></li>
			<li><span><spring:message code="label.contactus.address" /></span>
				<p>
					:
					<spring:message code="label.contactus.address.value" />
				</p></li>
			<li><span><spring:message code="label.contactus.phone" /></span>
				<p>
					:
					<spring:message code="label.contactus.phone.value" />
				</p></li>
		</ul>
		<ul id="connect">
			<h3>
				<spring:message code="label.getupdated.header" />
			</h3>
			<li><a href=""><spring:message code="label.getupdated.blog" /></a></li>
			<li><a href="" target="_blank">Facebook</a></li>
			<li><a href="" target="_blank">Twitter</a></li>
		</ul>
		<div id="newsletter">
			<p>
				<b><spring:message code="label.newsletter.signup.header" /></b>
				<spring:message code="label.newsletter.signup.details" />
			</p>
			<form action="" method="">
				<input type="text" value="Name" class="txtfield"
					onblur="javascript:if(this.value==''){this.value=this.defaultValue;}"
					onfocus="javascript:if(this.value==this.defaultValue){this.value='';}" />
				<input type="text" value="Enter Email Address" class="txtfield"
					onblur="javascript:if(this.value==''){this.value=this.defaultValue;}"
					onfocus="javascript:if(this.value==this.defaultValue){this.value='';}" />
				<input type="submit" value="" class="button" />
			</form>
		</div>
		<span class="footnote"><spring:message
				code="label.copyright.statement" /></span> <span class="footnote"><spring:message
				code="label.designed.by.statement" /></span>
	</div>
	<!-- /#footer -->
</body>
</html>