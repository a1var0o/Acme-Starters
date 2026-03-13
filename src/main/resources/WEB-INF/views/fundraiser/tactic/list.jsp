<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list navigable = "true">
	<acme:list-column code="fundraiser.tactic.list.label.name" path="name" width="25%"/>
	<acme:list-column code="fundraiser.tactic.list.label.notes" path="notes" width="25%"/>
	<acme:list-column code="fundraiser.tactic.list.label.expectedPercentage" path="expectedPercentage" width="25%"/>
	<acme:list-column code="fundraiser.tactic.list.label.kind" path="kind" width="25%"/>
</acme:list>