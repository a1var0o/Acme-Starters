<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jstl:if test="${projectDraftMode && canAdd}">
	<acme:button code="project-member.campaign.list.button.add"
		action="/project-member/campaign/list-available?projectId=${projectId}" />
	<br><br>
</jstl:if>

<jstl:if test="${_command == 'list-available'}">
	<div class="table-responsive">
		<table class="table table-striped table-hover">
			<thead class="thead-dark">
				<tr>
					<th><spring:message code="project-member.campaign.list.label.ticker" /></th>
					<th><spring:message code="project-member.campaign.list.label.name" /></th>
					<th><spring:message code="project-member.campaign.list.label.startMoment" /></th>
					<th><spring:message code="project-member.campaign.list.label.endMoment" /></th>
				</tr>
			</thead>
			<tbody>
				<jstl:forEach items="${campaigns}" var="campaign">
					<tr style="cursor: pointer;" onclick="window.location.href='project-member/campaign/show?id=${campaign.id}&projectId=${projectId}'">
						<td><jstl:out value="${campaign.ticker}" /></td>
						<td><jstl:out value="${campaign.name}" /></td>
						<td><jstl:out value="${campaign.startMoment}" /></td>
						<td><jstl:out value="${campaign.endMoment}" /></td>
					</tr>
				</jstl:forEach>
			</tbody>
		</table>
	</div>
</jstl:if>

<jstl:choose>
	<jstl:when test="${_command == 'list' || _command == 'assign'}">
		<acme:list navigable="true">
			<acme:list-column code="project-member.campaign.list.label.ticker" path="ticker"/>
			<acme:list-column code="project-member.campaign.list.label.name" path="name"/>
			<acme:list-column code="project-member.campaign.list.label.startMoment" path="startMoment"/>
			<acme:list-column code="project-member.campaign.list.label.endMoment" path="endMoment"/>
		</acme:list>
	</jstl:when>
</jstl:choose>
