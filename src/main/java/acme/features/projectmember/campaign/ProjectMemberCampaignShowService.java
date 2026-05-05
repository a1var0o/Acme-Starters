package acme.features.projectmember.campaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.Campaign;
import acme.realms.ProjectMember;

@Service
public class ProjectMemberCampaignShowService extends AbstractService<ProjectMember, Campaign> {

	@Autowired
	private ProjectMemberCampaignRepository repository;

	private Campaign campaign;

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		this.campaign = this.repository.findCampaignById(id);
	}

	@Override
	public void authorise() {
		boolean status = this.campaign != null;
		if (status) {
			int accountId = super.getRequest().getPrincipal().getAccountId();
			int projectId;
			if (this.campaign.getProject() != null)
				projectId = this.campaign.getProject().getId();
			else
				projectId = super.getRequest().getData("projectId", int.class);
			status = this.repository.isProjectMember(projectId, accountId);
		}
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		int projectId;

		super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
		super.unbindGlobal("hasProject", this.campaign.getProject() != null);

		if (this.campaign.getProject() != null)
			projectId = this.campaign.getProject().getId();
		else
			projectId = super.getRequest().getData("projectId", int.class);

		super.unbindGlobal("projectId", projectId);
	}
}
