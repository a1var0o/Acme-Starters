
package acme.features.auditor.auditreport;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.AuditReport;
import acme.realms.Auditor;

@Service
public class AuditorAuditReportListService extends AbstractService<Auditor, AuditReport> {

	@Autowired
	private AuditorAuditReportRepository	repository;

	private Collection<AuditReport>			auditReports;


	@Override
	public void load() {
		int accountId = super.getRequest().getPrincipal().getAccountId();
		this.auditReports = this.repository.findAuditReportsByAuditorAccountId(accountId);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.auditReports, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "auditor");
	}
}
