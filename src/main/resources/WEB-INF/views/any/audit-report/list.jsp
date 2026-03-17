<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list navigable="true">
	<acme:list-column code="any.audit-report.list.label.ticker" path="ticker" width="10%"/>
	<acme:list-column code="any.audit-report.list.label.name" path="name" width="10%"/>
	<acme:list-column code="any.audit-report.list.label.description" path="description" width="50%"/>
	<acme:list-column code="any.audit-report.list.label.startMoment" path="startMoment" width="10%"/>
	<acme:list-column code="any.audit-report.list.label.endMoment" path="endMoment" width="10%"/>
	<acme:list-hidden path="moreIfo"/>
</acme:list>