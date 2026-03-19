
package acme.features.fundraiser.tactic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.Strategy;
import acme.entities.Tactic;
import acme.entities.TacticKind;
import acme.realms.Fundraiser;

@Service
public class FundraiserTacticCreateService extends AbstractService<Fundraiser, Tactic> {

	@Autowired
	private FundraiserTacticRepository	repository;

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
		{
			if (this.tactic.getExpectedPercentage() != null) {
				boolean sumLowerThan100 = this.strategy.getExpectedPercentage() + this.tactic.getExpectedPercentage() <= 100;

				super.state(sumLowerThan100, "expectedPercentage", "acme.validation.tactic.total-sum-greater-than-100");
			}

		}
	}

	@Override
	public void execute() {
		this.repository.save(this.tactic);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		SelectChoices choices;

		choices = SelectChoices.from(TacticKind.class, this.tactic.getKind());

		tuple = super.unbindObject(this.tactic, "name", "notes", "expectedPercentage", "kind");
		tuple.put("strategyId", super.getRequest().getData("strategyId", int.class));
		tuple.put("draftMode", this.tactic.getStrategy().getDraftMode());
		tuple.put("kinds", choices);
	}
}
