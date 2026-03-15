<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textarea code="any.auditor.show.label.firm" path="firm"/>
	<acme:form-textarea code="any.auditor.show.label.highlights" path="highlights"/>
	<acme:form-checkbox code="any.auditor.show.label.solicitor" path="solicitor"/>
</acme:form>