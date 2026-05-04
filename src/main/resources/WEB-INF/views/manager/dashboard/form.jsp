
<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<h2>
	<acme:print code="manager.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:print code="manager.dashboard.form.label.number-of-projects"/>
		</th>
		<td>
			<acme:print value="${numberOfProjects}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:print code="manager.dashboard.form.label.number-of-projects-deviation"/>
		</th>
		<td>
			<acme:print value="${numberOfProjectsDeviation}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:print code="manager.dashboard.form.label.min-effort"/>
		</th>
		<td>
			<acme:print value="${minEffort}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:print code="manager.dashboard.form.label.max-effort"/>
		</th>
		<td>
			<acme:print value="${maxEffort}"/>
		</td>
	</tr>	
	<tr>	
		<th scope="row">
			<acme:print code="manager.dashboard.form.label.avg-effort"/>
		</th>
		<td>
			<acme:print value="${avgEffort}"/>
		</td>
	</tr>	
</table>