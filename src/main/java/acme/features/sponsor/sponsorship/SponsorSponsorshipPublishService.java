
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
			Date startMoment;

			startMoment = this.sponsorship.getStartMoment();
			futureInterval = startMoment != null && MomentHelper.isFuture(startMoment);

			super.state(futureInterval, "*", "acme.validation.sponsorship.no-future-interval.message");
		}
		{
			boolean atLeastOneDonation;
			Collection<Donation> donations;

			donations = this.repository.findDonationsBySponsorshipId(this.sponsorship.getId());
			atLeastOneDonation = !donations.isEmpty();

			super.state(atLeastOneDonation, "*", "acme.validation.sponsorship.donations.message");
		}
		{
			boolean correctDates;
			Date startMoment;
			Date endMoment;

			startMoment = this.sponsorship.getStartMoment();
			endMoment = this.sponsorship.getEndMoment();

			correctDates = startMoment != null && endMoment != null && MomentHelper.isBefore(startMoment, endMoment);

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
