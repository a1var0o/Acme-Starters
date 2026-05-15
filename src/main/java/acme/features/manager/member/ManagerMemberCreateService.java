
package acme.features.manager.member;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.Member;
import acme.entities.Project;
import acme.realms.Manager;
import acme.realms.ProjectMember;

@Service
public class ManagerMemberCreateService extends AbstractService<Manager, Member> {

	@Autowired
	private ManagerMemberRepository		repository;

	private Project						project;
	private Member						member;

	private Collection<ProjectMember>	projectMembers;


	@Override
	public void load() {
		int projectId;

		projectId = super.getRequest().getData("projectId", int.class);

		this.projectMembers = this.repository.findAllProjectMembersNotInProject(projectId);

		this.project = this.repository.findProject(projectId);

		this.member = super.newObject(Member.class);
		this.member.setProject(this.project);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.project != null && this.project.getManager().isPrincipal() && this.project.getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void validate() {
		super.validateObject(this.member);
	}

	@Override
	public void execute() {
		this.repository.save(this.member);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		SelectChoices availableUsers;
		availableUsers = SelectChoices.from(this.projectMembers, "userAccount.username", this.member.getProjectMember());

		tuple = super.unbindObject(this.member, "project.title", "projectMember");
		tuple.put("users", availableUsers);
		tuple.put("projectId", this.project.getId());
	}

	@Override
	public void bind() {
		super.bindObject(this.member, "projectMember");
	}

}
