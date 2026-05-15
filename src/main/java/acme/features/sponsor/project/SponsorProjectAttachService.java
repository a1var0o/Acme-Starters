
package acme.features.sponsor.project;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.Project;
import acme.entities.Sponsorship;
import acme.features.sponsor.sponsorship.SponsorSponsorshipRepository;
import acme.realms.Sponsor;

@Service
public class SponsorProjectAttachService extends AbstractService<Sponsor, Project> {

	@Autowired
	private SponsorSponsorshipRepository	sponsorshipRepository;
	private Collection<Sponsorship>			sponsorships;
	@Autowired
	private SponsorProjectRepository		projectRepository;
	private Project							project;
	private Sponsorship						sponsorship;


	@Override
	public void load() {
		int projectId;
		projectId = super.getRequest().getData("id", int.class);
		int sponsorId = super.getRequest().getPrincipal().getAccountId();
		this.sponsorships = this.sponsorshipRepository.findPublishAndNotAttachedSponsorships(sponsorId);
		this.project = this.projectRepository.findProjectById(projectId);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.project != null && !this.project.getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		int sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
		this.sponsorship = this.sponsorshipRepository.findSponsorshipById(sponsorshipId);
	}

	@Override
	public void validate() {

		{
			boolean interval;
			Date startMoment;
			Date publishMoment;
			interval = this.sponsorship != null;
			if (interval) {
				startMoment = this.sponsorship.getStartMoment();
				publishMoment = this.project.getPublishMoment();
				interval = startMoment != null && publishMoment != null && MomentHelper.isAfter(startMoment, publishMoment);
			}

			super.state(interval, "*", "acme.validation.attach.project.interval.message");
		}
	}

	@Override
	public void execute() {
		this.sponsorship.setProject(this.project);
		this.sponsorshipRepository.save(this.sponsorship);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		SelectChoices availableSponsorships;
		availableSponsorships = SelectChoices.from(this.sponsorships, "ticker", this.sponsorship);
		tuple = super.unbindObject(this.project, "title");
		tuple.put("sponsorships", availableSponsorships);

	}
}
