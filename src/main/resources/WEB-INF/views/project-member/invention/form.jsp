<%@page%>

	<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
		<%@taglib prefix="acme" uri="http://acme-framework.org/" %>

			<jstl:if test="${assigned}">
				<script type="text/javascript">
					window.location.replace('${pageContext.request.contextPath}/project-member/invention/list?projectId=${projectId}');
				</script>
			</jstl:if>

			<acme:form readonly="true">
				<acme:form-textbox code="project-member.invention.form.label.ticker" path="ticker" />
				<acme:form-textbox code="project-member.invention.form.label.name" path="name" />
				<acme:form-textarea code="project-member.invention.form.label.description" path="description" />
				<acme:form-moment code="project-member.invention.form.label.startMoment" path="startMoment" />
				<acme:form-moment code="project-member.invention.form.label.endMoment" path="endMoment" />
				<acme:form-url code="project-member.invention.form.label.moreInfo" path="moreInfo" />

				<jstl:if test="${!hasProject}">
					<input type="hidden" name="projectId" value="${projectId}" />
					<acme:submit code="project-member.invention.list.button.assign"
						action="/project-member/invention/assign?id=${id}&amp;projectId=${projectId}" />
				</jstl:if>
			</acme:form>