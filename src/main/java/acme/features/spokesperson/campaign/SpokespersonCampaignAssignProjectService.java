
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
public class SpokespersonCampaignAssignProjectService extends AbstractService<Spokesperson, Campaign> {

	@Autowired
	private SpokespersonCampaignRepository	repository;

	private Campaign						campaign;
	private Collection<Project>				projects;


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.campaign = this.repository.findCampaignById(id);
		int accountId = super.getRequest().getPrincipal().getAccountId();
		this.projects = this.repository.findDraftProjectsByAccountId(accountId);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.campaign != null && this.campaign.getProject() == null && super.getRequest().getPrincipal().hasRealmOfType(Spokesperson.class)
			&& this.campaign.getSpokesperson().getUserAccount().getId() == super.getRequest().getPrincipal().getAccountId();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.campaign, "project");
	}

	@Override
	public void validate() {
		{
			boolean isDraft;
			isDraft = this.campaign.getProject() != null && this.campaign.getProject().getDraftMode();
			super.state(isDraft, "project", "acme.validation.assign.project.draft.message");
		}
	}

	@Override
	public void execute() {
		this.repository.save(this.campaign);
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
