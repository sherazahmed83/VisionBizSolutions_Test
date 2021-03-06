<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<sec:authorize access="hasRole('ROLE_USER')">
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>User Area</title>
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/resources/css/usercss/screen.css"
		type="text/css" media="screen, projection"></link>
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/resources/css/usercss/print.css"
		type="text/css" media="print"></link>
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/resources/css/style.css"
		type="text/css" />

	<!--[if IE]>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/usercss/ie.css" type="text/css" media="screen, projection">
	<![endif]-->
	<style>
body {
	margin-top: 20px;
	margin-bottom: 20px;
	background-color: #DFDFDF;
}
</style>
</head>
<body>
	<div class="container"
		style="border: #C1C1C1 solid 1px; border-radius: 10px;">
		<!-- Header -->
		<tiles:insertAttribute name="header" />
		<!-- Menu Page -->
		<div class="span-5  border"
			style="height: 920px; background-color: #FCFCFC;">
			<tiles:insertAttribute name="menu" />
		</div>
		<!-- Body Page -->
		<div class="span-19 last">
			<tiles:insertAttribute name="body" />
		</div>
		<!-- Footer Page -->
		<tiles:insertAttribute name="footer" />
	</div>
</body>
	</html>
</sec:authorize>