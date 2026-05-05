
package acme.features.projectmember.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.Project;
import acme.realms.ProjectMember;

@Service
public class ProjectMemberProjectShowService extends AbstractService<ProjectMember, Project> {

	@Autowired
	private ProjectMemberProjectRepository	repository;

	private Project							project;


	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		this.project = this.repository.findProjectById(id);
	}

	@Override
	public void authorise() {
		boolean status = super.getRequest().getPrincipal().hasRealmOfType(ProjectMember.class) && this.project != null;

		if (status) {
			int accountId = super.getRequest().getPrincipal().getAccountId();
			Collection<Project> involvedProjects = this.repository.findProjectsByProjectMemberAccountId(accountId);
			status = involvedProjects.contains(this.project);
		}

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.project, "title", "keywords", "description", "kickOffMoment", "closeOutMoment", "draftMode");
	}
}
