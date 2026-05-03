
package acme.features.manager.project;

import java.util.Collection;
import java.util.Date;

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
	private ManagerProjectRepository	repository;

	private Project						project;


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
		if (this.project.getKickOffMoment() != null && this.project.getCloseOutMoment() != null) {
			// The CloseOutMoment must be after the KickOffMoment
			boolean validDates = this.project.getCloseOutMoment().after(this.project.getKickOffMoment());
			super.state(validDates, "closeOutMoment", "acme.validation.project.dates.message");
		}

		Collection<Invention> inventions = this.repository.findInventionsByProjectId(this.project.getId());
		Collection<Campaign> campaigns = this.repository.findCampaignsByProjectId(this.project.getId());
		Collection<Strategy> strategies = this.repository.findStrategiesByProjectId(this.project.getId());

		{
			boolean atLeastOneInvention = !inventions.isEmpty();
			super.state(atLeastOneInvention, "*", "acme.validation.project.inventions.message");
		}

		if (this.project.getKickOffMoment() != null) {
			boolean validStartMoments = true;
			for (Invention inv : inventions)
				if (inv.getStartMoment() != null && !this.project.getKickOffMoment().before(inv.getStartMoment())) {
					validStartMoments = false;
					break;
				}
			if (validStartMoments)
				for (Campaign cam : campaigns)
					if (cam.getStartMoment() != null && !this.project.getKickOffMoment().before(cam.getStartMoment())) {
						validStartMoments = false;
						break;
					}
			if (validStartMoments)
				for (Strategy str : strategies)
					if (str.getStartMoment() != null && !this.project.getKickOffMoment().before(str.getStartMoment())) {
						validStartMoments = false;
						break;
					}
			super.state(validStartMoments, "kickOffMoment", "acme.validation.project.kickOffMoment.message");
		}

		if (this.project.getCloseOutMoment() != null) {
			boolean validEndMoments = true;
			for (Invention inv : inventions)
				if (inv.getEndMoment() != null && !this.project.getCloseOutMoment().after(inv.getEndMoment())) {
					validEndMoments = false;
					break;
				}
			if (validEndMoments)
				for (Campaign cam : campaigns)
					if (cam.getEndMoment() != null && !this.project.getCloseOutMoment().after(cam.getEndMoment())) {
						validEndMoments = false;
						break;
					}
			if (validEndMoments)
				for (Strategy str : strategies)
					if (str.getEndMoment() != null && !this.project.getCloseOutMoment().after(str.getEndMoment())) {
						validEndMoments = false;
						break;
					}
			super.state(validEndMoments, "closeOutMoment", "acme.validation.project.closeOutMoment.message");
		}
	}

	@Override
	public void execute() {
		this.project.setDraftMode(false);
		this.project.setPublishMoment(new Date());

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
