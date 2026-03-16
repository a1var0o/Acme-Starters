
package acme.features.inventor.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.Invention;
import acme.realms.Inventor;

@Service
public class InventorInventionUpdateService extends AbstractService<Inventor, Invention> {

	@Autowired
	InventorInventionRepository	repository;

	Invention					invention;
	Inventor					inventor;


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);

		this.inventor = (Inventor) super.getRequest().getPrincipal().getActiveRealm();

		this.invention = this.repository.findInvention(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.invention != null && this.invention.getInventor().equals(this.inventor) && this.invention.getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void validate() {
		super.validateObject(this.invention);
	}

	@Override
	public void execute() {
		this.repository.save(this.invention);
	}

	@Override
	public void bind() {
		super.bindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void unbind() {
		super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

}
