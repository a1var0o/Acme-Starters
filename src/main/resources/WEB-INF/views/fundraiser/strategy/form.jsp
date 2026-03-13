<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="fundraiser.strategy.list.label.ticker" path="ticker"/>
	<acme:form-textbox code="fundraiser.strategy.list.label.name" path="name"/>
	<acme:form-textarea code="fundraiser.strategy.list.label.description" path="description"/>
	<acme:form-moment code="fundraiser.strategy.list.label.startMoment" path="startMoment"/>
	<acme:form-moment code="fundraiser.strategy.list.label.endMoment" path="endMoment"/>
	<acme:form-checkbox code="fundraiser.strategy.list.label.draftMode" path="draftMode"/>
	
	<acme:button code="fundraiser.strategy.form.button.tactics" action="/fundraiser/tactic/list?strategyId=${id}"/>
</acme:form>