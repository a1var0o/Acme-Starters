<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="auditor.auditsection.list.label.name" path="name"/>
	<acme:form-textbox code="auditor.auditsection.list.label.notes" path="notes"/>
	<acme:form-double code="auditor.auditsection.list.label.hours" path="hours"/>
	<acme:form-textbox code="auditor.auditsection.list.label.kind" path="kind" />
</acme:form>