package acme.features.fundraiser.strategy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.Project;
import acme.entities.Strategy;
import acme.realms.Fundraiser;

@Service
public class FundraiserStrategyAssignProjectService extends AbstractService<Fundraiser, Strategy> {

	@Autowired
	private FundraiserStrategyRepository	repository;

	private Strategy						strategy;
	private Collection<Project>			projects;

	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.strategy = this.repository.findStrategyById(id);
		int accountId = super.getRequest().getPrincipal().getAccountId();
		this.projects = this.repository.findDraftProjectsByAccountId(accountId);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.strategy != null && this.strategy.getProject() == null && super.getRequest().getPrincipal().hasRealmOfType(Fundraiser.class) && this.strategy.getFundraiser().getUserAccount().getId() == super.getRequest().getPrincipal().getAccountId();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.strategy, "project");
	}

	@Override
	public void validate() {
		{
			boolean isDraft;
			isDraft = this.strategy.getProject() != null && this.strategy.getProject().getDraftMode() == true;
			super.state(isDraft, "project", "acme.validation.assign.project.draft.message");
		}
	}

	@Override
	public void execute() {
		this.repository.save(this.strategy);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		SelectChoices availableProjects;
		boolean hasProject = this.strategy.getProject() != null;
		availableProjects = SelectChoices.from(this.projects, "title", this.strategy.getProject());
		
		tuple = super.unbindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "project");
		tuple.put("projects", availableProjects);
		tuple.put("hasProject", hasProject);
	}
}
