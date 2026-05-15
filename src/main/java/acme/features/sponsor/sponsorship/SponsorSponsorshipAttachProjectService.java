
package acme.features.sponsor.sponsorship;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.views.SelectChoices;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.Project;
import acme.entities.Sponsorship;
import acme.realms.Sponsor;

@Service
public class SponsorSponsorshipAttachProjectService extends AbstractService<Sponsor, Sponsorship> {

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
		projectId = super.getRequest().getData("projectId", int.class);
		int sponsorId = super.getRequest().getPrincipal().getAccountId();
		this.sponsorships = this.sponsorshipRepository.findPublishSponsorships(sponsorId);
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
		super.bindObject(this.sponsorship, "sponsorship");
		super.unbindGlobal("id", this.sponsorship.getId());
	}

	@Override
	public void validate() {

		{
			boolean interval;
			Date startMoment;
			Date publishMoment;
			interval = this.sponsorship.getProject() != null;
			if (interval) {
				startMoment = this.sponsorship.getStartMoment();
				publishMoment = this.sponsorship.getProject().getPublishMoment();
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
		SelectChoices availableSponsorships;
		availableSponsorships = SelectChoices.from(this.sponsorships, "ticker", this.sponsorship);
		super.unbindGlobal("sponsorship", this.sponsorship);
		super.unbindGlobal("project", this.project.getTitle());
		super.unbindGlobal("sponsorships", availableSponsorships);
		super.unbindGlobal("projectId", this.project.getId());

	}
}
