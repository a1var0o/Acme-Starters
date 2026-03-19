
package acme.features.auditor.auditsection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.AuditSection;
import acme.realms.Auditor;

@Service
public class AuditorAuditSectionDeleteService extends AbstractService<Auditor, AuditSection> {

	@Autowired
	private AuditorAuditSectionRepository	repository;
	private AuditSection					auditSection;


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.auditSection = this.repository.findAuditSectionById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.auditSection != null && this.auditSection.getAuditReport().getDraftMode() && this.auditSection.getAuditReport().getAuditor().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.auditSection, "name", "notes", "hours", "kind");
	}

	@Override
	public void validate() {
		;
	}

	@Override
	public void execute() {
		this.repository.delete(this.auditSection);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.auditSection, "name", "notes", "hours", "kind");
		tuple.put("AuditReportId", this.auditSection.getAuditReport().getId());
		tuple.put("draftMode", this.auditSection.getAuditReport().getDraftMode());
	}
}
