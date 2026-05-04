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
							<th><spring:message code="project-member.invention.list.label.h" /></th>
						</tr>
					</thead>
					<tbody>
						<jstl:forEach items="${inventions}" var="item">
							<tr onclick="window.location.href='show?id=${item.id}'" style="cursor: pointer;" class="table-hover">
								<td><jstl:out value="${item.ticker}" /></td>
								<td><jstl:out value="${item.name}" /></td>
								<td><jstl:out value="${item.startMoment}" /></td>
								<td><jstl:out value="${item.startMoment}" /></td>
							</tr>
						</jstl:forEach>
					</tbody>
				</table>

				<acme:button code="project-member.invention.list.button.return" action="/project-member/invention/list?projectId=${projectId}" />