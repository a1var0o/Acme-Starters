
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.Member;
import acme.entities.Project;
import acme.realms.Manager;

@Service
public class ManagerProjectDeleteService extends AbstractService<Manager, Project> {

	@Autowired
	private ManagerProjectRepository	repository;
	private Project						project;


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.project = this.repository.findProject(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.project != null && this.project.getDraftMode() && this.project.getManager().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.project, "title", "keywords", "description", "kickOffMoment", "closeOutMoment");
	}

	@Override
	public void validate() {
		;
	}

	@Override
	public void execute() {
		Collection<Member> members;

		members = this.repository.findMembersByProjectId(this.project.getId());
		this.repository.deleteAll(members);
		this.repository.delete(this.project);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.project, "title", "keywords", "description", "kickOffMoment", "closeOutMoment", "draftMode");
	}
}
