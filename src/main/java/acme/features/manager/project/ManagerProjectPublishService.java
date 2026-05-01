package acme.features.manager.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.Campaign;
import acme.entities.Invention;
import acme.entities.Project;
import acme.entities.Strategy;
import acme.realms.Manager;

@Service
public class ManagerProjectPublishService extends AbstractService<Manager, Project> {

	@Autowired
	private ManagerProjectRepository repository;

	private Project project;

	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.project = this.repository.findProject(id);
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.project != null && this.project.getDraftMode() && this.project.getManager().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.project, "title", "keywords", "description", "kickOffMoment", "closeOutMoment");
	}

	@Override
	public void validate() {
		super.validateObject(this.project);
		{
			Collection<Invention> inventions = this.repository.findInventionsByProjectId(this.project.getId());
			boolean atLeastOneInvention = !inventions.isEmpty();

			super.state(atLeastOneInvention, "*", "acme.validation.project.inventions.message");
		}
	}

	@Override
	public void execute() {
		this.project.setDraftMode(false);

		Collection<Invention> inventions = this.repository.findInventionsByProjectId(this.project.getId());
		for (Invention invention : inventions) {
			invention.setDraftMode(false);
			this.repository.save(invention);
		}

		Collection<Campaign> campaigns = this.repository.findCampaignsByProjectId(this.project.getId());
		for (Campaign campaign : campaigns) {
			campaign.setDraftMode(false);
			this.repository.save(campaign);
		}

		Collection<Strategy> strategies = this.repository.findStrategiesByProjectId(this.project.getId());
		for (Strategy strategy : strategies) {
			strategy.setDraftMode(false);
			this.repository.save(strategy);
		}

		this.repository.save(this.project);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.project, "title", "keywords", "description", "kickOffMoment", "closeOutMoment", "draftMode");
	}

}
