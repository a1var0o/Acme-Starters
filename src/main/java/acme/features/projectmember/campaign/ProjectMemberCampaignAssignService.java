
package acme.features.projectmember.campaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.Campaign;
import acme.entities.Project;
import acme.realms.ProjectMember;

@Service
public class ProjectMemberCampaignAssignService extends AbstractService<ProjectMember, Campaign> {

	@Autowired
	private ProjectMemberCampaignRepository repository;

	private Campaign campaign;
	private Project project;

	@Override
	public void load() {
		int campaignId = super.getRequest().getData("id", int.class);
		this.campaign = this.repository.findCampaignById(campaignId);

		int projectId = super.getRequest().getData("projectId", int.class);
		this.project = this.repository.findProjectById(projectId);
	}

	@Override
	public void authorise() {
		boolean status = super.getRequest().getPrincipal().hasRealmOfType(ProjectMember.class) && this.campaign != null
				&& this.project != null;
		if (status) {
			int accountId = super.getRequest().getPrincipal().getAccountId();
			status = this.repository.isProjectMember(this.project.getId(), accountId);
			if (status) {
				status = this.project.getDraftMode();

				if (status && this.campaign.getSpokesperson() != null)
					status = this.campaign.getSpokesperson().getUserAccount().getId() == accountId;
			}
		}
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		// Do not bind properties as we only want to assign the project
	}

	@Override
	public void validate() {
		// No validation needed for properties
	}

	@Override
	public void execute() {
		this.campaign.setProject(this.project);
		this.repository.save(this.campaign);
	}

	@Override
	public void unbind() {
		super.getResponse().addGlobal("assigned", true);
		super.getResponse().addGlobal("projectId", this.project.getId());
	}
}
