<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.audit-report.list.label.name" path="name"/>
	<acme:form-textarea code="any.audit-report.list.label.description" path="description"/>
	<acme:form-moment code="any.audit-report.list.label.startMoment" path="startMoment"/>
	<acme:form-moment code="any.audit-report.list.label.endMoment" path="endMoment"/>
	<acme:button code="any.audit-report.list.label.auditor" action="/any/auditor/show?id=${id}"/>
	<acme:button code="any.audit-report.list.label.auditsection" action="/any/audit-section/list?id=${id}"/>
</acme:form>