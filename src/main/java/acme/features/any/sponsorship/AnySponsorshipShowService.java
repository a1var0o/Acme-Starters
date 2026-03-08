
package acme.features.any.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.Sponsorship;
import acme.realms.Sponsor;

@Service
public class AnySponsorshipShowService extends AbstractService<Any, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnySponsorshipRepository	repository;

	private Sponsorship					sponsorship;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.sponsorship = this.repository.findSponsorshipById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.sponsorship != null && !this.sponsorship.getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Collection<Sponsor> sponsors;
		SelectChoices choices;
		Tuple tuple;

		sponsors = this.repository.findAllSponsors();
		choices = SelectChoices.from(sponsors, "identity.fullName", this.sponsorship.getSponsor());

		tuple = super.unbindObject(this.sponsorship, "ticker", "name", "startMoment", "endMoment", "moreInfo", "description");
		tuple.put("sponsor", choices.getSelected().getKey());
		tuple.put("sponsors", choices);
	}

}
