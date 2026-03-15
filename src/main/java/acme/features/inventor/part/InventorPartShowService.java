
package acme.features.inventor.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.Part;
import acme.realms.Inventor;

@Service
public class InventorPartShowService extends AbstractService<Inventor, Part> {

	@Autowired
	private InventorPartRepository	repository;

	private Part					part;


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);

		this.part = this.repository.findPart(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.part != null && super.getRequest().getPrincipal().hasRealmOfType(Inventor.class) && this.part.getInvention().getInventor().getUserAccount().getId() == super.getRequest().getPrincipal().getAccountId();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.part, "name", "description", "cost", "kind");
	}
}
