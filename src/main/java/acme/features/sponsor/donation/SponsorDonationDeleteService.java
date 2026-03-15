
package acme.features.sponsor.donation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.Donation;
import acme.entities.Sponsorship;
import acme.realms.Sponsor;

@Service
public class SponsorDonationDeleteService extends AbstractService<Sponsor, Donation> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorDonationRepository	repository;

	private Sponsorship					sponsorship;
	private Donation					donation;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.donation = this.repository.findDonationById(id);
		this.sponsorship = this.donation.getSponsorship();
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.donation != null && //
			this.sponsorship.getDraftMode() && //
			this.sponsorship.getSponsor().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.donation, "name", "notes", "money", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.donation);
	}

	@Override
	public void execute() {
		this.repository.delete(this.donation);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.donation, "name", "notes", "money", "kind");
		tuple.put("sponsorshipId", this.sponsorship.getId());
		tuple.put("draftMode", this.sponsorship.getDraftMode());
	}
}
