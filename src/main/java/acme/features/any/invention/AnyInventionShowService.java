
package acme.features.any.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.Invention;

@Service
public class AnyInventionShowService extends AbstractService<Any, Invention> {

	@Autowired
	private AnyInventionRepository	repository;

	private Invention				invention;


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);

		this.invention = this.repository.findInvention(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.invention != null && !this.invention.getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

}
