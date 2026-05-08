
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
public class FundraiserStrategyShowService extends AbstractService<Fundraiser, Strategy> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private FundraiserStrategyRepository	repository;

	private Strategy						strategy;
	private Collection<Project>				projects;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.strategy = this.repository.findStrategyById(id);
		int accountId = super.getRequest().getPrincipal().getAccountId();
		Collection<Project> draftProjects = this.repository.findDraftProjectsByAccountId(accountId);
		this.projects = new java.util.ArrayList<>(draftProjects);
		if (this.strategy.getProject() != null && !this.projects.contains(this.strategy.getProject()))
			this.projects.add(this.strategy.getProject());
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.strategy != null && (this.strategy.getFundraiser().isPrincipal() || !this.strategy.getDraftMode());

		super.setAuthorised(status);
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
