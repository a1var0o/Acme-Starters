
package acme.constraints;

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

				super.state(context, uniqueAuditReport, "ticker", "acme.validation.auditreport.duplicated-ticker.message");
			}
			{
				boolean enoughAuditSections;

				enoughAuditSections = auditReport.getDraftMode() || !this.repository.getAuditSectionsByAuditReportId(auditReport.getId()).isEmpty();

				super.state(context, enoughAuditSections, "*", "acme.validation.auditreport.auditsections.message");
			}
			{
				boolean validInterval;

				validInterval = auditReport.getDraftMode() || MomentHelper.isBefore(auditReport.getStartMoment(), auditReport.getEndMoment());

				super.state(context, validInterval, "*", "acme.validation.auditreport.interval.message");
			}
			result = !super.hasErrors(context);
		}

		return result;
	}

}
