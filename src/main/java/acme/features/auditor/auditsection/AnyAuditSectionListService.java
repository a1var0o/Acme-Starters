
package acme.features.auditor.auditsection;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.AuditReport;
import acme.entities.AuditSection;

@Service
public class AnyAuditSectionListService extends AbstractService<Any, AuditSection> {

	@Autowired
	private AnyAuditSectionRepository	repository;

	private AuditReport					auditreport;
	private Collection<AuditSection>	auditsections;


	@Override
	public void load() {
		int auditreportId;
		auditreportId = super.getRequest().getData("id", int.class);

		this.auditreport = this.repository.findAuditReport(auditreportId);
		this.auditsections = this.repository.findAuditSectionsByAuditReport(auditreportId);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.auditreport != null && !this.auditreport.getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.auditsections, "name", "notes", "hours", "kind");
	}

}
