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

	$(document).ready(
			function() {
			$('#success_user_Update_Message').hide();
			$('#error_user_Update_Message').hide();

			var isError = true;
			$('#oldPassword')
			.focusout(
					function() {
						var re = /^([A-Za-z0-9]{8,20})$/;
						var result = re.test($('#oldPassword')
								.val());

						//run the character number check  
						if ($('#oldPassword').val().length == 0) {
							$('#password_result')
									.html(
											'<span style="color: #ff0000;"> Old Password is required and must consists of atleast 8 characters</span>');
							isError = true;
							$('#oldPassword').focus();
						} else if ($('#oldPassword').val().length > 20) {
							$('#password_result')
							.html(
									'<span style="color: #ff0000;"> Old Password cannot be more then 20 characters</span>');
							isError = true;
							$('#oldPassword').focus();
						} else if (!result) {
							$('#password_result')
									.html(
											'<span style="color: #ff0000;"> Old Password can only contain alphabets and numerics, must have atleast 8 characters</span>');
							isError = true;
							$('#oldPassword').focus();
						} else {
							isError = false;
							$('#password_result').html('');
						}
					});

			$('#newPassword')
					.focusout(
							function() {
								var re = /^([A-Za-z0-9]{8,20})$/;
								var result = re.test($('#newPassword')
										.val());

								//run the character number check  
								if ($('#newPassword').val().length == 0) {
									$('#newPassword_result')
											.html(
													'<span style="color: #ff0000;"> New Password is required and must consists of atleast 8 characters</span>');
									isError = true;
									$('#newPassword').focus();
								} else if ($('#newPassword').val().length > 20) {
									$('#newPassword_result')
									.html(
											'<span style="color: #ff0000;"> New Password cannot be more then 20 characters</span>');
									isError = true;
									$('#newPassword').focus();
								} else if (!result) {
									$('#newPassword_result')
											.html(
													'<span style="color: #ff0000;"> New Password can only contain alphabets and numerics, must have atleast 8 characters</span>');
									isError = true;
									$('#newPassword').focus();
								} else if ($('#oldPassword').val() === $('#newPassword').val()) {
									$('#newPassword_result')
									.html(
											'<span style="color: #ff0000;"> Old password and new Password must be different </span>');
									isError = true;
									$('#newPassword').focus();
								} else {
									isError = false;
									$('#newPassword_result').html('');
								}
							});

			$('#confirmPassword')
					.focusout(
							function() {
								var re = /^([A-Za-z0-9]{8,20})$/;
								var result = re.test($('#confirmPassword')
										.val());
								var newPassword = $('#newPassword').val;
								var confirmPassword = $('#confirmPassword').val;
								var comparison_result = (newPassword == confirmPassword);

								//run the character number check  
								if ($('#confirmPassword').val().length == 0) {
									$('#confirmPassword_result')
											.html(
													'<span style="color: #ff0000;"> This password field is also required and must consists of atleast 8 characters</span>');
									isError = true;
									$('#confirmPassword').focus();
								} else if ($('#confirmPassword').val().length > 20) {
									$('#confirmPassword_result')
									.html(
											'<span style="color: #ff0000;"> This password field cannot be more then 20 characters</span>');
									isError = true;
									$('#confirmPassword').focus();
								} else if (!result) {
									$('#confirmPassword_result')
											.html(
													'<span style="color: #ff0000;"> This password field can only contain alphabets and numerics, must have atleast 8 characters</span>');
									isError = true;
									$('#confirmPassword').focus();
								} else if (!comparison_result) {									
									$('#confirmPassword_result')
									.html(
											'<span style="color: #ff0000;"> Both password fields must have same password</span>');
									isError = true;
									$('#confirmPassword').focus();
								} else {
									isError = false;
									$('#confirmPassword_result').html('');
								}
							});


			/*function to update password*/
			$('#passwordSaveform')
					.submit(
							function(event) {
								if(!isError) {
								var url = $('#passwordSaveform')
										.attr("action");
								//get all the attributes  
								var oldPassword = $('#oldPassword').val();
								var newPassword = $('#newPassword').val();
								
								var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
								var csrfHeader = $("meta[name='_csrf_header']").attr("content");
								var csrfToken = $("meta[name='_csrf']").attr("content");

								var json = {
									"oldPassword" : oldPassword,
									"newPassword" : newPassword,
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
											$('#success_user_Update_Message').show();
											$('#success_user_Update_Message').html(
													'Your new password has been updated.');
											$('#oldPassword').val("");
											$('#newPassword').val("");
											$('#confirmPassword').val("");
											setTimeout(function() {
												   $('#success_user_Update_Message').fadeOut('slow');												   
												}, 3000);
										} else {  
											$('#error_user_Update_Message').show();
											$('#error_user_Update_Message').html(
													'Sorry! your new password has not been updated. Either you didn\'t provided the correct old password or something gone bad at server-side. Please try again later!');
											setTimeout(function() {
												   $('#error_user_Update_Message').fadeOut('slow');												   
												}, 5000);
										}
									},
									error: function(XMLHttpRequest, textStatus, errorThrown) {
										$('#error_user_Update_Message').show();
										$('#error_user_Update_Message').html(
												'Sorry! password has not been updated. Your session is timed-out' 
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
<body>
	<br />
	<div class="divrightmain">
		<h3 style="color: #003a75;">
			<spring:message code="label.changePassword.page.header" />
		</h3>
		<div id="success_user_Update_Message" class="success_user_profile_update" style="width: 270px;"></div>
		<br/>
		<div id="error_user_Update_Message" class="error_user_profile_update" style="width: 600px; height: 40px;"></div>
		<form action="${pageContext.request.contextPath}/dashboard/updatepassword?${_csrf.parameterName}=${_csrf.token}&token=${DASHBOARD-AUTH-TOKEN}"
			method="post" id="passwordSaveform">
			<table style="font-size: 9pt; text-align: left;">
				<tr>
					<td style="width: 150px;"><spring:message
							code="label.changePassword.page.old.password" /></td>
					<td><input name="oldPassword"
							type="password" id="oldPassword" size="32" style="width: 200px;" /></td>
					<td><div id='password_result'
							style='text-align: left; vertical-align: middle;'></div></td>
				</tr>
				<tr style="height: 5px;">
					<td style="width: 150px;"><spring:message
							code="label.changePassword.page.new.password" /></td>
					<td><input name="newPassword"
							type="password" id="newPassword" size="32" style="width: 200px;" /></td>
					<td><div id='newPassword_result'
							style='text-align: left; vertical-align: middle;'></div></td>							
				</tr>
				<tr style="height: 5px;">
					<td style="width: 150px;"><spring:message
							code="label.changePassword.page.new.password.confirm" /></td>
					<td><input name="confirmPassword"
							type="password" id="confirmPassword" size="32" style="width: 200px;" /></td>
					<td><div id='confirmPassword_result'
							style='text-align: left; vertical-align: middle;'></div></td>
				</tr>
				<tr>
					<td></td>
					<td colspan="2"><input type="submit"
						value="<spring:message
												code="label.changePassword.page.submit" />"
						name="submit" /></td>
				</tr>
			</table>
		</form>

	</div>


</body>
</html>