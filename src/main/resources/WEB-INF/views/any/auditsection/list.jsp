<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list navigable="true">
	<acme:list-column code="any.auditsection.list.label.name" path="name" width="10%"/>
	<acme:list-column code="any.auditsection.list.label.description" path="notes" width="50%"/>
	<acme:list-column code="any.auditsection.list.label.hours" path="hours" width="10%"/>
	<acme:list-column code="any.auditsection.list.label.kind" path="kind" width="10%"/>
</acme:list>

