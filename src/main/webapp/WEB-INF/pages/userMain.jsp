<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<sec:csrfMetaTags />
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script>
	function disableFields() {
		document.getElementById("username").disabled = true;
		document.getElementById("email").disabled = true;
	}

	$(document).ready(
			function() {
			$('#success_user_Update_Message').hide();
			$('#error_user_Update_Message').hide();

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
							$('#company').focus();
						} else if ($('#company').val().length > 50) {
							$('#company_result')
							.html(
									'<span style="color: #ff0000;"> Company Name cannot be more then 50 characters</span>');
							isError = true;
							$('#company').focus();
						} else if (!result) {
							$('#company_result')
									.html(
											'<span style="color: #ff0000;"> Company Name can only contain alphabets and dot(.) and must have atleast 4 characters</span>');
							isError = true;
							$('#company').focus();
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
									$('#fname').focus();
								} else if ($('#fname').val().length > 20) {
									$('#fname_result')
									.html(
											'<span style="color: #ff0000;"> First Name cannot be more then 20 characters</span>');
									isError = true;
									$('#fname').focus();
								} else if (!result) {
									$('#fname_result')
											.html(
													'<span style="color: #ff0000;"> First Name can only contain alphabets and must have atleast 3 characters</span>');
									isError = true;
									$('#fname').focus();
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
									$('#lname').focus();
								} else if ($('#lname').val().length > 20) {
									$('#lname_result')
									.html(
											'<span style="color: #ff0000;"> Last Name cannot be more then 20 characters</span>');
									isError = true;
									$('#lname').focus();
								} else if (!result) {
									$('#lname_result')
											.html(
													'<span style="color: #ff0000;"> Last Name can only contain alphabets and must have atleast 3 characters.</span>');
									isError = true;
									$('#lname').focus();
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
									$('#address').focus();
								} else if ($('#address').val().length > 150) {
									$('#address_result')
									.html(
											'<span style="color: #ff0000;"> Address cannot be more then 150 characters</span>');
									isError = true;
									$('#address').focus();
								} else if (!result) {
									$('#address_result')
									.html(
											'<span style="color: #ff0000;"> Address can only contain alphabets and numeric characters, dash(-), dot(.), number-sign(#) and must have atleast 10 characters</span>');
									isError = true;
									$('#address').focus();
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
									$('#province').focus();
								} else if ($('#province').val().length > 40) {
									$('#province_result')
									.html(
											'<span style="color: #ff0000;"> Province cannot be more then 40 characters</span>');
									isError = true;
									$('#province').focus();
								} else if (!result) {
									$('#province_result')
											.html(
													'<span style="color: #ff0000;"> Province can only contain alphabets and must have atleast 4 characters</span>');
									isError = true;
									$('#province').focus();
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
									$('#zip').focus();
								} else if ($('#zip').val().length > 7) {
									$('#zip_result')
									.html(
											'<span style="color: #ff0000;"> Zip cannot be more then 7 digits</span>');
									isError = true;
									$('#zip').focus();
								} else if (isNaN($('#zip').val())) {
									$('#zip_result')
											.html(
													'<span style="color: #ff0000;"> Zip can only contain numbers or digits</span>');
									isError = true;
									$('#zip').focus();
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
									$('#phone').focus();
								} else if ($('#phone').val().length > 15) {
									$('#phone_result')
									.html(
											'<span style="color: #ff0000;"> Contact No. cannot be more then 15 digits</span>');
									isError = true;
									$('#phone').focus();
								} else if (isNaN($('#phone').val())) {
									$('#phone_result')
											.html(
													'<span style="color: #ff0000;"> Contact No. can only contain numbers or digits</span>');
									isError = true;
									$('#phone').focus();
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
									$('#software').focus();
								} else if ($('#software').val().length > 100) {
									$('#software_result')
									.html(
											'<span style="color: #ff0000;"> Accounting Software cannot be more then 100 digits</span>');
									isError = true;
									$('#software').focus();
								} else if (!result) {
									$('#software_result')
									.html(
											'<span style="color: #ff0000;"> Accounting Software can only contain alphabets, dash(-) and dot(.) and must have atleast 2 characters</span>');
									isError = true;
									$('#software').focus();
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
									$('#about').focus();
								} else if (!result) {
									$('#about_result')
									.html(
											'<span style="color: #ff0000;"> This field can only contain alphabets and numeric characters, dot(.) and dash(-) and must have atleast 30 characters</span>');
									isError = true;
									$('#about').focus();
								} else {
									isError = false;
									$('#about_result').html('');
								}
							});

			/*function to save user data*/
			$('#profileSaveform')
					.submit(
							function(event) {
								if(!isError) {
								var url = $('#profileSaveform')
										.attr("action");
								//get all the attributes  
								var company = $('#company').val();
								var fName = $('#fname').val();
								var lName = $('#lname').val();
								var address = $('#address').val();
								var country = $('#country').val();
								var province = $('#province').val();
								var zip = $('#zip').val();
								var phone = $('#phone').val();
								var email = $('#email').val();
								
								var username = $('#username').val();											
								var preferred = $('#preferred')
										.val();
								var stage = $('input[name="stage"]:checked', '#profileSaveform').val();//$('#stage').val();
								var software = $('#software').val();
								var frequency = $('input[name="frequency"]:checked', '#profileSaveform').val();//$('#frequency').val();
								var about = $('#about').val();

								var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
								var csrfHeader = $("meta[name='_csrf_header']").attr("content");
								var csrfToken = $("meta[name='_csrf']").attr("content");

								var json = {
									"company" : company,
									"fName" : fName,
									"lName" : lName,
									"address" : address,
									"country" : country,
									"province" : province,
									"zip" : zip,
									"phone" : phone,
									"email" : email,
									"username" : username,
									"preferred" : preferred,
									"stage" : stage,
									"software" : software,
									"frequency" : frequency,
									"about" : about
								};
								
								$.ajax({
									dataType: "text",
									url : url,
									data : JSON.stringify(json),
									type : "POST",
									beforeSend : function(xhr) {
										xhr.setRequestHeader("Accept", "application/json");
										xhr.setRequestHeader("Content-Type", "application/json");
										xhr.setRequestHeader(csrfHeader, csrfToken);
									},
									success : function(result) {									
										
										if (result == 'true') { 
											$('#success_user_Update_Message').show();
											$('#success_user_Update_Message').html(
													'Your profile has been updated.');
											setTimeout(function() {
												   $('#success_user_Update_Message').fadeOut('slow');												   
												}, 3000);
										} else {											
												$('#error_user_Update_Message').show();
												$('#error_user_Update_Message').html(
														'Sorry! your profile has not been updated. ' 
														+ result
														+ ' Please try again later or login again.');
												setTimeout(function() {
													   $('#error_user_Update_Message').fadeOut('slow');												   
													}, 5000);
											
										}
									},
									error: function(XMLHttpRequest, textStatus, errorThrown) {
										$('#error_user_Update_Message').show();
										$('#error_user_Update_Message').html(
												'Sorry! your profile has not been updated. Your session is timed-out' 
												+ ' or something wrong happend on server side.'
												+ ' <strong>Please login again to proceed.</strong>');
										setTimeout(function() {
											   $('#error_user_Update_Message').fadeOut('slow');												   
											}, 8000);

								    }
								});
								}// end if for error
								event.preventDefault();	
							});
							
					});
</script>
</head>
<body onload="disableFields();">
	<br />
	<div class="divrightmain">
		<h3 style="color: #003a75;">
			<spring:message code="label.userMain.page.header" />
		</h3>
		<div id="success_user_Update_Message" class="success_user_profile_update"></div>
		<br/>
		<div id="error_user_Update_Message" class="error_user_profile_update"  style="width: 600px; height: 40px;"></div>
		<form:form action="${pageContext.request.contextPath}/dashboard/updateuser/?token=${DASHBOARD-AUTH-TOKEN}"
			method="post" id="profileSaveform" onsubmit="return validate(this)"
			modelAttribute="command">
			<table style="font-size: 9pt; text-align: left;">
				<tr>
					<td style="width: 150px;"><spring:message
							code="label.userMain.page.form.field1" /></td>
					<td><form:input path="company" name="company"
							type="text" id="company" size="32" style="width: 200px;" /></td>
					<td><div id='company_result'
							style='text-align: left; vertical-align: middle;'></div></td>
				</tr>
				<tr>
					<td style="width: 150px;"><spring:message
							code="label.userMain.page.form.field2" /></td>
					<td><form:input path="fname" name="fname" type="text"
							id="fname" size="32" style="width: 200px;" /></td>
					<td><div id='fname_result'
							style='text-align: left; vertical-align: middle;'></div></td>
				</tr>
				<tr>
					<td style="width: 150px;"><spring:message
							code="label.userMain.page.form.field2.1" /></td>
					<td><form:input path="lname" name="lname" type="text"
							id="lname" size="32" style="width: 200px;" /></td>
					<td><div id='lname_result'
							style='text-align: left; vertical-align: middle;'></div></td>
				</tr>
				<tr>
					<td style="width: 150px;"><spring:message
							code="label.userMain.page.form.field3" /></td>
					<td><form:input path="address" name="address" type="text"
							id="address" size="32" style="width: 200px;" /></td>
					<td><div id='address_result'
							style='text-align: left; vertical-align: middle;'></div></td>
				</tr>
				<tr>
					<td style="width: 150px;"><spring:message
							code="label.userMain.page.form.field4" /></td>
					<td colspan="2"><form:select path="country" name="country"
							id="country" style="width: 206px;" items="${countriesMap}">
						</form:select></td>
				</tr>
				<tr>
					<td style="width: 150px;"><spring:message
							code="label.userMain.page.form.field5" /></td>
					<td><form:input path="province" name="province" type="text"
							id="province" size="32" style="width: 200px;" /></td>
					<td><div id='province_result'
							style='text-align: left; vertical-align: middle;'></div></td>
				</tr>
				<tr>
					<td style="width: 150px;"><spring:message
							code="label.userMain.page.form.field6" /></td>
					<td><form:input path="zip" name="zip" type="text" id="zip"
							size="10" /></td>
					<td><div id='zip_result'
							style='text-align: left; vertical-align: middle;'></div></td>
				</tr>
				<tr>
					<td style="width: 150px;"><spring:message
							code="label.userMain.page.form.field7" /></td>
					<td><form:input path="phone" name="phone" type="text"
							id="phone" size="32" style="width: 200px;" /></td>
					<td><div id='phone_result'
							style='text-align: left; vertical-align: middle;'></div></td>
				</tr>
				<tr>
					<td style="width: 150px;"><spring:message
							code="label.userMain.page.form.field8" /></td>
					<td colspan="2"><form:input path="email" name="email"
							type="text" id="email" size="32" style="width: 200px;"
							readonly="readonly" /></td>
				</tr>
				<tr style="height: 5px;">
					<td style="width: 150px;"><spring:message
							code="label.userMain.page.form.field7.1" /></td>
					<td colspan="2"><form:input path="username" name="username"
							type="text" id="username" size="32" style="width: 200px;"
							readonly="readonly" /></td>
				</tr>
				<tr>
					<td style="width: 150px;"><spring:message
							code="label.userMain.page.form.field9" /></td>
					<td colspan="2"><spring:message
							code="label.userMain.page.form.field9.option1" var="i18nEmail" />
						<spring:message code="label.userMain.page.form.field9.option2"
							var="i18nPhone" /> <spring:message
							code="label.userMain.page.form.field9.option3" var="i18nBoth" /> <form:select
							path="preferred" name="preferred">
							<form:option value="${i18nEmail}">
								<spring:message code="label.userMain.page.form.field9.option1" />
							</form:option>
							<form:option value="${i18nPhone}">
								<spring:message code="label.userMain.page.form.field9.option2" />
							</form:option>
							<form:option selected="selected" value="${i18nBoth}">
								<spring:message code="label.userMain.page.form.field9.option3" />
							</form:option>
						</form:select></td>
				</tr>
				<tr>
					<td style="width: 150px;"><spring:message
							code="label.userMain.page.form.field10" /></td>
					<td colspan="2"><spring:message
							code="label.userMain.page.form.field10.option1" var="i18nOption1" />
						<spring:message code="label.userMain.page.form.field10.option2"
							var="i18nOption2" /> <spring:message
							code="label.userMain.page.form.field10.option3" var="i18nOption3" />
						<form:radiobutton path="stage" name="stage" value="${i18nOption1}" />
						<spring:message code="label.userMain.page.form.field10.option1" /><br />
						<form:radiobutton path="stage" name="stage" value="${i18nOption2}" />
						<spring:message code="label.userMain.page.form.field10.option2" /><br />
						<form:radiobutton path="stage" name="stage" value="${i18nOption3}" />
						<spring:message code="label.userMain.page.form.field10.option3" /></td>
				</tr>
				<tr>
					<td style="width: 150px;"><spring:message
							code="label.userMain.page.form.field11" /></td>
					<td><form:input path="software" name="software" type="text"
							id="software" size="32" style="width: 200px;" /></td>
					<td><div id='software_result'
							style='text-align: left; vertical-align: middle;'></div></td>
				</tr>
				<tr>
					<td style="width: 150px;"><spring:message
							code="label.userMain.page.form.field12" /></td>
					<td colspan="2"><spring:message
							code="label.userMain.page.form.field12.option1" var="i18nR2Option1" />
						<spring:message code="label.userMain.page.form.field12.option2"
							var="i18nR2Option2" /> <spring:message
							code="label.userMain.page.form.field12.option3" var="i18nR2Option3" />
						<spring:message code="label.userMain.page.form.field12.option4"
							var="i18nR2Option4" /> <label> <form:radiobutton
								path="frequency" name="frequency" value="${i18nR2Option1}"
								id="RadioGroup2_0" /> <spring:message
								code="label.userMain.page.form.field12.option1" />
					</label> <label> <form:radiobutton path="frequency"
								name="frequency" value="${i18nR2Option2}" id="RadioGroup2_1" />
							<spring:message code="label.userMain.page.form.field12.option2" /><br />
					</label> <label> <form:radiobutton path="frequency"
								name="frequency" value="${i18nR2Option3}" id="RadioGroup2_2" />
							<spring:message code="label.userMain.page.form.field12.option3" />
					</label> <label> &nbsp; &nbsp; <form:radiobutton path="frequency"
								name="frequency" value="${i18nR2Option4}" id="RadioGroup2_3" />
							<spring:message code="label.userMain.page.form.field12.option4" />
					</label></td>
				</tr>
				<tr>
					<td style="width: 150px;"><spring:message
							code="label.userMain.page.form.field13" /></td>
					<td><form:textarea path="about" name="about" id="about"
							cols="40" rows="5" style="width: 200px;"
							onkeyup="textCounter(this,'counter',350);" />
							
							<c:if test="${not empty command.about}">
							<input disabled
								maxlength="3" size="3" value="<c:out value="${350 - command.about.length()}" />" id="counter" />
							</c:if>
							
							<c:if test="${empty command.about}">
							<input disabled
								maxlength="3" size="3" value="350" id="counter" />
							</c:if>
							
							 <script>
							function textCounter(field, field2, maxlimit) {
								var countfield = document
										.getElementById(field2);
								if (field.value.length > maxlimit) {
									field.value = field.value.substring(0,
											maxlimit);
									return false;
								} else {
									countfield.value = maxlimit
											- field.value.length;
								}
							}
						</script></td>
					<td><div id='about_result'
							style='text-align: left; vertical-align: middle;'></div></td>
				</tr>
				<tr>
					<td></td>
					<td colspan="2"><input type="submit" value="<spring:message code="label.userMain.page.submit" />"
						name="submit" /></td>
				</tr>
			</table>
		</form:form>

	</div>


</body>
</html>