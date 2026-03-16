
package acme.features.sponsor.sponsorship;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.Donation;
import acme.entities.Sponsorship;
import acme.realms.Sponsor;

@Service
public class SponsorSponsorshipPublishService extends AbstractService<Sponsor, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorshipRepository	repository;

	private Sponsorship						sponsorship;

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

		status = this.sponsorship != null && //
			this.sponsorship.getDraftMode() && //
			this.sponsorship.getSponsor().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.sponsorship, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.sponsorship);
		{
			boolean futureInterval;

			futureInterval = MomentHelper.isFuture(this.sponsorship.getStartMoment());
			super.state(futureInterval, "*", "acme.validation.sponsorship.no-future-interval.message");
		}
		{
			Collection<Donation> donations = this.repository.findDonationsBySponsorshipId(this.sponsorship.getId());
			boolean atLeastOneDonation = !donations.isEmpty();

			super.state(atLeastOneDonation, "*", "acme.validation.sponsorship.donations.message");
		}
		{
			Date start = this.sponsorship.getStartMoment();
			Date end = this.sponsorship.getEndMoment();
			boolean correctDates = MomentHelper.isBefore(start, end);

			super.state(correctDates, "*", "acme.validation.sponsorship.interval.message");
		}
	}

	@Override
	public void execute() {
		this.sponsorship.setDraftMode(false);
		this.repository.save(this.sponsorship);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.sponsorship, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
	}
}
