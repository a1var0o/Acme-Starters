
package acme.features.spokesperson.milestone;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.Campaign;
import acme.entities.Milestone;
import acme.realms.Spokesperson;

@Service
public class SpokespersonMilestoneListService extends AbstractService<Spokesperson, Milestone> {

	@Autowired
	private SpokespersonMilestoneRepository	repository;

	private Collection<Milestone>			milestones;
	private Campaign						campaign;


	@Override
	public void load() {

		int campaignId = super.getRequest().getData("campaignId", int.class);
		this.campaign = this.repository.findCampaignById(campaignId);
		this.milestones = this.repository.findMilestonesByCampaign(this.campaign.getId());
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.campaign != null && (this.campaign.getSpokesperson().isPrincipal() || !this.campaign.getDraftMode());
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		boolean showCreate;
		super.unbindObjects(this.milestones, "title", "achievements", "effort", "kind", "campaign");
		showCreate = this.campaign.getDraftMode() && this.campaign.getSpokesperson().isPrincipal();
		super.unbindGlobal("campaignId", this.campaign.getId());
		super.unbindGlobal("showCreate", showCreate);
	}

}
