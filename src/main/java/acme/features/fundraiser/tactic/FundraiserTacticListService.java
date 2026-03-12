
package acme.features.fundraiser.tactic;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.Strategy;
import acme.entities.Tactic;
import acme.realms.Fundraiser;

@Service
public class FundraiserTacticListService extends AbstractService<Fundraiser, Tactic> {

	@Autowired
	private FundraiserTacticRepository	repository;

	private Collection<Tactic>			tactics;
	private Strategy					strategy;


	@Override
	public void load() {

		int strategyId = super.getRequest().getData("strategyId", int.class);
		this.strategy = this.repository.findStrategyById(strategyId);
		this.tactics = this.repository.findTacticsByStrategy(strategyId);
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.strategy != null && (this.strategy.getFundraiser().isPrincipal() || !this.strategy.getDraftMode());
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		boolean showCreate;
		super.unbindObjects(this.tactics, "name", "notes", "expectedPercentage", "kind");
		showCreate = this.strategy.getDraftMode() && this.strategy.getFundraiser().isPrincipal();
		super.unbindGlobal("strategyId", this.strategy.getId());
		super.unbindGlobal("showCreate", showCreate);
	}

}
