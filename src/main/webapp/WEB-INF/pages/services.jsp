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
	src="${pageContext.request.contextPath}/resources/js/twisty2b.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/eHlpDhtm.js"></script>
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
				<li class="selected"><a
					href="${pageContext.request.contextPath}/services"><spring:message
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
						<div class="divrightmain" id="container">

							<h3 style="color: #003a75;">
								<spring:message code="label.services.page.header" />
							</h3>

							<div class="whyusmain">
								<div class="whyus">
									<spring:message code="label.services.page.para1" />
								</div>
							</div>

							<div class="whyusmain">
								<p>
									<a class="whyusheading" href="javascript:TextPopup(this)"
										id="a1" onclick="JavaScript:swapImages(this, dropdown1)"
										onfocus="this.blur()"><img
										src="${pageContext.request.contextPath}/resources/js/images1/arrowright.gif"
										name="dropdown1" alt="" id="dropdown1" style="border: none;"
										width="10" height="9" border="0" /> Accounts Receivable</a>
								</p>
								<div id="POPUP184786756" class="whyus" style="display: none;">
									<%@include file="accountsReceivable.jsp"%>
								</div>
								<br />
								<!-----------------Accounts Payable----------------------------------->
								<p>
									<a class="whyusheading" href="javascript:TextPopup(this)"
										id="a2" onclick="JavaScript:swapImages(this, dropdown2)"
										onfocus="this.blur()"><img
										src="${pageContext.request.contextPath}/resources/js/images1/arrowright.gif"
										name="dropdown2" alt="" id="dropdown2" style="border: none;"
										width="10" height="9" border="0" /> Accounts Payable</a>
								</p>
								<div id="POPUP184774211" class="whyus" style="display: none;">
									<%@include file="accountsPayable.jsp"%>
								</div>
								<br />
								<!-----------------Bank Reconciliation----------------------------------->
								<p>
									<a class="whyusheading" href="javascript:TextPopup(this)"
										id="a3" onclick="JavaScript:swapImages(this, dropdown3)"
										onfocus="this.blur()"><img
										src="${pageContext.request.contextPath}/resources/js/images1/arrowright.gif"
										name="dropdown3" alt="" id="dropdown3" style="border: none;"
										width="10" height="9" border="0" /> Bank Reconciliation</a>
								</p>
								<div id="POPUP184759235" class="whyus" style="display: none;">
									<%@include file="bankReconciliation.jsp"%>
								</div>
								<br />
								<!-----------------Payroll Administration----------------------------------->
								<p>
									<a class="whyusheading" href="javascript:TextPopup(this)"
										id="a4" onclick="JavaScript:swapImages(this, dropdown4)"
										onfocus="this.blur()"><img
										src="${pageContext.request.contextPath}/resources/js/images1/arrowright.gif"
										name="dropdown4" alt="" id="dropdown4" style="border: none;"
										width="10" height="9" border="0" /> Payroll Administration</a>
								</p>

								<div id="POPUP184786692" class="whyus" style="display: none;">
									<%@include file="payrollAdministration.jsp"%>
								</div>
								<br />
								<!-----------------General Ledger----------------------------------->
								<p>
									<a class="whyusheading" href="javascript:TextPopup(this)"
										id="a5" onclick="JavaScript:swapImages(this, dropdown5)"
										onfocus="this.blur()"><img
										src="${pageContext.request.contextPath}/resources/js/images1/arrowright.gif"
										name="dropdown5" alt="" id="dropdown5" style="border: none;"
										width="10" height="9" border="0" /> General Ledger</a>
								</p>

								<div id="POPUP184786660" class="whyus" style="display: none;">
									<%@include file="generalLedger.jsp"%>
								</div>
								<br />
								<!-----------------Inventory Reconciliation----------------------------------->
								<p>
									<a class="whyusheading" href="javascript:TextPopup(this)"
										id="a6" onclick="JavaScript:swapImages(this, dropdown6)"
										onfocus="this.blur()"><img
										src="${pageContext.request.contextPath}/resources/js/images1/arrowright.gif"
										name="dropdown6" alt="" id="dropdown6" style="border: none;"
										width="10" height="9" border="0" /> Inventory Reconciliation</a>
								</p>
								<div id="POPUP184781160" class="whyus" style="display: none;">
									<%@include file="inventoryReconciliation.jsp"%>
								</div>
								<br />
								<!-----------------Budgeting----------------------------------->
								<p>
									<a class="whyusheading" href="javascript:TextPopup(this)"
										id="a7" onclick="JavaScript:swapImages(this, dropdown7)"
										onfocus="this.blur()"><img
										src="${pageContext.request.contextPath}/resources/js/images1/arrowright.gif"
										name="dropdown7" alt="" id="dropdown7" style="border: none;"
										width="10" height="9" border="0" /> Budgeting</a>
								</p>

								<div id="POPUP184789211" class="whyus" style="display: none;">
									<%@include file="budgeting.jsp"%>
								</div>
								<br />
								<!-----------------Cash Management----------------------------------->
								<p>
									<a class="whyusheading" href="javascript:TextPopup(this)"
										id="a8" onclick="JavaScript:swapImages(this, dropdown8)"
										onfocus="this.blur()"><img
										src="${pageContext.request.contextPath}/resources/js/images1/arrowright.gif"
										name="dropdown8" alt="" id="dropdown8" style="border: none;"
										width="10" height="9" border="0" /> Cash Management</a>
								</p>

								<div id="POPUP184764327" class="whyus" style="display: none;">
									<%@include file="cashManagement.jsp"%>
								</div>

								<br />
								<!-----------------Financial Reporting----------------------------------->
								<p>
									<a class="whyusheading" href="javascript:TextPopup(this)"
										id="a9" onclick="JavaScript:swapImages(this, dropdown9)"
										onfocus="this.blur()"><img
										src="${pageContext.request.contextPath}/resources/js/images1/arrowright.gif"
										name="dropdown9" alt="" id="dropdown9" style="border: none;"
										width="10" height="9" border="0" /> Financial Reporting</a>
								</p>

								<div id="POPUP184709758" class="whyus" style="display: none;">
									<%@include file="financialReporting.jsp"%>
								</div>
							</div>
							<br />
							<?rh-script_start ?>
							<script type="text/javascript">
								//<![CDATA[
								if (typeof (TextPopupInit) != 'function')
									TextPopupInit = new Function();
								TextPopupInit('a1', 'POPUP184786756');
								TextPopupInit('a2', 'POPUP184774211');
								TextPopupInit('a3', 'POPUP184759235');
								TextPopupInit('a4', 'POPUP184786692');
								TextPopupInit('a5', 'POPUP184786660');
								TextPopupInit('a6', 'POPUP184781160');
								TextPopupInit('a7', 'POPUP184789211');
								TextPopupInit('a8', 'POPUP184764327');
								TextPopupInit('a9', 'POPUP184709758');
								//]]>
							</script>
							<?rh-script_end ?>
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