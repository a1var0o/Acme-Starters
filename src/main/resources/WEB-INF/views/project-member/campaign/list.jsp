<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<jstl:choose>
	<jstl:when test="${_command == 'list'}">
		<acme:list navigable="true">
			<acme:list-column code="project-member.campaign.list.label.ticker" path="ticker"/>
			<acme:list-column code="project-member.campaign.list.label.name" path="name"/>
			<acme:list-column code="project-member.campaign.list.label.startMoment" path="startMoment"/>
			<acme:list-column code="project-member.campaign.list.label.endMoment" path="endMoment"/>
		</acme:list>
	</jstl:when>
</jstl:choose>
