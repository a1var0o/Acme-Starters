
package acme.features.authenticated.tactic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.Strategy;
import acme.entities.Tactic;
import acme.realms.Fundraiser;

@Service
public class AuthenticatedTacticShowService extends AbstractService<Fundraiser, Tactic> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedTacticRepository	repository;

	private Tactic							tactic;

	private Strategy						strategy;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.tactic = this.repository.findTacticById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.tactic.getStrategy().equals(this.strategy);
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.tactic, "name", "notes", "expectedPercentage", "kind", "strategy");
	}
}
