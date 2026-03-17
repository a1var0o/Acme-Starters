
package acme.features.inventor.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.Invention;
import acme.entities.Part;
import acme.entities.PartKind;
import acme.realms.Inventor;

@Service
public class InventorPartCreateService extends AbstractService<Inventor, Part> {

	@Autowired
	private InventorPartRepository	repository;

	private Invention				invention;
	private Part					part;


	@Override
	public void load() {
		int inventionId;

		inventionId = super.getRequest().getData("inventionId", int.class);
		this.invention = this.repository.findInvention(inventionId);

		this.part = super.newObject(Part.class);
		this.part.setInvention(this.invention);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.invention != null && //
			this.part.getInvention().getInventor().isPrincipal() && //
			this.part.getInvention().getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.part, "name", "description", "cost", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.part);
	}

	@Override
	public void execute() {
		this.repository.save(this.part);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		SelectChoices partKinds;

		partKinds = SelectChoices.from(PartKind.class, this.part.getKind());

		tuple = super.unbindObject(this.part, "name", "description", "cost", "kind");
		tuple.put("inventionId", super.getRequest().getData("inventionId", int.class));
		tuple.put("draftMode", this.part.getInvention().getDraftMode());
		tuple.put("partKinds", partKinds);
	}
}
