<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="manager.project.form.label.title" path="title"/>
	<acme:form-textbox code="manager.project.form.label.keywords" path="keywords"/>
	<acme:form-textarea code="manager.project.form.label.description" path="description"/>
	<acme:form-moment code="manager.project.form.label.kickOffMoment" path="kickOffMoment"/>
	<acme:form-moment code="manager.project.form.label.closeOutMoment" path="closeOutMoment"/>
	
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update') && draftMode == true}">
			<acme:submit code="manager.project.form.button.update" action="/manager/project/update"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="manager.project.form.button.create" action="/manager/project/create"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|publish') && draftMode == true}">
			<acme:submit code="manager.project.form.button.publish" action="/manager/project/publish"/>
		</jstl:when>
	</jstl:choose>
</acme:form>
