
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

	private Collection<AuditSection>		auditsections;
	private AuditReport						auditreport;


	@Override
	public void load() {

		int auditReportId = super.getRequest().getData("auditReportId", int.class);
		this.auditreport = this.repository.findAuditReportById(auditReportId);
		this.auditsections = this.repository.findAuditSectionsByAuditReport(this.auditreport.getId());
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.auditreport != null;
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.auditsections, "name", "notes", "hours", "kind", "auditreport");
	}

}
