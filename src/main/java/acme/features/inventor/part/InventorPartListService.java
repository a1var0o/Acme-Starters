
package acme.features.inventor.part;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.Invention;
import acme.entities.Part;
import acme.realms.Inventor;

@Service
public class InventorPartListService extends AbstractService<Inventor, Part> {

	@Autowired
	private InventorPartRepository	repository;

	private Invention				invention;
	private Collection<Part>		parts;


	@Override
	public void load() {
		int inventionId;
		inventionId = super.getRequest().getData("inventionId", int.class);

		this.invention = this.repository.findInvention(inventionId);
		this.parts = this.repository.findPartsByInvention(inventionId);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.invention != null && super.getRequest().getPrincipal().hasRealmOfType(Inventor.class) && this.invention.getInventor().getUserAccount().getId() == super.getRequest().getPrincipal().getAccountId();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.parts, "name", "description", "cost", "kind");
	}

}
