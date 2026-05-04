
package acme.features.projectmember.campaign;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.Campaign;
import acme.entities.Project;
import acme.realms.ProjectMember;
import acme.realms.Spokesperson;

@Service
public class ProjectMemberCampaignListAvailableService extends AbstractService<ProjectMember, Campaign> {

	@Autowired
	private ProjectMemberCampaignRepository repository;

	private Collection<Campaign> campaigns;
	private Project project;

	@Override
	public void load() {
		int projectId = super.getRequest().getData("projectId", int.class);
		this.project = this.repository.findProjectById(projectId);

		int accountId = super.getRequest().getPrincipal().getAccountId();
		Spokesperson spokesperson = this.repository.findSpokespersonByAccountId(accountId);

		if (spokesperson != null)
			this.campaigns = this.repository.findAvailableCampaigns(spokesperson.getId());

		super.getResponse().addGlobal("projectId", this.project.getId());
		super.getResponse().addGlobal("campaigns", this.campaigns);
	}

	@Override
	public void authorise() {
		boolean status = super.getRequest().getPrincipal().hasRealmOfType(ProjectMember.class) && this.project != null;
		if (status) {
			int accountId = super.getRequest().getPrincipal().getAccountId();
			status = this.repository.isProjectMember(this.project.getId(), accountId);
			if (status)
				status = this.project.getDraftMode();
		}
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.campaigns, "ticker", "name", "description", "startMoment", "endMoment");
		super.unbindGlobal("projectId", this.project.getId());
	}
}
