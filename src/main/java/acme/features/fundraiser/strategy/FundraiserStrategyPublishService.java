
package acme.features.fundraiser.strategy;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.Strategy;
import acme.entities.Tactic;
import acme.realms.Fundraiser;

@Service
public class FundraiserStrategyPublishService extends AbstractService<Fundraiser, Strategy> {

	@Autowired
	private FundraiserStrategyRepository	repository;
	private Strategy						strategy;


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.strategy = this.repository.findStrategyById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.strategy != null && this.strategy.getDraftMode() && this.strategy.getFundraiser().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.strategy);
		{
			boolean futureInterval;

			futureInterval = MomentHelper.isFuture(this.strategy.getStartMoment());
			super.state(futureInterval, "*", "acme.validation.strategy.no-future-interval.message");
		}
		{
			Collection<Tactic> tactics = this.repository.findTacticsByStrategyId(this.strategy.getId());
			boolean atLeastOnetactic = !tactics.isEmpty();

			super.state(atLeastOnetactic, "*", "acme.validation.strategy.no-tactics-and-published.message");
		}
		{
			Date start = this.strategy.getStartMoment();
			Date end = this.strategy.getEndMoment();
			boolean correctDates = MomentHelper.isBefore(start, end);

			super.state(correctDates, "*", "acme.validation.strategy.correct-interval.message");
		}
	}

	@Override
	public void execute() {
		this.strategy.setDraftMode(false);
		this.repository.save(this.strategy);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
	}
}
