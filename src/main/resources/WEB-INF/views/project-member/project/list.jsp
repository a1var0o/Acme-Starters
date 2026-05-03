<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list navigable="true">
	<acme:list-column code="project-member.project.list.label.title" path="title"/>
	<acme:list-column code="project-member.project.list.label.keywords" path="keywords"/>
	<acme:list-column code="project-member.project.list.label.kickOffMoment" path="kickOffMoment"/>
	<acme:list-column code="project-member.project.list.label.closeOutMoment" path="closeOutMoment"/>
</acme:list>
