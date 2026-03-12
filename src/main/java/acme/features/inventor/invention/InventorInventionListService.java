
package acme.features.inventor.invention;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.Invention;
import acme.realms.Inventor;

@Service
public class InventorInventionListService extends AbstractService<Inventor, Invention> {

	@Autowired
	private InventorInventionRepository	repository;

	private Collection<Invention>		inventions;


	@Override
	public void load() {
		int inventorAccountId;

		inventorAccountId = super.getRequest().getPrincipal().getAccountId();
		this.inventions = this.repository.findInventionsByInventorAccountId(inventorAccountId);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRealmOfType(Inventor.class);
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.inventions, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}
}
