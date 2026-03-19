
package acme.features.auditor.auditsection;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.AuditReport;
import acme.entities.AuditSection;
import acme.realms.Auditor;

@Service
public class AuditorAuditSectionListService extends AbstractService<Auditor, AuditSection> {

	@Autowired
	private AuditorAuditSectionRepository	repository;

	private Collection<AuditSection>		auditSections;
	private AuditReport						auditReport;


	@Override
	public void load() {

		int auditReportId = super.getRequest().getData("auditReportId", int.class);
		this.auditReport = this.repository.findAuditReportById(auditReportId);
		this.auditSections = this.repository.findAuditSectionsByAuditReport(this.auditReport.getId());
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.auditReport != null;
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.auditSections, "name", "notes", "hours", "kind", "auditReport");
	}

}
