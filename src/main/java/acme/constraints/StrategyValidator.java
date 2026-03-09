
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
				boolean hasTactics = false;
				Collection<Tactic> tactics = this.repository.findTacticsByStrategy(strategy.getId());
				if (!strategy.getDraftMode() && tactics != null)
					hasTactics = true;
				if (strategy.getDraftMode())
					hasTactics = true;
				super.state(context, hasTactics, "*", "acme.validation.strategy.no-tactics-and-published.message");
			}
			{
				Date start = strategy.getStartMoment();
				Date end = strategy.getEndMoment();
				boolean correctDates = false;

				if (!strategy.getDraftMode() && MomentHelper.isBefore(start, end))
					correctDates = true;
				if (strategy.getDraftMode())
					correctDates = true;
				super.state(context, correctDates, "*", "acme.validation.strategy.correct-interval.message");
			}
			result = !super.hasErrors(context);
		}

		return result;
	}
}
