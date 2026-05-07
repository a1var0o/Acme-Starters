
package acme.features.auditor.auditreport;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.AuditReport;
import acme.entities.Project;
import acme.realms.Auditor;

@Service
public class AuditorAuditReportAttachProjectService extends AbstractService<Auditor, AuditReport> {

	@Autowired
	private AuditorAuditReportRepository	auditReportRepository;
	private AuditReport						auditReport;
	@Autowired
	private AuditorProjectRepository		projectRepository;
	private Collection<Project>				projects;


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.auditReport = this.auditReportRepository.findAuditReportById(id);
		this.projects = this.projectRepository.findPublishedProjects();
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.auditReport != null && !this.auditReport.getDraftMode() && this.auditReport.getAuditor().isPrincipal() && this.auditReport.getProject() == null;
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.auditReport, "project");
	}

	@Override
	public void validate() {

		{
			boolean interval;
			Date startMoment;
			Date publishMoment;
			interval = this.auditReport.getProject() != null;
			if (interval) {
				startMoment = this.auditReport.getStartMoment();
				publishMoment = this.auditReport.getProject().getPublishMoment();
				interval = startMoment != null && publishMoment != null && MomentHelper.isAfter(startMoment, publishMoment);
			}

			super.state(interval, "project", "acme.validation.attach.project.interval.message");
		}
	}

	@Override
	public void execute() {
		this.auditReportRepository.save(this.auditReport);
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
