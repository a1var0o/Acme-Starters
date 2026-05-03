<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list navigable="true">
	<acme:list-column code="project-member.campaign.list.label.ticker" path="ticker"/>
	<acme:list-column code="project-member.campaign.list.label.name" path="name"/>
	<acme:list-column code="project-member.campaign.list.label.startMoment" path="startMoment"/>
	<acme:list-column code="project-member.campaign.list.label.endMoment" path="endMoment"/>
</acme:list>

<acme:button code="project-member.campaign.list.button.return" action="/project-member/project/show?id=${projectId}"/>
