<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.strategy.list.label.ticker" path="ticker"/>
	<acme:form-textbox code="any.strategy.list.label.name" path="name"/>
	<acme:form-textarea code="any.strategy.list.label.description" path="description"/>
	<acme:form-moment code="any.strategy.list.label.startMoment" path="startMoment"/>
	<acme:form-moment code="any.strategy.list.label.endMoment" path="endMoment"/>
	
	<acme:button code="any.strategy.form.button.tactics" action="/any/tactic/list?strategyId=${id}"/>
	<acme:button code="any.strategy.form.button.fundraiser" action="/any/fundraiser/show?strategyId=${id}"/>
</acme:form>