
package acme.features.fundraiser.tactic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.Strategy;
import acme.entities.Tactic;
import acme.realms.Fundraiser;

@Service
public class FundraiserTacticCreateService extends AbstractService<Fundraiser, Tactic> {

	@Autowired
	private FundraiserTacticRepository	repository;

	private Fundraiser					fundraiser;
	private Strategy					strategy;
	private Tactic						tactic;


	@Override
	public void load() {
		int strategyId;

		strategyId = super.getRequest().getData("strategyId", int.class);
		this.strategy = this.repository.findStrategyById(strategyId);

		this.tactic = super.newObject(Tactic.class);
		this.tactic.setStrategy(this.strategy);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.strategy != null && this.tactic.getStrategy().getFundraiser().isPrincipal() && this.tactic.getStrategy().getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.tactic, "name", "notes", "expectedPercentage", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.tactic);
	}

	@Override
	public void execute() {
		this.repository.save(this.tactic);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.tactic, "name", "notes", "expectedPercentage", "kind");
		tuple.put("strategyId", super.getRequest().getData("strategyId", int.class));
		tuple.put("draftMode", this.tactic.getStrategy().getDraftMode());
	}
}
