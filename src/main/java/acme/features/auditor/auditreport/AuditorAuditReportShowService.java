
package acme.features.auditor.auditreport;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.AuditReport;
import acme.entities.Project;
import acme.realms.Auditor;

@Service
public class AuditorAuditReportShowService extends AbstractService<Auditor, AuditReport> {

	@Autowired
	private AuditorAuditReportRepository	repository;

	private AuditReport						auditReport;

	private Collection<Project>				projects;

	@Autowired
	private AuditorProjectRepository		projectRepository;


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.auditReport = this.repository.findAuditReportById(id);
		this.projects = this.projectRepository.findPublishedProjects();

	}

	@Override
	public void authorise() {
		boolean status;
		status = this.auditReport != null && (this.auditReport.getAuditor().isPrincipal() || !this.auditReport.getDraftMode());

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		SelectChoices availableProjects;
		boolean hasProject = this.auditReport.getProject() != null;
		availableProjects = SelectChoices.from(this.projects, "title", this.auditReport.getProject());
		tuple = super.unbindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "auditor", "project");
		tuple.put("projects", availableProjects);
		tuple.put("hasProject", hasProject);
	}
}
