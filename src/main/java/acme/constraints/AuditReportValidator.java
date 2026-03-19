
package acme.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.AuditReport;
import acme.entities.AuditReportRepository;

@Validator
public class AuditReportValidator extends AbstractValidator<ValidAuditReport, AuditReport> {

	@Autowired
	private AuditReportRepository repository;


	@Override
	protected void initialise(final ValidAuditReport annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final AuditReport auditReport, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;

		if (auditReport == null)
			result = true;
		else {
			{
				boolean uniqueAuditReport;
				AuditReport existingAuditReport;

				existingAuditReport = this.repository.findAuditReportByTicker(auditReport.getTicker());
				uniqueAuditReport = existingAuditReport == null || existingAuditReport.equals(auditReport);

				super.state(context, uniqueAuditReport, "ticker", "acme.validation.audit-report.duplicated-ticker.message");
			}
			{
				boolean enoughAuditSections;

				enoughAuditSections = auditReport.getDraftMode() || !this.repository.getAuditSectionsByAuditReportId(auditReport.getId()).isEmpty();

				super.state(context, enoughAuditSections, "*", "acme.validation.audit-report.auditsections.message");
			}
			{
				boolean interval;
				Date start = auditReport.getStartMoment();
				Date end = auditReport.getEndMoment();
				interval = auditReport.getDraftMode() || MomentHelper.isBefore(start, end);
				super.state(context, interval, "*", "acme.validation.audit-report.correct-interval.message");
			}
			result = !super.hasErrors(context);
		}

		return result;
	}

}
