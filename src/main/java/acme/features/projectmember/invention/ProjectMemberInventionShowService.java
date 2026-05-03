
package acme.features.projectmember.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.Invention;
import acme.realms.ProjectMember;

@Service
public class ProjectMemberInventionShowService extends AbstractService<ProjectMember, Invention> {

	@Autowired
	private ProjectMemberInventionRepository repository;

	private Invention invention;

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		this.invention = this.repository.findInventionById(id);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo",
				"draftMode");

		boolean hasProject = this.invention.getProject() != null;
		super.unbindGlobal("hasProject", hasProject);
		super.unbindGlobal("id", this.invention.getId());

		if (hasProject) {
			super.unbindGlobal("projectId", this.invention.getProject().getId());
			super.unbindGlobal("projectTitle", this.invention.getProject().getTitle());
		} else {
			if (super.getRequest().hasData("projectId")) {
				int reqProjectId = super.getRequest().getData("projectId", int.class);
				super.unbindGlobal("projectId", reqProjectId);
			}
		}
	}
}
