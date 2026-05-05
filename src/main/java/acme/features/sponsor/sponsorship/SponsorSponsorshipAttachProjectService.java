
package acme.features.sponsor.sponsorship;

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
import acme.realms.Sponsor;

@Service
public class SponsorSponsorshipAttachProjectService extends AbstractService<Sponsor, Sponsorship> {

	@Autowired
	private SponsorSponsorshipRepository	sponsorshipRepository;
	private Sponsorship						sponsorship;
	@Autowired
	private SponsorProjectRepository		projectRepository;
	private Collection<Project>				projects;


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.sponsorship = this.sponsorshipRepository.findSponsorshipById(id);
		this.projects = this.projectRepository.findPublishedProjects();
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.sponsorship != null && !this.sponsorship.getDraftMode() && this.sponsorship.getSponsor().isPrincipal() && this.sponsorship.getProject() == null;
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.sponsorship, "project");
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

			super.state(interval, "project", "acme.validation.attach.project.interval.message");
		}
	}

	@Override
	public void execute() {
		this.sponsorshipRepository.save(this.sponsorship);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		SelectChoices availableProjects;
		boolean hasProject = this.sponsorship.getProject() != null;
		availableProjects = SelectChoices.from(this.projects, "title", this.sponsorship.getProject());
		tuple = super.unbindObject(this.sponsorship, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "sponsor", "project");
		tuple.put("projects", availableProjects);
		tuple.put("hasProject", hasProject);
	}
}
