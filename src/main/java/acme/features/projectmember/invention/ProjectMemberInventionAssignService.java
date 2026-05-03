package acme.features.projectmember.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.Invention;
import acme.entities.Project;
import acme.realms.ProjectMember;

@Service
public class ProjectMemberInventionAssignService extends AbstractService<ProjectMember, Invention> {

	@Autowired
	private ProjectMemberInventionRepository repository;

	private Invention invention;
	private Project project;

	@Override
	public void load() {
		int inventionId = super.getRequest().getData("id", int.class);
		this.invention = this.repository.findInventionById(inventionId);

		int projectId = super.getRequest().getData("customProjectId", int.class);
		this.project = this.repository.findProjectById(projectId);
	}

	@Override
	public void authorise() {
		boolean status = super.getRequest().getPrincipal().hasRealmOfType(ProjectMember.class) && this.invention != null
				&& this.project != null;
		if (status) {
			int accountId = super.getRequest().getPrincipal().getAccountId();
			status = this.repository.countProjectMembership(this.project.getId(), accountId) > 0;
			if (status) {
				// The project must be unpublished to add inventions
				status = this.project.getDraftMode();

				// The user must be the inventor of the invention
				if (status && this.invention.getInventor() != null) {
					status = this.invention.getInventor().getUserAccount().getId() == accountId;
				}
			}
		}
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.invention);
	}

	@Override
	public void validate() {
		super.validateObject(this.invention);
	}

	@Override
	public void execute() {
		this.invention.setProject(this.project);
		this.repository.save(this.invention);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
		super.unbindGlobal("projectId", this.project.getId());
	}
}
