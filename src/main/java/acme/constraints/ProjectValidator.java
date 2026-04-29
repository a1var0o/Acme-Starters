
package acme.constraints;

import java.util.Collection;
import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.Invention;
import acme.entities.Project;
import acme.entities.ProjectRepository;

@Validator
public class ProjectValidator extends AbstractValidator<ValidProject, Project> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectRepository repository;


	// ConstraintValidator interface ------------------------------------------
	@Override
	protected void initialise(final ValidProject annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Project project, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;

		if (project == null)
			result = true;
		else {
			{

				Collection<Invention> inventions = this.repository.getProjectInventions(project.getId());
				boolean atLeastOneInvention = project.getDraftMode() || !inventions.isEmpty();

				super.state(context, atLeastOneInvention, "*", "acme.validation.project.no-inventions-and-published.message");
			}
			{
				Date start = project.getKickOffMoment();
				Date end = project.getCloseOutMoment();
				boolean correctDates = project.getDraftMode() || project.getKickOffMoment() != null && project.getCloseOutMoment() != null && MomentHelper.isBefore(start, end);

				super.state(context, correctDates, "*", "acme.validation.project.correct-interval.message");
			}
			result = !super.hasErrors(context);
		}

		return result;
	}
}
