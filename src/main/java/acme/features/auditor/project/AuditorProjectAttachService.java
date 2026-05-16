
package acme.features.auditor.project;

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
public class AuditorProjectAttachService extends AbstractService<Auditor, Project> {

	private Collection<AuditReport>		auditReports;
	@Autowired
	private AuditorProjectRepository	repository;
	private Project						project;
	private AuditReport					auditReport;


	@Override
	public void load() {
		int projectId;
		projectId = super.getRequest().getData("id", int.class);
		int auditorId = super.getRequest().getPrincipal().getAccountId();
		this.auditReports = this.repository.findPublishedAndNotAttachedReports(auditorId);
		this.project = this.repository.findProjectById(projectId);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.project != null && !this.project.getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		int auditReportId = super.getRequest().getData("reportId", int.class);
		this.auditReport = this.repository.findAuditReportById(auditReportId);
	}

	@Override
	public void validate() {

		{
			boolean interval;
			Date startMoment;
			Date publishMoment;
			interval = this.auditReport != null;
			if (interval) {
				startMoment = this.auditReport.getStartMoment();
				publishMoment = this.project.getPublishMoment();
				interval = startMoment != null && publishMoment != null && MomentHelper.isAfter(startMoment, publishMoment);
			}

			super.state(interval, "*", "acme.validation.attach.project.interval.message");
		}
	}

	@Override
	public void execute() {
		this.auditReport.setProject(this.project);
		this.repository.save(this.auditReport);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		SelectChoices availableReports;
		availableReports = SelectChoices.from(this.auditReports, "ticker", this.auditReport);
		tuple = super.unbindObject(this.project, "title");
		tuple.put("reports", availableReports);

	}
}
