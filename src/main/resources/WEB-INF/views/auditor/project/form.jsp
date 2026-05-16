<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox readonly = "${true}" code="sponsor.project.form.label.project" path="title"/>
	<acme:form-select code="auditor.project.form.label.audit-report" path="reportId" choices="${reports}"/>
	<acme:submit code="auditor.project.form.button.attach" action="/auditor/project/attach"/>
</acme:form>