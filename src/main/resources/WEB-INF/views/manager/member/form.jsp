<%@page%>

	<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
		<%@taglib prefix="acme" uri="http://acme-framework.org/" %>

			<acme:form>
				<acme:form-textbox code="manager.member.form.label.project" path="project.title" readonly="true"/>
				<acme:form-select code="manager.member.form.label.project-member" path="projectMember" choices="${users}"/>

				
				<acme:submit code="manager.member.form.button.add" action="/manager/member/create?projectId=${projectId}"/>
			</acme:form>