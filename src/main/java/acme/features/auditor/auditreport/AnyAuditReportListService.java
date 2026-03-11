
package acme.features.auditor.auditreport;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.AuditReport;

@Service
public class AnyAuditReportListService extends AbstractService<Any, AuditReport> {

	@Autowired
	private AnyAuditReportRepository	repository;

	private Collection<AuditReport>		auditreports;


	@Override
	public void load() {
		this.auditreports = this.repository.findPublishedAuditReports();
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRealmOfType(Any.class);
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.auditreports, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}
}
