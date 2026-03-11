<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="spokesperson.milestone.list.label.title" path="title"/>
	<acme:form-textbox code="spokesperson.milestone.list.label.achievements" path="achievements"/>
	<acme:form-double code="spokesperson.milestone.list.label.effort" path="effort"/>
	<acme:form-textbox code="spokesperson.milestone.list.label.kind" path="kind" />
</acme:form>