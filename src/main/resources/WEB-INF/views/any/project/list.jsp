<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list navigable="true">
	<acme:list-column code="any.project.list.label.title" path="title" width="10%"/>
	<acme:list-column code="any.project.list.label.keywords" path="keywords" width="10%"/>
	<acme:list-column code="any.project.list.label.description" path="description" width="50%"/>
	<acme:list-column code="any.project.list.label.kickOffMoment" path="kickOffMoment" width="10%"/>
	<acme:list-column code="any.project.list.label.closeOutMoment" path="closeOutMoment" width="10%"/>
	<acme:list-column code="any.project.list.label.publishMoment" path="publishMoment" width="10%"/>
</acme:list>