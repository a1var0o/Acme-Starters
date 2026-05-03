package acme.features.projectmember.strategy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.Project;
import acme.entities.Strategy;
import acme.realms.ProjectMember;

@Service
public class ProjectMemberStrategyListService extends AbstractService<ProjectMember, Strategy> {

	@Autowired
	private ProjectMemberStrategyRepository repository;

	private Collection<Strategy> strategies;
	private Project project;

	@Override
	public void load() {
		int projectId = super.getRequest().getData("projectId", int.class);
		this.project = this.repository.findProjectById(projectId);
		this.strategies = this.repository.findStrategiesByProjectId(projectId);
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
		super.unbindObjects(this.strategies, "ticker", "name", "description", "startMoment", "endMoment");
		super.unbindGlobal("projectId", this.project.getId());
	}
}
