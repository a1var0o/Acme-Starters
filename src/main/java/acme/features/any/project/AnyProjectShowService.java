
package acme.features.any.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.Project;

@Service
public class AnyProjectShowService extends AbstractService<Any, Project> {

	@Autowired
	private AnyProjectRepository	repository;

	private Project					project;


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);

		this.project = this.repository.findProject(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.project != null && !this.project.getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.project, "title", "keywords", "description", "kickOffMoment", "closeOutMoment", "draftMode");
	}
}
