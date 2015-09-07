<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>

<!DOCTYPE html>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><spring:message code="label.page.title" /></title>
<link href="${pageContext.request.contextPath}/resources/css/style.css"
	rel="stylesheet" type="text/css" />
<script
	src="${pageContext.request.contextPath}/resources/js/contactus.js"></script>
<script src="https://www.google.com/recaptcha/api.js"></script>
<!--[if lte IE 7]>
		<link rel="stylesheet" href="css/ie.css" type="text/css" charset="utf-8" />	
	<![endif]-->
</head>

<body>
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
				<li class="selected"><a
					href="${pageContext.request.contextPath}/contact"><spring:message
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
			<table align="center" height="920" bgcolor="#ffffff">
				<tr>
					<td></td>
					<td width="280" valign="top"><%@include
							file="leftside-links.jsp"%></td>
					<td></td>
					<td width="720" valign="top" style="padding-right: 10px;">
						<div class="divrightmain">
							<h3 style="color: #003a75;">
								<spring:message code="label.contact.page.header" />
							</h3>
							<div class="whyusmain">
								<div class="whyus" style="font-size: 10pt;">
									<table>
										<tr>
											<td style="width: 100px; text-align: left;" colspan="2"><spring:message
													code="label.contact.page.moreinfo.text" /></td>
										</tr>
										<tr>
											<td style="width: 100px; vertical-align: top;"><spring:message
													code="label.contact.page.address" /></td>
											<td><spring:message
													code="label.contact.page.address.value" /></td>
										</tr>
										<tr>
											<td style="width: 100px;"><spring:message
													code="label.contact.page.phone" /></td>
											<td><spring:message
													code="label.contact.page.phone.value" /></td>
										</tr>
										<tr>
											<td style="width: 100px;"><spring:message
													code="label.contact.page.fax" /></td>
											<td><spring:message code="label.contact.page.fax.value" /></td>
										</tr>
										<tr>
											<td style="width: 100px;">
												<div class="whyusmain" style="width: 60px;">
													<div class="whyus">
														<spring:message code="label.contact.page.emailus" />
													</div>
												</div>
											</td>
											<td>
												<div class="whyusmain" style="width: 130px">
													<div class="whyus">
														<a style="text-decoration: none;"
															href="<spring:message code="label.contact.page.emailus.mailto" />"><spring:message
																code="label.contact.page.emailus.value" /></a>
													</div>
												</div>
											</td>
										</tr>
									</table>
								</div>
							</div>
							<!-- End of <div class="whyusmain"> -->
							<br /> <br />
							<div class="formdivhr">
								<div class="hr">
									<hr />
								</div>
							</div>
							<div>
								<a id="enquiry"></a>
							</div>
							<form action="${pageContext.request.contextPath}/requestEnquiry?${_csrf.parameterName}=${_csrf.token}" method="post" 
								 id="contactusform"
								onsubmit="return validate(this)">
								<table style="font-size: 9pt; text-align: left;">
									<tr>
										<td colspan="2">
											<div class="whyusmain">
												<div class="whyusheading"
													style="font-size: 10pt; font-weight: bold;">
													<spring:message code="label.contact.page.message.text" />
												</div>
												<spring:message code="label.contact.page.form.note" />
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
												code="label.contact.page.form.field1" /></td>
										<td><input name="company" type="text" id="company"
											size="32" style="width: 200px;" /></td>
									</tr>
									<tr>
										<td style="width: 150px;"><spring:message
												code="label.contact.page.form.field2" /></td>
										<td><input name="name" type="text" id="name" size="32"
											style="width: 200px;" /><span style="color: red;">*</span></td>
									</tr>
									<tr>
										<td style="width: 150px;"><spring:message
												code="label.contact.page.form.field3" /></td>
										<td><input name="address" type="text" id="address"
											size="32" style="width: 200px;" /><span style="color: red;">*</span></td>
									</tr>
									<tr>
										<td style="width: 150px;"><spring:message
												code="label.contact.page.form.field4" /></td>
										<td><%@include file="countryOptions.jsp"%></td>
									</tr>
									<tr>
										<td style="width: 150px;"><spring:message
												code="label.contact.page.form.field5" /></td>
										<td><input name="province" type="text" id="province"
											size="32" style="width: 200px;" /><span style="color: red;">*</span></td>
									</tr>
									<tr>
										<td style="width: 150px;"><spring:message
												code="label.contact.page.form.field6" /></td>
										<td><input name="zip" type="text" id="zip" size="10" /></td>
									</tr>
									<tr>
										<td style="width: 150px;"><spring:message
												code="label.contact.page.form.field7" /></td>
										<td><input name="phone" type="text" id="phone" size="32"
											style="width: 200px;" /><span style="color: red;">*</span></td>
									</tr>
									<tr>
										<td style="width: 150px;"><spring:message
												code="label.contact.page.form.field8" /></td>
										<td><input name="email" type="text" id="email" size="32"
											style="width: 200px;" /><span style="color: red;">*</span></td>
									</tr>
									<tr>
										<td style="width: 150px;"><spring:message
												code="label.contact.page.form.field9" /></td>
										<td><select name="preferred">
												<option><spring:message
														code="label.contact.page.form.field9.option1" /></option>
												<option><spring:message
														code="label.contact.page.form.field9.option2" /></option>
												<option selected="selected"><spring:message
														code="label.contact.page.form.field9.option3" /></option>
										</select></td>
									</tr>
									<tr>
										<td style="width: 150px;"><spring:message
												code="label.contact.page.form.field10" /></td>
										<td><input type="radio" name="stage"
											value="Gathering info" /> <spring:message
												code="label.contact.page.form.field10.option1" /><br /> <input
											type="radio" name="stage" value="Decided to outsource" /> <spring:message
												code="label.contact.page.form.field10.option2" /><br /> <input
											type="radio" name="stage" value="Ready right now" /> <spring:message
												code="label.contact.page.form.field10.option3" /></td>
									</tr>
									<tr>
										<td style="width: 150px;"><spring:message
												code="label.contact.page.form.field11" /></td>
										<td><input name="software" type="text" id="software"
											size="32" style="width: 200px;" /><span style="color: red;">*</span></td>
									</tr>
									<tr>
										<td style="width: 150px;"><spring:message
												code="label.contact.page.form.field12" /></td>
										<td><label> <input type="radio" name="frequency"
												value="Real-time" id="RadioGroup2_0" /> <spring:message
													code="label.contact.page.form.field12.option1" />
										</label> <label> <input type="radio" name="frequency"
												value="Daily Overnight" id="RadioGroup2_1" /> <spring:message
													code="label.contact.page.form.field12.option2" /><br />
										</label> <label> <input type="radio" name="frequency"
												value="Weekly" id="RadioGroup2_2" /> <spring:message
													code="label.contact.page.form.field12.option3" />
										</label> <label> &nbsp; &nbsp; <input type="radio"
												name="frequency" value="Monthly" id="RadioGroup2_3" /> <spring:message
													code="label.contact.page.form.field12.option4" />
										</label></td>
									</tr>
									<tr>
										<td style="width: 150px;"><spring:message
												code="label.contact.page.form.field13" /></td>
										<td><textarea name="about" id="about" cols="40" rows="5"
												style="width: 200px;"
												onkeyup="textCounter(this,'counter',350);"></textarea>
										<span
											style="color: red;">*</span> <input disabled maxlength="3"
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
											</script>
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
										<td><input type="submit" value="Submit" name="submit" />
											<input id="Reset" type="reset" value="Reset" /></td>
										<!-- onclick="return checkmail(this.form.email)" -->
									</tr>
								</table>
							</form>
							<!-- End of right side div -->
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