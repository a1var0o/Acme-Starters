
package acme.constraints;

import java.util.Collection;
import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.Strategy;
import acme.entities.StrategyRepository;
import acme.entities.Tactic;

@Validator
public class StrategyValidator extends AbstractValidator<ValidStrategy, Strategy> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private StrategyRepository repository;


	// ConstraintValidator interface ------------------------------------------
	@Override
	protected void initialise(final ValidStrategy annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Strategy strategy, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;

		if (strategy == null)
			result = true;
		else {
			{
				boolean uniqueStrategy;
				Strategy existingstrategy;

				existingstrategy = this.repository.findStrategyByTicker(strategy.getTicker());
				uniqueStrategy = existingstrategy == null || existingstrategy.equals(strategy);

				super.state(context, uniqueStrategy, "ticker", "acme.validation.strategy.duplicated-ticker.message");
			}
			{
				Collection<Tactic> tactics = this.repository.findTacticsByStrategy(strategy.getId());
				boolean atLeastOnetactic = strategy.getDraftMode() || !tactics.isEmpty();

				super.state(context, atLeastOnetactic, "*", "acme.validation.strategy.no-tactics-and-published.message");
			}
			{
				Date start = strategy.getStartMoment();
				Date end = strategy.getEndMoment();
				boolean correctDates = strategy.getDraftMode() || MomentHelper.isBefore(start, end);

				super.state(context, correctDates, "*", "acme.validation.strategy.correct-interval.message");
			}
			result = !super.hasErrors(context);
		}

		return result;
	}
}
