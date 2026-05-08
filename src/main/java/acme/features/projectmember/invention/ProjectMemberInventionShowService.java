
package acme.features.projectmember.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.Invention;
import acme.realms.ProjectMember;

@Service
public class ProjectMemberInventionShowService extends AbstractService<ProjectMember, Invention> {

	@Autowired
	private ProjectMemberInventionRepository	repository;

	private Invention							invention;


	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		this.invention = this.repository.findInventionById(id);
	}

	@Override
	public void authorise() {
		boolean status = this.invention != null;
		if (status) {
			int accountId = super.getRequest().getPrincipal().getAccountId();
			int projectId;
			if (this.invention.getProject() != null)
				projectId = this.invention.getProject().getId();
			else
				projectId = super.getRequest().getData("projectId", int.class);
			status = this.repository.isProjectMember(projectId, accountId);
		}
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		int projectId;

		super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
		super.unbindGlobal("hasProject", this.invention.getProject() != null);

		if (this.invention.getProject() != null)
			projectId = this.invention.getProject().getId();
		else
			projectId = super.getRequest().getData("projectId", int.class);

		super.unbindGlobal("projectId", projectId);
	}
}
