<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="inventor.part.list.label.name" path="name"/>
	<acme:form-textarea code="inventor.part.list.label.description" path="description"/>
	<acme:form-money code="inventor.part.list.label.cost" path="cost"/>
	<acme:form-textbox code="inventor.part.list.label.kind" path="kind"/>
</acme:form>