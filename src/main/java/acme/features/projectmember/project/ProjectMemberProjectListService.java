package acme.features.projectmember.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.Project;
import acme.realms.ProjectMember;

@Service
public class ProjectMemberProjectListService extends AbstractService<ProjectMember, Project> {

	@Autowired
	private ProjectMemberProjectRepository repository;

	private Collection<Project> projects;

	@Override
	public void load() {
		int accountId = super.getRequest().getPrincipal().getAccountId();
		this.projects = this.repository.findProjectsByProjectMemberAccountId(accountId);
	}

	@Override
	public void authorise() {
		boolean status = super.getRequest().getPrincipal().hasRealmOfType(ProjectMember.class);
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.projects, "title", "keywords", "description", "kickOffMoment", "closeOutMoment",
				"draftMode");
	}
}
