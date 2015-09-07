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
				<li class="first" class="selected"><a
					href="${pageContext.request.contextPath}/"><spring:message
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
			<table align="center" height="920" bgcolor="#ffffff">
				<tr>
					<td></td>
					<td width="280" valign="top"><%@include
							file="leftside-links.jsp"%></td>
					<td></td>
					<td width="720" valign="top" style="padding-right: 10px;">
						<div class="divrightmain">
							<h2 style="color: #003a75;">
								<spring:message code="label.verificationSuccess.page.header" />
							</h2>
							<div class="whyusmain">
								<div class="whyus" style="text-align: left;">
									<spring:message code="label.verificationSuccess.page.para1" />
									<br /> <br /><p style="text-align: center;">  <strong><span class="thankyou"><a
											href="${pageContext.request.contextPath}/" class="thankyou">Continue</a></span></strong></p>
								</div>
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