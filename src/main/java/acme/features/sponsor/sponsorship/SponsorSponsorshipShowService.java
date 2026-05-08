
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.Project;
import acme.entities.Sponsorship;
import acme.realms.Sponsor;

@Service
public class SponsorSponsorshipShowService extends AbstractService<Sponsor, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorshipRepository	repository;

	private Sponsorship						sponsorship;

	@Autowired
	private SponsorProjectRepository		projectRepository;
	private Collection<Project>				projects;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.sponsorship = this.repository.findSponsorshipById(id);
		this.projects = this.projectRepository.findPublishedProjects();
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.sponsorship != null && //
			this.sponsorship.getSponsor().isPrincipal();

		super.setAuthorised(status);
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
