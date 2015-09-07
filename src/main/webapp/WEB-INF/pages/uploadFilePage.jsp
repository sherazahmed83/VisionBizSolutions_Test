<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<sec:csrfMetaTags />
<script
	src="${pageContext.request.contextPath}/resources/css/fileupload/js/jquery.1.9.1.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/css/fileupload/js/vendor/jquery.ui.widget.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/css/fileupload/js/jquery.iframe-transport.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/css/fileupload/js/jquery.fileupload.js"></script>

<!-- bootstrap just to have good looking page -->
<script
	src="${pageContext.request.contextPath}/resources/css/fileupload/bootstrap/js/bootstrap.min.js"></script>
<link
	href="${pageContext.request.contextPath}/resources/css/fileupload/bootstrap/css/bootstrap.css"
	type="text/css" rel="stylesheet" />

<!-- we code these -->
<link
	href="${pageContext.request.contextPath}/resources/css/fileupload/css/dropzone.css"
	type="text/css" rel="stylesheet" />
<script
	src="${pageContext.request.contextPath}/resources/css/fileupload/js/myuploadfunction.js"></script>


<script>
	$(document).ready(function() {
		$('#success_user_Update_Message').hide();
		$('#error_user_Update_Message').hide();
	});
</script>

</head>
<body>
	<br />
	<div class="divrightmain">
		<h3 style="color: #003a75;">
			<spring:message code="label.uploadFilePage.page.header" />
		</h3>
		<span style="color: #ff0000; font:9pt;"><spring:message code="label.uploadFilePage.page.file.types.allowed.note" /></span>
		<br/>
		<div id="success_user_Update_Message"
			class="success_user_profile_update" style="width: 600px; height: 40px;"></div>
		<br />
		<div id="error_user_Update_Message" class="error_user_profile_update"
			style="width: 600px; height: 40px;"></div>
		<table style="font-size: 9pt; text-align: left;">
			<tr>
				<td><input id="fileupload" type="file" name="files[]" 
					data-url="${pageContext.request.contextPath}/dashboard/upload.json?${_csrf.parameterName}=${_csrf.token}&token=${DASHBOARD-AUTH-TOKEN}"></td>
			</tr>
			<tr>
				<td>
					<div id="dropzone" class="fade well"></div>
					<div id="progress" class="progress">
						<div class="bar" style="width: 0%;"></div>
					</div>
				</td>
			</tr>
		</table>
	</div>


</body>
</html>