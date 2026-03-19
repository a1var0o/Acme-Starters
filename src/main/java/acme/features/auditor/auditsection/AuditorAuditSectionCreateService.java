
package acme.features.auditor.auditsection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.AuditReport;
import acme.entities.AuditSection;
import acme.realms.Auditor;

@Service
public class AuditorAuditSectionCreateService extends AbstractService<Auditor, AuditSection> {

	@Autowired
	private AuditorAuditSectionRepository	repository;

	private AuditReport						auditReport;
	private AuditSection					auditSection;


	@Override
	public void load() {
		int auditReportId;

		auditReportId = super.getRequest().getData("auditReportId", int.class);
		this.auditReport = this.repository.findAuditReportById(auditReportId);

		this.auditSection = super.newObject(AuditSection.class);
		this.auditSection.setAuditReport(this.auditReport);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.auditReport != null && this.auditSection.getAuditReport().getAuditor().isPrincipal() && this.auditSection.getAuditReport().getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.auditSection, "name", "notes", "hours", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.auditSection);
	}

	@Override
	public void execute() {
		this.repository.save(this.auditSection);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.auditSection, "name", "notes", "hours", "kind");
		tuple.put("auditReportId", super.getRequest().getData("auditReportId", int.class));
		tuple.put("draftMode", this.auditSection.getAuditReport().getDraftMode());
	}
}
