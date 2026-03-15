<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list navigable = "true">
	<acme:list-column code="auditor.auditsection.list.label.name" path="name" width="25%"/>
	<acme:list-column code="auditor.auditsection.list.label.notes" path="notes" width="25%"/>
	<acme:list-column code="auditor.auditsection.list.label.hours" path="hours" width="25%"/>
	<acme:list-column code="auditor.auditsection.list.label.kind" path="kind" width="25%"/>
</acme:list>