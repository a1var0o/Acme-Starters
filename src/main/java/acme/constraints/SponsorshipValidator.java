
package acme.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.Sponsorship;
import acme.entities.SponsorshipRepository;

@Validator
public class SponsorshipValidator extends AbstractValidator<ValidSponsorship, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorshipRepository repository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidSponsorship annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Sponsorship sponsorship, final ConstraintValidatorContext context) {
		// HINT: sponsorship can be null
		assert context != null;

		boolean result;

		if (sponsorship == null)
			result = true;
		else {
			{
				boolean uniqueSponsorship;
				Sponsorship existingSponsorship;

				existingSponsorship = this.repository.findSponsorshipByTicker(sponsorship.getTicker());
				uniqueSponsorship = existingSponsorship == null || existingSponsorship.equals(sponsorship);

				super.state(context, uniqueSponsorship, "ticker", "acme.validation.sponsorship.duplicated-ticker.message");
			}
			{
				boolean sponsorshipHasDonations;
				Double totalDonations;

				totalDonations = this.repository.getTotalDonations(sponsorship.getId());
				sponsorshipHasDonations = sponsorship.getDraftMode() || totalDonations != null;

				super.state(context, sponsorshipHasDonations, "*", "acme.validation.sponsorship.donations.message");
			}
			{
				boolean correctInterval;
				Date startMoment;
				Date endMoment;

				startMoment = sponsorship.getStartMoment();
				endMoment = sponsorship.getEndMoment();

				correctInterval = sponsorship.getDraftMode() || startMoment != null && endMoment != null && MomentHelper.isBefore(startMoment, endMoment);

				super.state(context, correctInterval, "*", "acme.validation.sponsorship.interval.message");
			}
			result = !super.hasErrors(context);
		}

		return result;
	}

}
