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
		boolean status = super.getRequest().getPrincipal().hasRealmOfType(ProjectMember.class) && this.campaign != null && this.campaign.getProject() != null;
		if (status) {
			int accountId = super.getRequest().getPrincipal().getAccountId();
			status = this.repository.countProjectMembership(this.campaign.getProject().getId(), accountId) > 0;
		}
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
		super.unbindGlobal("projectId", this.campaign.getProject().getId());
	}
}
