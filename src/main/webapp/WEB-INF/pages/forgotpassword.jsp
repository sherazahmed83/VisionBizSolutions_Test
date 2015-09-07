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
<script src="https://www.google.com/recaptcha/api.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<!--[if lte IE 7]>
		<link rel="stylesheet" href="css/ie.css" type="text/css" charset="utf-8" />	
	<![endif]-->
	
<script>

function validate(form) {
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
}

function IsValidEmail(e) {
	var re = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+.[A-Z]{2,4}$/i;
	return re.test(e);
}

	$(document)
		.ready(
			function() {
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
								
							} else if ($('#email').val().length > 45) {
								$('#email_result')
								.html(
										'<span style="color: #ff0000;"> Email cannot be more then 45 characters</span>');
							} else if (!result) {
								$('#email_result')
										.html(
												'<span style="color: #ff0000;"> Email must be a valid email, e.g. <strong>someemail@mail.com</strong></span>');
							} else {
								$('#email_result')
								.html('');
							}
						});			
			}
		);
</script>
</head>

<body onload='document.loginForm.username.focus();'>
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
							${pageContext.request.userPrincipal.name} | <a style="color: #ffffff;"
							href="javascript:formSubmit()"> Logout</a>
						</span>
					</c:if>
				</sec:authorize> 
				<c:if test="${pageContext.request.userPrincipal.name == null}">
					<span style="font-size: 10pt; color: #ffffff;"> Welcome guest | <a style="color: #ffffff;"
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
				<li><a href="${pageContext.request.contextPath}/support"><spring:message
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
								<spring:message code="label.forgotpassword.page.header" />
							</h3>
							<div id="login-box" style="width: 450px;">
							<span
									style="text-align: left; font-size: 13pt; font-weight: bold; padding-bottom: 30px;"><spring:message
										code="label.forgotpassword.page.subheader" /></span> <span>&nbsp;</span>
								<span><br /></span>
								<span><br /></span>
								<span
									style="text-align: left; font-size: 10pt; padding-bottom: 30px;"><spring:message
										code="label.forgotpassword.page.para1" /></span> <span>&nbsp;</span>
								<span>&nbsp;</span>
								<span><br /></span>
								<form action="${pageContext.request.contextPath}/processforgotpassword" 
									method="POST" onsubmit="return validate(this)"
								 id="contactusform">

									<table style="width: 600px;">
										<tr>
											<td>&nbsp;
											</td>
											<td>&nbsp;
											</td>
										</tr>
										<tr>
											<td style="text-align: left; font-size: 10pt; width: 120px;"><spring:message code="label.forgotpassword.page.email" /></td>
											<td><input type="text" name="email" id="email" size="32" style="width: 200px;" ><!-- /td>
											<td style='text-align: left;'--><div id='email_result'
											style='text-align: left; font-size: 9pt;'></div></td>
											
										</tr>
										<tr>
											<td>&nbsp;
											</td>
											<td>&nbsp;
											</td>
											
										</tr>										
										<tr>
											<td>&nbsp;
											</td>
											<td style="text-align: left; font-size: 10pt;"><spring:message code="label.forgotpassword.page.captacha" />
											</td>
											
										</tr>
									<tr>
										<td></td>
										<td>
											<div class="g-recaptcha"
												data-sitekey="6LftYwUTAAAAAIF9rxzS1GnyjnYcd8xFk5sbjVYx"></div>
										</td>
										
									</tr>
										<tr>
											<td></td>
											<td><input name="submit" type="submit"
												value="<spring:message code="label.forgotpassword.page.submit" />" /></td>
											
										</tr>
									</table>
									<input type="hidden" name="${_csrf.parameterName}"
										value="${_csrf.token}" />
								</form>
							</div>
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