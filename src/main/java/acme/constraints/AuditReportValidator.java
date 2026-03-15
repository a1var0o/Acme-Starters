
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
	public boolean isValid(final AuditReport auditreport, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;

		if (auditreport == null)
			result = true;
		else {
			{
				boolean uniqueAuditReport;
				AuditReport existingAuditReport;

				existingAuditReport = this.repository.findAuditReportByTicker(auditreport.getTicker());
				uniqueAuditReport = existingAuditReport == null || existingAuditReport.equals(auditreport);

				super.state(context, uniqueAuditReport, "ticker", "acme.validation.auditreport.duplicated-ticker.message");
			}
			{
				boolean enoughAuditSections;

				enoughAuditSections = auditreport.getDraftMode() || !this.repository.getAuditSectionsByAuditReportId(auditreport.getId()).isEmpty();

				super.state(context, enoughAuditSections, "*", "acme.validation.auditreport.auditsections.message");
			}
			{
				boolean validInterval;

				validInterval = auditreport.getDraftMode() || MomentHelper.isBefore(auditreport.getStartMoment(), auditreport.getEndMoment());

				super.state(context, validInterval, "*", "acme.validation.auditreport.interval.message");
			}
			result = !super.hasErrors(context);
		}

		return result;
	}

}
