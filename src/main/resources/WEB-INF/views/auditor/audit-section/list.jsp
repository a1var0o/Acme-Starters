<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list navigable = "true">
	<acme:list-column code="auditor.audit-section.list.label.name" path="name" width="10%"/>
	<acme:list-column code="auditor.audit-section.list.label.notes" path="notes" width="50%"/>
	<acme:list-column code="auditor.audit-section.list.label.hours" path="hours" width="10%"/>
	<acme:list-column code="auditor.audit-section.list.label.kind" path="kind" width="10%"/>
</acme:list>

<jstl:if test="${showCreate}">
	<acme:button code="auditor.audit-section.list.button.create" action="/auditor/audit-section/create?auditReportId=${auditReportId}"/>
</jstl:if>