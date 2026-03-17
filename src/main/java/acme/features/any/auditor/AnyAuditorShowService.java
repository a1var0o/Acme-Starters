
package acme.features.any.auditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.AuditReport;
import acme.realms.Auditor;

@Service
public class AnyAuditorShowService extends AbstractService<Any, Auditor> {

	@Autowired
	private AnyAuditorRepository	repository;

	private AuditReport				auditReport;
	private Auditor					auditor;


	@Override
	public void load() {
		int auditReportId;
		auditReportId = super.getRequest().getData("id", int.class);

		this.auditReport = this.repository.findAuditReport(auditReportId);
		this.auditor = this.auditReport.getAuditor();
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.auditReport != null;
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.auditor, "firm", "highlights", "solicitor");
	}
}
