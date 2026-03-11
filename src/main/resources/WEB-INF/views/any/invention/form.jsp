<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.invention.show.label.name" path="name"/>
	<acme:form-textarea code="any.invention.show.label.description" path="description"/>
	<acme:form-moment code="any.invention.show.label.startMoment" path="startMoment"/>
	<acme:form-moment code="any.invention.show.label.endMoment" path="endMoment"/>
	<acme:button code="any.invention.show.label.inventor" action="/any/inventor/show?id=${id}"/>
	<acme:button code="any.invention.show.label.parts" action="/any/part/list?inventionId=${id}"/>
</acme:form>
