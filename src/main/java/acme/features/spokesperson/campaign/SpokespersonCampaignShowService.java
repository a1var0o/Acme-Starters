
package acme.features.spokesperson.campaign;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.Campaign;
import acme.entities.Project;
import acme.realms.Spokesperson;

@Service
public class SpokespersonCampaignShowService extends AbstractService<Spokesperson, Campaign> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private SpokespersonCampaignRepository	repository;

	private Campaign						campaign;
	private Collection<Project>				projects;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.campaign = this.repository.findCampaignById(id);
		int accountId = super.getRequest().getPrincipal().getAccountId();
		Collection<Project> draftProjects = this.repository.findDraftProjectsByAccountId(accountId);
		this.projects = new java.util.ArrayList<>(draftProjects);
		if (this.campaign.getProject() != null && !this.projects.contains(this.campaign.getProject()))
			this.projects.add(this.campaign.getProject());
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.campaign != null && (this.campaign.getSpokesperson().isPrincipal() || !this.campaign.getDraftMode());

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		SelectChoices availableProjects;
		boolean hasProject = this.campaign.getProject() != null;
		availableProjects = SelectChoices.from(this.projects, "title", this.campaign.getProject());

		tuple = super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "project");
		tuple.put("projects", availableProjects);
		tuple.put("hasProject", hasProject);
	}
}
