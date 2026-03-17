
package acme.features.inventor.invention;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.Invention;
import acme.realms.Inventor;

@Service
public class InventorInventionPublishService extends AbstractService<Inventor, Invention> {

	@Autowired
	private InventorInventionRepository	repository;

	private Invention					invention;


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);

		this.invention = this.repository.findInvention(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.invention != null && this.invention.getDraftMode() && this.invention.getInventor().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void validate() {
		super.validateObject(this.invention);
		{
			boolean validInterval;

			Date startMoment = this.invention.getStartMoment();
			Date endMoment = this.invention.getEndMoment();

			if (startMoment != null && endMoment != null) {
				validInterval = MomentHelper.isBefore(startMoment, endMoment);
				super.state(validInterval, "*", "acme.validation.invention.interval.message");
			}
		}
		{
			boolean validStart;

			Date startMoment = this.invention.getStartMoment();

			if (startMoment != null) {
				validStart = MomentHelper.isFuture(startMoment);
				super.state(validStart, "*", "acme.validation.invention.start.message");
			}
		}
		{
			boolean enoughParts;

			enoughParts = !this.repository.findPartsByInventionId(this.invention.getId()).isEmpty();

			super.state(enoughParts, "*", "acme.validation.invention.parts.message");
		}
	}

	@Override
	public void execute() {
		this.invention.setDraftMode(false);
		this.repository.save(this.invention);
	}

	@Override
	public void bind() {
		super.bindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void unbind() {
		super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
	}

}
