<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.milestone.list.label.name" path="title"/>
	<acme:form-textarea code="any.milestone.list.label.description" path="achievements"/>
	<acme:form-money code="any.milestone.list.label.effort" path="effort"/>
	<acme:form-textbox code="any.milestone.list.label.kind" path="kind"/>
</acme:form>