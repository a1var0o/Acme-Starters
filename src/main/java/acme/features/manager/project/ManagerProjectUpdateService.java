
package acme.features.manager.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.Project;
import acme.realms.Manager;

@Service
public class ManagerProjectUpdateService extends AbstractService<Manager, Project> {

	@Autowired
	ManagerProjectRepository	repository;

	Project						project;
	Manager						manager;


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);

		this.manager = (Manager) super.getRequest().getPrincipal().getActiveRealm();

		this.project = this.repository.findProject(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.project != null && //
			this.project.getDraftMode() && //
			this.project.getManager().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void validate() {
		super.validateObject(this.project);
	}

	@Override
	public void execute() {
		this.repository.save(this.project);
	}

	@Override
	public void bind() {
		super.bindObject(this.project, "title", "keywords", "description", "kickOffMoment", "closeOutMoment");
	}

	@Override
	public void unbind() {
		super.unbindObject(this.project, "title", "keywords", "description", "kickOffMoment", "closeOutMoment", "draftMode");
	}

}
