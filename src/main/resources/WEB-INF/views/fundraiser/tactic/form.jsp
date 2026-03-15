<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="fundraiser.tactic.list.label.name" path="name"/>
	<acme:form-textbox code="fundraiser.tactic.list.label.notes" path="notes"/>
	<acme:form-double code="fundraiser.tactic.list.label.expectedPercentage" path="expectedPercentage"/>
	<acme:form-textbox code="fundraiser.tactic.list.label.kind" path="kind" />
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="fundraiser.tactic.form.button.update" action="/fundraiser/tactic/update"/>
			<acme:submit code="fundraiser.tactic.form.button.delete" action="/fundraiser/tactic/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="fundraiser.tactic.form.button.create" action="/fundraiser/tactic/create?strategyId=${strategyId}"/>
		</jstl:when>		
	</jstl:choose>	
</acme:form>