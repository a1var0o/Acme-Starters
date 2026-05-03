package acme.features.projectmember.invention;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.Invention;
import acme.entities.Project;
import acme.realms.Inventor;
import acme.realms.ProjectMember;

@Service
public class ProjectMemberInventionListAvailableService extends AbstractService<ProjectMember, Invention> {

	@Autowired
	private ProjectMemberInventionRepository repository;

	private Collection<Invention> inventions;
	private Project project;

	@Override
	public void load() {
		int projectId = super.getRequest().getData("projectId", int.class);
		this.project = this.repository.findProjectById(projectId);

		int accountId = super.getRequest().getPrincipal().getAccountId();
		Inventor inventor = this.repository.findInventorByAccountId(accountId);

		if (inventor != null) {
			this.inventions = this.repository.findAvailableInventions(inventor.getId());
		}
	}

	@Override
	public void authorise() {
		boolean status = super.getRequest().getPrincipal().hasRealmOfType(ProjectMember.class) && this.project != null;
		if (status) {
			int accountId = super.getRequest().getPrincipal().getAccountId();
			status = this.repository.countProjectMembership(this.project.getId(), accountId) > 0;
			if (status) {
				// The project must be unpublished to add inventions
				status = this.project.getDraftMode();
			}
		}
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.inventions, "ticker", "name", "description", "startMoment", "endMoment");
		super.unbindGlobal("projectId", this.project.getId());
	}
}
