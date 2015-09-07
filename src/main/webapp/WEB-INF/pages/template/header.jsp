<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="span-24">
	<table>
		<tbody>
			<tr>
				<td><a href="${pageContext.request.contextPath}/"><img
						src="${pageContext.request.contextPath}/resources/images/logo-user1.jpg"
						style="padding-top: 10px; width: 190px;" /></a></td>
				<td
					style="font-size: 30pt; text-align: center; vertical-align: middle; width: 560px; font-weight: bold;">
					<spring:message code="label.user.header.page.header" />
				</td>
				<td
					style="font-size: 11pt; text-align: right; vertical-align: top; width: 200px;">
					<c:if test="${pageContext.request.userPrincipal.name != null}">			
							Welcome ${pageContext.request.userPrincipal.name}
					</c:if>
				</td>
			</tr>
		</tbody>
	</table>
	<img
		src="${pageContext.request.contextPath}/resources/images/auth_user_header.jpg"
		style="width: 950px;" />
</div>