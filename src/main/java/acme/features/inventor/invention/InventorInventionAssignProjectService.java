
package acme.features.inventor.invention;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.Invention;
import acme.entities.Project;
import acme.realms.Inventor;

@Service
public class InventorInventionAssignProjectService extends AbstractService<Inventor, Invention> {

	@Autowired
	private InventorInventionRepository	repository;

	private Invention					invention;
	private Collection<Project>			projects;


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.invention = this.repository.findInvention(id);
		int accountId = super.getRequest().getPrincipal().getAccountId();
		this.projects = this.repository.findDraftProjectsByAccountId(accountId);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.invention != null && this.invention.getProject() == null && super.getRequest().getPrincipal().hasRealmOfType(Inventor.class) && this.invention.getInventor().getUserAccount().getId() == super.getRequest().getPrincipal().getAccountId();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.invention, "project");
	}

	@Override
	public void validate() {
		{
			boolean isDraft;
			isDraft = this.invention.getProject() != null && this.invention.getProject().getDraftMode();
			super.state(isDraft, "project", "acme.validation.assign.project.draft.message");
		}
	}

	@Override
	public void execute() {
		this.repository.save(this.invention);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		SelectChoices availableProjects;
		boolean hasProject = this.invention.getProject() != null;
		availableProjects = SelectChoices.from(this.projects, "title", this.invention.getProject());

		tuple = super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "project");
		tuple.put("projects", availableProjects);
		tuple.put("hasProject", hasProject);
	}
}
