<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.audit-section.list.label.name" path="name"/>
	<acme:form-textarea code="any.audit-section.list.label.description" path="notes"/>
	<acme:form-textbox code="any.audit-section.list.label.hours" path="hours"/>
	<acme:form-textbox code="any.audit-section.list.label.kind" path="kind"/>
</acme:form>