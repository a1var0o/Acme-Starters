<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox readonly = "${true}" code="sponsor.project.form.label.project" path="title"/>
	<acme:form-select code="sponsor.project.form.label.sponsorship" path="sponsorshipId" choices="${sponsorships}"/>
	<acme:submit code="sponsor.project.form.button.attach" action="/sponsor/project/attach"/>
</acme:form>