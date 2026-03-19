
package acme.features.auditor.auditreport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.AuditReport;
import acme.realms.Auditor;

@Service
public class AuditorAuditReportCreateService extends AbstractService<Auditor, AuditReport> {

	@Autowired
	private AuditorAuditReportRepository	repository;

	private Auditor							auditor;
	private AuditReport						auditReport;


	@Override
	public void load() {
		int userAccountId;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.auditor = this.repository.findAuditorByUserAccountId(userAccountId);

		this.auditReport = super.newObject(AuditReport.class);
		this.auditReport.setDraftMode(true);
		this.auditReport.setAuditor(this.auditor);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRealmOfType(Auditor.class);
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.auditReport);
	}

	@Override
	public void execute() {
		this.repository.save(this.auditReport);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
	}
}
