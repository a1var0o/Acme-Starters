<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list navigable="true">
	<acme:list-column code="inventor.part.list.label.name" path="name" width="10%"/>
	<acme:list-column code="inventor.part.list.label.description" path="description" width="50%"/>
	<acme:list-column code="inventor.part.list.label.cost" path="cost" width="10%"/>
	<acme:list-column code="inventor.part.list.label.kind" path="kind" width="10%"/>
</acme:list>

<jstl:if test="${showCreate}">
	<acme:button code="inventor.part.list.button.create" action="/inventor/part/create?inventionId=${inventionId}"/>
</jstl:if>