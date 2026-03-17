
<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list navigable = "true">
	<acme:list-column code="auditor.audit-report.list.label.ticker" path="ticker" width="10%"/>
	<acme:list-column code="auditor.audit-report.list.label.name" path="name" width="25%"/>
	<acme:list-column code="auditor.audit-report.list.label.description" path="description" width="30%"/>
	<acme:list-column code="auditor.audit-report.list.label.startMoment" path="startMoment" width="15%"/>
	<acme:list-column code="auditor.audit-report.list.label.endMoment" path="endMoment" width="15%"/>
	<acme:list-column code="auditor.audit-report.list.label.draftMode" path="draftMode" width="5%"/>
	<acme:list-hidden path="moreInfo"/>
	<acme:list-hidden path="draftMode"/>
</acme:list>

<acme:button code="auditor.audit-report.list.button.create" action="/auditor/audit-report/create"/>