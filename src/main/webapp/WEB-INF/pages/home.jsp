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
				<li class="first selected"><a
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

	<div id="adbox">
		<div class="body">
			<div class="images">
				<img
					src="${pageContext.request.contextPath}/resources/images/discussing2.jpg"
					alt="Img" class="preview" /> <img
					src="${pageContext.request.contextPath}/resources/images/shake-hands.jpg"
					alt="Img" height="144" width="230px" /> <img
					src="${pageContext.request.contextPath}/resources/images/professionals.jpg"
					alt="Img" height="135" width="230px" class="last" />
			</div>
			<div class="details">
				<p>
					<span style="text-align: left; font-size: 15px;"> <spring:message
							code="label.index.page.initials" /> <a
						href="${pageContext.request.contextPath}/about"><spring:message
								code="label.aboutus.more" /></a>
					</span> <br />
					<spring:message code="label.index.page.second.para.initials" />
					<a href="${pageContext.request.contextPath}/about"><spring:message
							code="label.aboutus.see.details" /></a>
				</p>
			</div>
		</div>
		<div class="footer">
			<ul>
				<li class="selected"><a href="index.html"><img
						src="${pageContext.request.contextPath}/resources/images/meeting2.jpg"
						alt="Img" /></a>
					<p>
						<a href="index.jsp"><spring:message
								code="label.index.page.first.info.header" /></a><br />
						<spring:message code="label.index.page.first.info.details" />
					</p></li>
				<li style="width: 300px;"><a href="index.html"><img
						src="${pageContext.request.contextPath}/resources/images/flags2.jpg"
						alt="Img" /></a>
					<p>
						<a href="index.jsp"><spring:message
								code="label.index.page.second.info.header" /></a><br />
						<spring:message code="label.index.page.second.info.details" />
					</p></li>
				<li style="width: 300px;"><a href="index.html"><img
						src="${pageContext.request.contextPath}/resources/images/boys.jpg"
						alt="Img" /></a>
					<p>
						<a href="index.jsp"><spring:message
								code="label.index.page.third.info.header" /></a><br />
						<spring:message code="label.index.page.third.info.details" />
					</p></li>
			</ul>
			<span class="bottom-shadow"></span>
		</div>
	</div>
	<!-- /#adbox -->
	<div id="contents">
		<div class="body">
			<div id="sidebar">
				<h3>
					<spring:message code="label.getupdated.blog" />
				</h3>
				<ul>
					<li><spring:message code="label.blog1" /> <span>Apr.
							1, 2015 | by Zeeshan Ahmed <a href="">8</a>
					</span></li>
					<li><spring:message code="label.blog2" /> <span>Dec.
							21, 2014 | by Zeeshan Ahmed <a href="">8</a>
					</span></li>
					<li><spring:message code="label.blog3" /> <span>Nov.
							11 2014 | by Zeeshan Ahmed <a href="">8</a>
					</span></li>
					<li><spring:message code="label.blog4" /> <span>Sept.
							18, 2014 | by<a style="background: none;"
							href="mailto:webmaster@visionbizsolutions.com">Webmaster</a>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="">8</a>
					</span></li>
				</ul>
			</div>
			<div id="main">
				<span><spring:message code="label.index.page.aim.statement" /></span>
				<ul>
					<li><a href=""><img
							src="${pageContext.request.contextPath}/resources/images/globe.jpg"
							alt="Img" />
						<h3>
								<spring:message code="label.index.page.global.header" />
							</h3></a>
						<p>
							<spring:message code="label.index.page.global.details" />
						</p></li>
					<li><a href=""><img
							src="${pageContext.request.contextPath}/resources/images/tools.jpg"
							alt="Img" />
						<h3>
								<spring:message code="label.index.page.services.header" />
							</h3></a>
						<p>
							<spring:message code="label.index.page.services.details" />
						</p></li>
					<li><a href=""><img
							src="${pageContext.request.contextPath}/resources/images/check.jpg"
							alt="Img" />
						<h3>
								<spring:message code="label.index.page.security.header" />
							</h3></a>
						<p>
							<spring:message code="label.index.page.security.details" />
						</p></li>
					<li><a href=""><img
							src="${pageContext.request.contextPath}/resources/images/graph.jpg"
							alt="Img" />
						<h3>
								<spring:message code="label.index.page.outsourcing.header" />
							</h3></a>
						<p>
							<spring:message code="label.index.page.outsourcing.details" />
						</p></li>
				</ul>
				<br /> <br /> <br /> <br /> <br />
				<p>
					<spring:message code="label.index.page.outsourcing.details.bottom" />
				</p>
			</div>
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