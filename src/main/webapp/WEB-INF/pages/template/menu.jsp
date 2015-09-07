<%@ 
taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<ul style="list-style: none; line-height: 28px;">

	<li><spring:url value="/welcome" var="profileUrl"
			htmlEscape="true" /> <a href="${profileUrl}"
		style="text-decoration: none;"><spring:message code="label.menu.page.profile.menu.name" /></a></li>

	<li><spring:url value="/dashboard/changePassword" var="changePasswordUrl"
			htmlEscape="true" /> <a href="${changePasswordUrl}"
		style="text-decoration: none;"><spring:message code="label.menu.page.change.password.menu.name" /></a></li>

	<li><spring:url value="/dashboard/uploadFilePage" var="uploadFileUrl"
			htmlEscape="true" /> <a href="${uploadFileUrl}"
		style="text-decoration: none;"><spring:message code="label.menu.page.upload.file.menu.name" /></a></li>
	<li>
	<li><spring:url value="/dashboard/getFilesLog" var="uploadFileUrl"
			htmlEscape="true" /> <a href="${uploadFileUrl}"
		style="text-decoration: none;"><spring:message code="label.menu.page.log.files.uploaded.menu.name" /></a></li>
	<li>
		<!-- For login user --> <c:url value="/logout" var="logoutUrl" />
		<form action="${logoutUrl}" method="post" id="logoutForm">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form> <script>
			function formSubmit() {
				document.getElementById("logoutForm").submit();
			}
		</script> <c:if test="${pageContext.request.userPrincipal.name != null}">
			<a href="javascript:formSubmit()" style="text-decoration: none;"><spring:message code="label.menu.page.logout.menu.name" /></a>
		</c:if>
	</li>
</ul>
