<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.tactic.list.label.name" path="name"/>
	<acme:form-textbox code="any.tactic.list.label.notes" path="notes"/>
	<acme:form-double code="any.tactic.list.label.expectedPercentage" path="expectedPercentage"/>
	<acme:form-textbox code="any.tactic.list.label.kind" path="kind" />
</acme:form>