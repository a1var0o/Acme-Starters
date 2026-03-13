<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list navigable="true">
	<acme:list-column code="any.milestone.list.label.name" path="title" width="10%"/>
	<acme:list-column code="any.milestone.list.label.description" path="achievements" width="50%"/>
	<acme:list-column code="any.milestone.list.label.effort" path="effort" width="10%"/>
	<acme:list-column code="any.milestone.list.label.kind" path="kind" width="10%"/>
</acme:list>

