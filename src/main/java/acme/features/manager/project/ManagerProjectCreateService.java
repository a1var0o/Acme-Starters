
package acme.features.manager.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.Member;
import acme.entities.Project;
import acme.realms.Manager;
import acme.realms.ProjectMember;

@Service
public class ManagerProjectCreateService extends AbstractService<Manager, Project> {

	@Autowired
	private ManagerProjectRepository	repository;

	private Project						project;
	private Manager						manager;
	private ProjectMember				projectMember;

	private Member						member;


	@Override
	public void load() {
		this.manager = (Manager) super.getRequest().getPrincipal().getActiveRealm();
		this.projectMember = this.repository.findProjectMemberByUserAccountId(super.getRequest().getPrincipal().getAccountId());

		this.project = super.newObject(Project.class);
		this.project.setDraftMode(true);
		this.project.setManager(this.manager);

		this.member = super.newObject(Member.class);
		this.member.setProject(this.project);
		this.member.setProjectMember(this.projectMember);
	}

	@Override
	public void authorise() {
		boolean status;
		String method;

		method = super.getRequest().getMethod();

		if (method.equals("GET"))
			status = true;
		else
			status = super.getRequest().getPrincipal().hasRealmOfType(Manager.class);
		super.setAuthorised(status);
	}

	@Override
	public void validate() {
		super.validateObject(this.project);
	}

	@Override
	public void execute() {
		this.repository.save(this.project);
		this.repository.save(this.member);
	}

	@Override
	public void bind() {
		super.bindObject(this.project, "title", "keywords", "description", "kickOffMoment", "closeOutMoment");
	}

	@Override
	public void unbind() {
		super.unbindObject(this.project, "title", "keywords", "description", "kickOffMoment", "closeOutMoment");
	}
}
