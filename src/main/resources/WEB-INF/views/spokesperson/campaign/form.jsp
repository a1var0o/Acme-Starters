<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="spokesperson.campaign.list.label.ticker" path="ticker"/>
	<acme:form-textbox code="spokesperson.campaign.list.label.name" path="name"/>
	<acme:form-textarea code="spokesperson.campaign.list.label.description" path="description"/>
	<acme:form-moment code="spokesperson.campaign.list.label.startMoment" path="startMoment"/>
	<acme:form-moment code="spokesperson.campaign.list.label.endMoment" path="endMoment"/>
	<acme:form-checkbox code="spokesperson.campaign.list.label.draftMode" path="draftMode"/>
	
	<acme:button code="spokesperson.campaign.form.button.milestones" action="/spokesperson/milestone/list?campaignId=${id}"/>
</acme:form>