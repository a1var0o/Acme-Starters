
package acme.features.manager.project;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;

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
		if (!super.getErrors().hasErrors("kickOffMoment") && !super.getErrors().hasErrors("closeOutMoment")) {
			if (this.project.getKickOffMoment() != null && this.project.getCloseOutMoment() != null) {
				// The CloseOutMoment must be after the KickOffMoment
				boolean validDates = this.project.getCloseOutMoment().after(this.project.getKickOffMoment());
				super.state(validDates, "closeOutMoment", "acme.validation.project.dates.message");
			}
		}

		long countInventions = this.repository.countInventionsByProjectId(this.project.getId());
		super.state(countInventions > 0, "*", "acme.validation.project.inventions.message");

		if (!super.getErrors().hasErrors("kickOffMoment") && this.project.getKickOffMoment() != null) {
			long invalidStarts = 0;
			invalidStarts += this.repository.countInventionsWithStartMomentBefore(this.project.getId(), this.project.getKickOffMoment());
			invalidStarts += this.repository.countCampaignsWithStartMomentBefore(this.project.getId(), this.project.getKickOffMoment());
			invalidStarts += this.repository.countStrategiesWithStartMomentBefore(this.project.getId(), this.project.getKickOffMoment());
			super.state(invalidStarts == 0, "kickOffMoment", "acme.validation.project.kickOffMoment.message");
		}

		if (!super.getErrors().hasErrors("closeOutMoment") && this.project.getCloseOutMoment() != null) {
			long invalidEnds = 0;
			invalidEnds += this.repository.countInventionsWithEndMomentAfter(this.project.getId(), this.project.getCloseOutMoment());
			invalidEnds += this.repository.countCampaignsWithEndMomentAfter(this.project.getId(), this.project.getCloseOutMoment());
			invalidEnds += this.repository.countStrategiesWithEndMomentAfter(this.project.getId(), this.project.getCloseOutMoment());
			super.state(invalidEnds == 0, "closeOutMoment", "acme.validation.project.closeOutMoment.message");
		}

		Date now = MomentHelper.getCurrentMoment();
		long pastStarts = 0;
		pastStarts += this.repository.countInventionsWithStartMomentInPast(this.project.getId(), now);
		pastStarts += this.repository.countCampaignsWithStartMomentInPast(this.project.getId(), now);
		pastStarts += this.repository.countStrategiesWithStartMomentInPast(this.project.getId(), now);
		super.state(pastStarts == 0, "*", "acme.validation.project.no-future-interval.message");

		long invalidIntervals = 0;
		invalidIntervals += this.repository.countInventionsWithInvalidInterval(this.project.getId());
		invalidIntervals += this.repository.countCampaignsWithInvalidInterval(this.project.getId());
		invalidIntervals += this.repository.countStrategiesWithInvalidInterval(this.project.getId());
		super.state(invalidIntervals == 0, "*", "acme.validation.project.invalid-interval.message");

		long itemsWithoutChildren = 0;
		itemsWithoutChildren += this.repository.countInventionsWithoutParts(this.project.getId());
		itemsWithoutChildren += this.repository.countCampaignsWithoutMilestones(this.project.getId());
		itemsWithoutChildren += this.repository.countStrategiesWithoutTactics(this.project.getId());
		super.state(itemsWithoutChildren == 0, "*", "acme.validation.project.items-without-children.message");
	}

	@Override
	public void execute() {
		this.project.setDraftMode(false);
		this.project.setPublishMoment(MomentHelper.getCurrentMoment());

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
