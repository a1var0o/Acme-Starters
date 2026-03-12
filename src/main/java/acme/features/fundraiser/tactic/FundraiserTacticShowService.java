
package acme.features.fundraiser.tactic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.Tactic;
import acme.realms.Fundraiser;

@Service
public class FundraiserTacticShowService extends AbstractService<Fundraiser, Tactic> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private FundraiserTacticRepository	repository;

	private Tactic						tactic;

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
		status = this.tactic != null && (this.tactic.getStrategy().getFundraiser().isPrincipal() || !this.tactic.getStrategy().getDraftMode());
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.tactic, "name", "notes", "expectedPercentage", "kind");
		tuple.put("strategyId", this.tactic.getStrategy().getId());
		tuple.put("draftMode", this.tactic.getStrategy().getDraftMode());
	}
}
