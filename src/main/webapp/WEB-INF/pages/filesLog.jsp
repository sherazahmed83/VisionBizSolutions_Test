<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<sec:csrfMetaTags />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/filesLogPage.css"
	type="text/css" />

</head>
<body>
	<br />
	<div class="divrightmain">
		<h3 style="color: #003a75;">
			<spring:message code="label.filesLog.page.header" />
		</h3>
		<br />
		<c:if test="${empty filesSet}">
			<spring:message code="label.filesLog.page.no.files.log.available.msg" />
		</c:if>
		<table id="files">
			<c:if test="${not empty filesSet}">
				<thead>
					<tr>
						<th><spring:message
								code="label.filesLog.page.table.header.col1.name" /></th>
						<th><spring:message
								code="label.filesLog.page.table.header.col2.name" /></th>
						<th><spring:message
								code="label.filesLog.page.table.header.col3.name" /></th>
						<th><spring:message
								code="label.filesLog.page.table.header.col4.name" /></th>
					</tr>
				</thead>
			</c:if>
			<tbody>
				<c:forEach items="${filesSet}" var="file" varStatus="status">
					<tr>
						<td><c:out value="${file.fileName}" /></td>
						<td><c:out value="${file.fileType}" /></td>
						<td><fmt:formatNumber type="number" maxFractionDigits="5"
								value="${file.fileSize / 1000}" /> KB</td>
						<td><jsp:useBean id="uploadedDate" class="java.util.Date" />
							<jsp:setProperty name="uploadedDate" property="time"
								value="${file.created.time}" /> <fmt:formatDate
								pattern="yyyy-MM-dd HH:mm" value="${uploadedDate}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>


</body>
</html>