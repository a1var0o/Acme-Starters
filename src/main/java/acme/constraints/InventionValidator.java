
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.Invention;
import acme.entities.InventionRepository;

@Validator
public class InventionValidator extends AbstractValidator<ValidInvention, Invention> {

	@Autowired
	private InventionRepository repository;


	@Override
	protected void initialise(final ValidInvention annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Invention invention, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;

		if (invention == null)
			result = true;
		else {
			{
				boolean uniqueInvention;
				Invention existingInvention;

				existingInvention = this.repository.findInventionByTicker(invention.getTicker());
				uniqueInvention = existingInvention == null || existingInvention.equals(invention);

				super.state(context, uniqueInvention, "ticker", "acme.validation.invention.duplicated-ticker.message");
			}
			{
				boolean enoughParts;

				enoughParts = invention.getDraftMode() || !this.repository.getPartsByInventionId(invention.getId()).isEmpty();

				super.state(context, enoughParts, "*", "acme.validation.invention.parts.message");
			}
			{
				boolean validInterval;

				validInterval = invention.getDraftMode() || MomentHelper.isBefore(invention.getStartMoment(), invention.getEndMoment());

				super.state(context, validInterval, "*", "acme.validation.invention.interval.message");
			}
			result = !super.hasErrors(context);
		}

		return result;
	}

}
