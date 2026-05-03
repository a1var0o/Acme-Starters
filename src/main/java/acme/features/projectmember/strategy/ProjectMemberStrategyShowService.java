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
		boolean status = super.getRequest().getPrincipal().hasRealmOfType(ProjectMember.class) && this.strategy != null && this.strategy.getProject() != null;
		if (status) {
			int accountId = super.getRequest().getPrincipal().getAccountId();
			status = this.repository.countProjectMembership(this.strategy.getProject().getId(), accountId) > 0;
		}
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
		super.unbindGlobal("projectId", this.strategy.getProject().getId());
	}
}
