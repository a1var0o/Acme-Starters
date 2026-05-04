<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<jstl:if test="${projectDraftMode}">
	<acme:button code="project-member.invention.list.button.add" action="/project-member/invention/list-available?projectId=${projectId}"/>
	<br><br>
</jstl:if>



<jstl:choose>	 
		<jstl:when test="${_command == 'list'}">
			<acme:list navigable="true">
				<acme:list-column code="project-member.invention.list.label.ticker" path="ticker"/>
				<acme:list-column code="project-member.invention.list.label.name" path="name"/>
				<acme:list-column code="project-member.invention.list.label.startMoment" path="startMoment"/>
				<acme:list-column code="project-member.invention.list.label.endMoment" path="endMoment"/>
			</acme:list>
			<acme:button code="project-member.invention.list.button.return" action="/project-member/project/show?id=${projectId}"/>	
		</jstl:when>
		<jstl:when test="${_command == 'list-available'}">
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
							<tr onclick="window.location.href='project-member/invention/show?id=${item.id}&projectId=${projectId}'" style="cursor: pointer;" class="table-hover">
								<td><jstl:out value="${item.ticker}" /></td>
								<td><jstl:out value="${item.name}" /></td>
								<td><jstl:out value="${item.startMoment}" /></td>
								<td><jstl:out value="${item.startMoment}" /></td>
								<td><acme:submit code="project-member.invention.list.button.assign" action="/project-member/invention/assign" /></td>
							</tr>
						</jstl:forEach>
					</tbody>
				</table>
				<acme:button code="project-member.invention.list.button.return" action="/project-member/invention/list?projectId=${projectId}" />
		</jstl:when>		
	</jstl:choose>


