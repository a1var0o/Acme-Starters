
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

	private AuditReport					auditreport;
	private Collection<AuditSection>	auditsections;


	@Override
	public void load() {
		int auditReportId;
		auditReportId = super.getRequest().getData("id", int.class);

		this.auditreport = this.repository.findCampaign(auditReportId);
		this.auditsections = this.repository.findAuditSectionsByAuditReport(auditReportId);
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
