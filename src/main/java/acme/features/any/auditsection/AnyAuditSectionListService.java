
package acme.features.any.auditsection;

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

	private AuditReport					auditReport;
	private Collection<AuditSection>	auditSections;


	@Override
	public void load() {
		int auditReportId;
		auditReportId = super.getRequest().getData("id", int.class);

		this.auditReport = this.repository.findAuditReport(auditReportId);
		this.auditSections = this.repository.findAuditSectionsByAuditReport(auditReportId);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.auditReport != null && !this.auditReport.getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.auditSections, "name", "notes", "hours", "kind");
	}

}
