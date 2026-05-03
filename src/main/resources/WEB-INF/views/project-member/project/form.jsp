<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form readonly="true">
	<acme:form-textbox code="project-member.project.form.label.title" path="title"/>
	<acme:form-textbox code="project-member.project.form.label.keywords" path="keywords"/>
	<acme:form-textarea code="project-member.project.form.label.description" path="description"/>
	<acme:form-moment code="project-member.project.form.label.kickOffMoment" path="kickOffMoment"/>
	<acme:form-moment code="project-member.project.form.label.closeOutMoment" path="closeOutMoment"/>
</acme:form>

<acme:button code="project-member.project.form.button.inventions" action="/project-member/invention/list?projectId=${id}"/>
<acme:button code="project-member.project.form.button.campaigns" action="/project-member/campaign/list?projectId=${id}"/>
<acme:button code="project-member.project.form.button.strategies" action="/project-member/strategy/list?projectId=${id}"/>

<acme:button code="project-member.project.form.button.return" action="/project-member/project/list"/>
