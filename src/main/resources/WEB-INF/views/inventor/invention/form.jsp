<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="inventor.invention.form.label.ticker" path="ticker"/>
	<acme:form-textbox code="inventor.invention.form.label.name" path="name"/>
	<acme:form-textarea code="inventor.invention.form.label.description" path="description"/>
	<acme:form-moment code="inventor.invention.form.label.startMoment" path="startMoment"/>
	<acme:form-moment code="inventor.invention.form.label.endMoment" path="endMoment"/>
	<acme:form-textarea code="inventor.invention.form.label.moreInfo" path="moreInfo"/>
	
	<acme:button code="inventor.invention.form.button.parts" action="/inventor/part/list?inventionId=${id}"/>
	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="inventor.invention.form.button.create" action="/inventor/invention/create"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'update|delete') && draftMode == true}">
			<acme:submit code="inventor.invention.form.button.update" action="/inventor/invention/update"/>
			<acme:submit code="inventor.invention.form.button.delete" action="/inventor/invention/delete"/>
		</jstl:when>
	</jstl:choose>
</acme:form>
