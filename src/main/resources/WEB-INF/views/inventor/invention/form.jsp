<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="inventor.invention.show.label.name" path="name"/>
	<acme:form-textarea code="inventor.invention.show.label.description" path="description"/>
	<acme:form-moment code="inventor.invention.show.label.startMoment" path="startMoment"/>
	<acme:form-moment code="inventor.invention.show.label.endMoment" path="endMoment"/>
	<acme:button code="inventor.invention.show.label.parts" action="/inventor/part/list?inventionId=${id}"/>
</acme:form>
