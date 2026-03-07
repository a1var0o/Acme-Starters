
package acme.features.authenticated.tactic;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.Strategy;
import acme.entities.Tactic;
import acme.realms.Fundraiser;

@Service
public class AuthenticatedTacticListService extends AbstractService<Fundraiser, Tactic> {

	@Autowired
	private AuthenticatedTacticRepository	repository;

	private Collection<Tactic>				tactics;
	private Strategy						strategy;


	@Override
	public void load() {

		this.tactics = this.repository.findTacticsByStrategy(this.strategy.getId());
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.tactics, "name", "notes", "expectedPercentage", "kind", "strategy");
	}

}
