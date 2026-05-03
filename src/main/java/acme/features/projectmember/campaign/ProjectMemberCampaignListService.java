package acme.features.projectmember.campaign;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.Campaign;
import acme.entities.Project;
import acme.realms.ProjectMember;

@Service
public class ProjectMemberCampaignListService extends AbstractService<ProjectMember, Campaign> {

	@Autowired
	private ProjectMemberCampaignRepository repository;

	private Collection<Campaign> campaigns;
	private Project project;

	@Override
	public void load() {
		int projectId = super.getRequest().getData("projectId", int.class);
		this.project = this.repository.findProjectById(projectId);
		this.campaigns = this.repository.findCampaignsByProjectId(projectId);
	}

	@Override
	public void authorise() {
		boolean status = super.getRequest().getPrincipal().hasRealmOfType(ProjectMember.class) && this.project != null;
		if (status) {
			int accountId = super.getRequest().getPrincipal().getAccountId();
			status = this.repository.countProjectMembership(this.project.getId(), accountId) > 0;
		}
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.campaigns, "ticker", "name", "description", "startMoment", "endMoment");
		super.unbindGlobal("projectId", this.project.getId());
	}
}
