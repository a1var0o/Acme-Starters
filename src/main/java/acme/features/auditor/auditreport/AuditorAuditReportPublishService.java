
package acme.features.spokesperson.campaign;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.AuditReport;
import acme.entities.AuditSection;
import acme.realms.Auditor;

@Service
public class AuditorAuditReportPublishService extends AbstractService<Auditor, AuditReport> {

	@Autowired
	private AuditorAuditReportRepository	repository;
	private AuditReport						auditreport;


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.auditreport = this.repository.findAuditReportById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.auditreport != null && this.auditreport.getDraftMode() && this.auditreport.getAuditor().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.auditreport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.auditreport);
		{
			boolean futureInterval;

			futureInterval = MomentHelper.isFuture(this.auditreport.getStartMoment());
			super.state(futureInterval, "*", "acme.validation.auditreport.no-future-interval.message");
		}
		{
			Collection<AuditSection> auditsections = this.repository.findAuditSectionsByAuditReportId(this.auditreport.getId());
			boolean atLeastOneAuditSection = !auditsections.isEmpty();

			super.state(atLeastOneAuditSection, "*", "acme.validation.auditreport.auditsections.message");
		}
		{
			Date start = this.auditreport.getStartMoment();
			Date end = this.auditreport.getEndMoment();
			boolean correctDates = MomentHelper.isBefore(start, end);

			super.state(correctDates, "*", "acme.validation.auditreport.interval.message");
		}
	}

	@Override
	public void execute() {
		this.auditreport.setDraftMode(false);
		this.repository.save(this.auditreport);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.auditreport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
	}
}
