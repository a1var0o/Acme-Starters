
package acme.features.auditor.auditreport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.entities.AuditReport;
import acme.realms.Auditor;

@Service
public class AuditorAuditReportCreateService extends AbstractService<Auditor, AuditReport> {

	@Autowired
	private AuditorAuditReportRepository	repository;

	private Auditor							auditor;
	private AuditReport						auditreport;


	@Override
	public void load() {
		int userAccountId;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.auditor = this.repository.findAuditorByUserAccountId(userAccountId);

		this.auditreport = super.newObject(AuditReport.class);
		this.auditreport.setDraftMode(true);
		this.auditreport.setAuditor(this.auditor);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRealmOfType(Auditor.class);
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.auditreport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.auditreport);
	}

	@Override
	public void execute() {
		this.repository.save(this.auditreport);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.auditreport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}
}
