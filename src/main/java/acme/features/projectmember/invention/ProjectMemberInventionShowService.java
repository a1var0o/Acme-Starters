
package acme.features.projectmember.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
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
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		tuple = super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");

		boolean hasProject = this.invention.getProject() != null;
		tuple.put("hasProject", hasProject);
		tuple.put("id", this.invention.getId());

		if (hasProject) {
			tuple.put("projectId", this.invention.getProject().getId());
			tuple.put("projectTitle", this.invention.getProject().getTitle());
		} else {
			int reqProjectId = super.getRequest().getData("projectId", int.class);
			tuple.put("projectId", reqProjectId);
		}
	}
}
