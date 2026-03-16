
package acme.features.spokesperson.milestone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.Campaign;
import acme.entities.Milestone;
import acme.realms.Spokesperson;

@Service
public class SpokespersonMilestoneCreateService extends AbstractService<Spokesperson, Milestone> {

	@Autowired
	private SpokespersonMilestoneRepository	repository;

	private Campaign						campaign;
	private Milestone						milestone;


	@Override
	public void load() {
		int campaignId;

		campaignId = super.getRequest().getData("campaignId", int.class);
		this.campaign = this.repository.findCampaignById(campaignId);

		this.milestone = super.newObject(Milestone.class);
		this.milestone.setCampaign(this.campaign);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.campaign != null && this.milestone.getCampaign().getSpokesperson().isPrincipal() && this.milestone.getCampaign().getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.milestone, "title", "achievements", "effort", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.milestone);
	}

	@Override
	public void execute() {
		this.repository.save(this.milestone);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.milestone, "title", "achievements", "effort", "kind");
		tuple.put("campaignId", super.getRequest().getData("campaignId", int.class));
		tuple.put("draftMode", this.milestone.getCampaign().getDraftMode());
	}
}
