
package acme.features.manager.member;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.Member;
import acme.entities.Project;
import acme.realms.Manager;

@Service
public class ManagerMemberListService extends AbstractService<Manager, Member> {

	@Autowired
	private ManagerMemberRepository	repository;

	private Project					project;
	private Collection<Member>		members;


	@Override
	public void load() {
		int projectId;

		projectId = super.getRequest().getData("projectId", int.class);
		this.project = this.repository.findProject(projectId);
		this.members = this.repository.getMembersByProjectId(projectId);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.project != null && this.project.getManager().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.members, "projectMember.userAccount.username");
	}

}
