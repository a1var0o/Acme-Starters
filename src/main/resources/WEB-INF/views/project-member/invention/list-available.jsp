<%@page%>

	<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
		<%@taglib prefix="acme" uri="http://acme-framework.org/" %>
			<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

				<table class="table table-sm">
					<thead>
						<tr>
							<th><spring:message code="project-member.invention.list.label.ticker" /></th>
							<th><spring:message code="project-member.invention.list.label.name" /></th>
							<th><spring:message code="project-member.invention.list.label.startMoment" /></th>
							<th><spring:message code="project-member.invention.list.label.endMoment" /></th>
						</tr>
					</thead>
					<tbody>
						<jstl:forEach items="${inventions}" var="item">
							<tr onclick="window.location.href='project-member/invention/show?id=${item.id}&projectId=${projectId}'" style="cursor: pointer;" class="table-hover">
								<td><jstl:out value="${item.ticker}" /></td>
								<td><jstl:out value="${item.name}" /></td>
								<td><jstl:out value="${item.startMoment}" /></td>
								<td><jstl:out value="${item.endMoment}" /></td>
							</tr>
						</jstl:forEach>
					</tbody>
				</table>

				<acme:button code="project-member.invention.list.button.return" action="/project-member/invention/list?projectId=${projectId}" />