<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="auditor.auditreport.list.label.ticker" path="ticker"/>
	<acme:form-textbox code="auditor.auditreport.list.label.name" path="name"/>
	<acme:form-textarea code="auditor.auditreport.list.label.description" path="description"/>
	<acme:form-moment code="auditor.auditreport.list.label.startMoment" path="startMoment"/>
	<acme:form-moment code="auditor.auditreport.list.label.endMoment" path="endMoment"/>
	<acme:form-checkbox code="auditor.auditreport.list.label.draftMode" path="draftMode"/>
	
	<acme:button code="auditor.auditreport.form.button.auditsections" action="/auditor/auditsection/list?auditReportId=${id}"/>
</acme:form>