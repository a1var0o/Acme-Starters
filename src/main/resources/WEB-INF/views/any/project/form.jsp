<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="manager.project.form.label.title" path="title"/>
	<acme:form-textbox code="manager.project.form.label.keywords" path="keywords"/>
	<acme:form-textarea code="manager.project.form.label.description" path="description"/>
	<acme:form-moment code="manager.project.form.label.kickOffMoment" path="kickOffMoment"/>
	<acme:form-moment code="manager.project.form.label.closeOutMoment" path="closeOutMoment"/>
	<acme:form-moment code="any.project.form.label.publishMoment" path="publishMoment"/>
</acme:form>
