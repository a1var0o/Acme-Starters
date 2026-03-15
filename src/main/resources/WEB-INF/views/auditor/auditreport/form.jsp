<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="auditor.auditreport.list.label.ticker" path="ticker"/>
	<acme:form-textbox code="auditor.auditreport.list.label.name" path="name"/>
	<acme:form-textarea code="auditor.auditreport.list.label.description" path="description"/>
	<acme:form-moment code="auditor.auditreport.list.label.startMoment" path="startMoment"/>
	<acme:form-moment code="auditor.auditreport.list.label.endMoment" path="endMoment"/>
	<acme:form-url code="auditor.auditreport.form.label.moreInfo" path="moreInfo"/>
	
	<jstl:choose>	 
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="auditor.auditreport.form.button.auditsections" action="/auditor/auditsection/list?auditReportId=${id}"/>	
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:button code="auditor.auditreport.form.button.milestones" action="/auditor/auditsection/list?auditReportId=${id}"/>
			<acme:submit code="auditor.auditreport.form.button.update" action="/auditor/auditreport/update"/>
			<acme:submit code="auditor.auditreport.form.button.delete" action="/auditor/auditreport/delete"/>
			<acme:submit code="auditor.auditreport.form.button.publish" action="/auditor/auditreport/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.auditreport.form.button.create" action="/auditor/auditreport/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>