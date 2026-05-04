package acme.features.projectmember.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.Strategy;
import acme.realms.ProjectMember;

@Service
public class ProjectMemberStrategyShowService extends AbstractService<ProjectMember, Strategy> {

	@Autowired
	private ProjectMemberStrategyRepository repository;

	private Strategy strategy;

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		this.strategy = this.repository.findStrategyById(id);
	}

	@Override
	public void authorise() {
		boolean status = this.strategy != null;
		if (status) {
			int accountId = super.getRequest().getPrincipal().getAccountId();
			int projectId;
			if (this.strategy.getProject() != null)
				projectId = this.strategy.getProject().getId();
			else
				projectId = super.getRequest().getData("projectId", int.class);
			status = this.repository.isProjectMember(projectId, accountId);
		}
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		int projectId;

		super.unbindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
		super.unbindGlobal("hasProject", this.strategy.getProject() != null);

		if (this.strategy.getProject() != null)
			projectId = this.strategy.getProject().getId();
		else
			projectId = super.getRequest().getData("projectId", int.class);

		super.unbindGlobal("projectId", projectId);
	}
}
