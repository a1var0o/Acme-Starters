
package acme.features.any.spokesperson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.Campaign;
import acme.realms.Spokesperson;

@Service
public class AnySpokespersonShowService extends AbstractService<Any, Spokesperson> {

	@Autowired
	private AnySpokespersonRepository	repository;

	private Campaign					campaign;
	private Spokesperson				spokesperson;


	@Override
	public void load() {
		int campaignId;
		campaignId = super.getRequest().getData("id", int.class);

		this.campaign = this.repository.findCampaign(campaignId);
		this.spokesperson = this.campaign.getSpokesperson();
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.campaign != null;
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.spokesperson, "cv", "achievements", "licensed");
	}
}
