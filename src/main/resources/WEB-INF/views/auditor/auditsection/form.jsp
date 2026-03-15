<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="auditor.auditsection.list.label.name" path="name"/>
	<acme:form-textbox code="auditor.auditsection.list.label.notes" path="notes"/>
	<acme:form-double code="auditor.auditsection.list.label.hours" path="hours"/>
	<acme:form-textbox code="auditor.auditsection.list.label.kind" path="kind" />
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="auditor.auditsection.form.button.update" action="/auditor/auditsection/update"/>
			<acme:submit code="auditor.auditsection.form.button.delete" action="/auditor/auditsection/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.auditsection.form.button.create" action="/auditor/auditsection/create?auditReportId=${auditReportId}"/>
		</jstl:when>		
	</jstl:choose>	
</acme:form>