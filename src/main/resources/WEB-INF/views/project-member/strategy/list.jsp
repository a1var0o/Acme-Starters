<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list navigable="true">
	<acme:list-column code="project-member.strategy.list.label.ticker" path="ticker"/>
	<acme:list-column code="project-member.strategy.list.label.name" path="name"/>
	<acme:list-column code="project-member.strategy.list.label.startMoment" path="startMoment"/>
	<acme:list-column code="project-member.strategy.list.label.endMoment" path="endMoment"/>
</acme:list>

<acme:button code="project-member.strategy.list.button.return" action="/project-member/project/show?id=${projectId}"/>
