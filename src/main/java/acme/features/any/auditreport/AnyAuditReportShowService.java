
package acme.features.any.auditreport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.AuditReport;

@Service
public class AnyAuditReportShowService extends AbstractService<Any, AuditReport> {

	@Autowired
	private AnyAuditReportRepository	repository;

	private AuditReport					auditreport;


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.auditreport = this.repository.findAuditReportById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.auditreport != null && !this.auditreport.getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.auditreport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "auditor");
	}
}
