<%@page%>

	<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
		<%@taglib prefix="acme" uri="http://acme-framework.org/" %>

			<acme:form readonly="true">
				<acme:form-textbox code="project-member.campaign.form.label.ticker" path="ticker" />
				<acme:form-textbox code="project-member.campaign.form.label.name" path="name" />
				<acme:form-textarea code="project-member.campaign.form.label.description" path="description" />
				<acme:form-moment code="project-member.campaign.form.label.startMoment" path="startMoment" />
				<acme:form-moment code="project-member.campaign.form.label.endMoment" path="endMoment" />
				<acme:form-url code="project-member.campaign.form.label.moreInfo" path="moreInfo" />
				<jstl:if test="${!hasProject}">
					<input type="hidden" name="projectId" value="${projectId}" />
					<acme:submit code="project-member.campaign.list.button.assign"
						action="/project-member/campaign/assign?id=${id}&amp;projectId=${projectId}" />
				</jstl:if>

				<jstl:if test="${hasProject}">
					<acme:button code="project-member.campaign.form.button.return"
						action="/project-member/campaign/list?projectId=${projectId}" />
				</jstl:if>
			</acme:form>