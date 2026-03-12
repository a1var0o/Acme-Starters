
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.Campaign;
import acme.entities.CampaignRepository;

@Validator
public class CampaignValidator extends AbstractValidator<ValidCampaign, Campaign> {

	@Autowired
	private CampaignRepository repository;


	@Override
	protected void initialise(final ValidCampaign annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Campaign campaign, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;

		if (campaign == null)
			result = true;
		else {
			{
				boolean uniqueCampaign;
				Campaign existingCampaign;

				existingCampaign = this.repository.findCampaignByTicker(campaign.getTicker());
				uniqueCampaign = existingCampaign == null || existingCampaign.equals(campaign);

				super.state(context, uniqueCampaign, "ticker", "acme.validation.campaign.duplicated-ticker.message");
			}
			{
				boolean enoughMilestones;

				enoughMilestones = campaign.getDraftMode() || !this.repository.getMilestonesByCampaignId(campaign.getId()).isEmpty();

				super.state(context, enoughMilestones, "*", "acme.validation.campaign.milestones.message");
			}
			{
				boolean validInterval;

				validInterval = campaign.getDraftMode() || MomentHelper.isBefore(campaign.getStartMoment(), campaign.getEndMoment());

				super.state(context, validInterval, "*", "acme.validation.campaign.interval.message");
			}
			result = !super.hasErrors(context);
		}

		return result;
	}

}
