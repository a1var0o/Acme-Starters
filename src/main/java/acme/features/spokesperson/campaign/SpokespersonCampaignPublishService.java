
package acme.features.spokesperson.campaign;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.Campaign;
import acme.entities.Milestone;
import acme.realms.Spokesperson;

@Service
public class SpokespersonCampaignPublishService extends AbstractService<Spokesperson, Campaign> {

	@Autowired
	private SpokespersonCampaignRepository	repository;
	private Campaign						campaign;


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.campaign = this.repository.findCampaignById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.campaign != null && this.campaign.getDraftMode() && this.campaign.getSpokesperson().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.campaign);
		{
			boolean futureInterval;

			futureInterval = MomentHelper.isFuture(this.campaign.getStartMoment());
			super.state(futureInterval, "*", "acme.validation.campaign.no-future-interval.message");
		}
		{
			Collection<Milestone> milestones = this.repository.findMilestonesByCampaignId(this.campaign.getId());
			boolean atLeastOneMilestone = !milestones.isEmpty();

			super.state(atLeastOneMilestone, "*", "acme.validation.campaign.milestones.message");
		}
		{
			Date start = this.campaign.getStartMoment();
			Date end = this.campaign.getEndMoment();
			boolean correctDates = MomentHelper.isBefore(start, end);

			super.state(correctDates, "*", "acme.validation.campaign.interval.message");
		}
	}

	@Override
	public void execute() {
		this.campaign.setDraftMode(false);
		this.repository.save(this.campaign);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
	}
}
