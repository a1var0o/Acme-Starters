
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

	private AuditReport						auditreport;
	private AuditSection					auditsection;


	@Override
	public void load() {
		int auditReportId;

		auditReportId = super.getRequest().getData("auditReportId", int.class);
		this.auditreport = this.repository.findAuditReportById(auditReportId);

		this.auditsection = super.newObject(AuditSection.class);
		this.auditsection.setAuditreport(this.auditreport);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.auditreport != null && this.auditsection.getAuditreport().getAuditor().isPrincipal() && this.auditsection.getAuditreport().getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.auditsection, "name", "notes", "hours", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.auditsection);
	}

	@Override
	public void execute() {
		this.repository.save(this.auditsection);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.auditsection, "name", "notes", "hours", "kind");
		tuple.put("auditReportId", super.getRequest().getData("auditReportId", int.class));
		tuple.put("draftMode", this.auditsection.getAuditreport().getDraftMode());
	}
}
