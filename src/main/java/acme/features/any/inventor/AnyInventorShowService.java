
package acme.features.any.inventor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.Invention;
import acme.realms.Inventor;

@Service
public class AnyInventorShowService extends AbstractService<Any, Inventor> {

	@Autowired
	private AnyInventorRepository	repository;

	private Invention				invention;
	private Inventor				inventor;


	@Override
	public void load() {
		int inventionId;
		inventionId = super.getRequest().getData("id", int.class);

		this.invention = this.repository.findInvention(inventionId);
		this.inventor = this.invention.getInventor();
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.invention != null;
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.inventor, "bio", "keyWords", "licensed");
	}
}
