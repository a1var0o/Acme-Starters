<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.campaign.list.label.name" path="name"/>
	<acme:form-textarea code="any.campaign.list.label.description" path="description"/>
	<acme:form-moment code="any.campaign.list.label.startMoment" path="startMoment"/>
	<acme:form-moment code="any.campaign.list.label.endMoment" path="endMoment"/>
	<acme:button code="any.campaign.list.label.spokesperson" action="/any/spokesperson/show?id=${id}"/>
	<acme:button code="any.campaign.list.label.milestones" action="/any/milestone/list?id=${id}"/>
</acme:form>