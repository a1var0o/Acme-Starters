
package acme.features.projectmember.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.Project;
import acme.entities.Strategy;
import acme.realms.ProjectMember;

@Service
public class ProjectMemberStrategyAssignService extends AbstractService<ProjectMember, Strategy> {

	@Autowired
	private ProjectMemberStrategyRepository repository;

	private Strategy strategy;
	private Project project;

	@Override
	public void load() {
		int strategyId = super.getRequest().getData("id", int.class);
		this.strategy = this.repository.findStrategyById(strategyId);

		int projectId = super.getRequest().getData("projectId", int.class);
		this.project = this.repository.findProjectById(projectId);
	}

	@Override
	public void authorise() {
		boolean status = super.getRequest().getPrincipal().hasRealmOfType(ProjectMember.class) && this.strategy != null
				&& this.project != null;
		if (status) {
			int accountId = super.getRequest().getPrincipal().getAccountId();
			status = this.repository.isProjectMember(this.project.getId(), accountId);
			if (status) {
				status = this.project.getDraftMode();

				if (status && this.strategy.getFundraiser() != null)
					status = this.strategy.getFundraiser().getUserAccount().getId() == accountId;
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
		this.strategy.setProject(this.project);
		this.repository.save(this.strategy);
	}

	@Override
	public void unbind() {
		super.getResponse().addGlobal("assigned", true);
		super.getResponse().addGlobal("projectId", this.project.getId());
	}
}
