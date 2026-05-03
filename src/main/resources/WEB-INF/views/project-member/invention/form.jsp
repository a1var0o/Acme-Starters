<%@page%>

	<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
		<%@taglib prefix="acme" uri="http://acme-framework.org/" %>

			<acme:form readonly="true">
				<acme:form-textbox code="project-member.invention.form.label.ticker" path="ticker" />
				<acme:form-textbox code="project-member.invention.form.label.name" path="name" />
				<acme:form-textarea code="project-member.invention.form.label.description" path="description" />
				<acme:form-moment code="project-member.invention.form.label.startMoment" path="startMoment" />
				<acme:form-moment code="project-member.invention.form.label.endMoment" path="endMoment" />
				<acme:form-url code="project-member.invention.form.label.moreInfo" path="moreInfo" />

				<jstl:if test="${hasProject}">
					<div class="form-group">
						<label><spring:message code="project-member.project.list.label.title"/>:</label>
						<input type="text" class="form-control" value="<jstl:out value="${projectTitle}"/>" readonly="readonly" />
					</div>
				</jstl:if>
				
				<jstl:if test="${!hasProject}">
					<input type="hidden" name="customProjectId" value="${projectId}" />
					<acme:submit code="project-member.invention.list.button.assign" action="/project-member/invention/assign" />
				</jstl:if>
			</acme:form>

			<jstl:if test="${!hasProject}">
				<acme:button code="project-member.invention.list.button.return" action="/project-member/invention/list-available?projectId=${projectId}"/>
			</jstl:if>

			<jstl:if test="${hasProject}">
				<acme:button code="project-member.invention.form.button.return" action="/project-member/invention/list?projectId=${projectId}"/>
			</jstl:if>