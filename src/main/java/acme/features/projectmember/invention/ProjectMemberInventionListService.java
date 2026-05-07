
package acme.features.projectmember.invention;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.Invention;
import acme.entities.Project;
import acme.realms.ProjectMember;

@Service
public class ProjectMemberInventionListService extends AbstractService<ProjectMember, Invention> {

	@Autowired
	private ProjectMemberInventionRepository	repository;

	private Collection<Invention>				inventions;
	private Project								project;


	@Override
	public void load() {
		int projectId = super.getRequest().getData("projectId", int.class);
		this.project = this.repository.findProjectById(projectId);
		this.inventions = this.repository.findInventionsByProjectId(projectId);
	}

	@Override
	public void authorise() {
		boolean status = super.getRequest().getPrincipal().hasRealmOfType(ProjectMember.class) && this.project != null;
		if (status) {
			int accountId = super.getRequest().getPrincipal().getAccountId();
			status = this.repository.isProjectMember(this.project.getId(), accountId);
		}
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.inventions, "ticker", "name", "description", "startMoment", "endMoment");
		super.unbindGlobal("projectId", this.project.getId());
		super.unbindGlobal("projectDraftMode", this.project.getDraftMode());
	}
}
