<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<jstl:if test="${projectDraftMode}">
	<acme:button code="project-member.invention.list.button.add" action="/project-member/invention/list-available?projectId=${projectId}"/>
	<br><br>
</jstl:if>

<acme:list navigable="true">
	<acme:list-column code="project-member.invention.list.label.ticker" path="ticker"/>
	<acme:list-column code="project-member.invention.list.label.name" path="name"/>
	<acme:list-column code="project-member.invention.list.label.startMoment" path="startMoment"/>
	<acme:list-column code="project-member.invention.list.label.endMoment" path="endMoment"/>
</acme:list>

<acme:button code="project-member.invention.list.button.return" action="/project-member/project/show?id=${projectId}"/>
