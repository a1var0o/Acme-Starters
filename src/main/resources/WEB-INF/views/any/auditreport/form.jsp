<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.auditreport.list.label.name" path="name"/>
	<acme:form-textarea code="any.auditreport.list.label.description" path="description"/>
	<acme:form-moment code="any.auditreport.list.label.startMoment" path="startMoment"/>
	<acme:form-moment code="any.auditreport.list.label.endMoment" path="endMoment"/>
	<acme:button code="any.auditreport.list.label.auditor" action="/any/auditor/show?id=${id}"/>
	<acme:button code="any.auditreport.list.label.auditsection" action="/any/auditsection/list?id=${id}"/>
</acme:form>