<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list navigable = "true">
	<acme:list-column code="any.tactic.list.label.name" path="name" width="20%"/>
	<acme:list-column code="any.tactic.list.label.notes" path="notes" width="20%"/>
	<acme:list-column code="any.tactic.list.label.expectedPercentage" path="expectedPercentage" width="20%"/>
	<acme:list-column code="any.tactic.list.label.kind" path="Kind" width="20%"/>
	<acme:list-column code="any.strategy.list.label.strategy" path="strategy" width="20%"/>
</acme:list>