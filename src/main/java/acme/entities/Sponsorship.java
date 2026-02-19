
package acme.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Moment;
import acme.client.components.datatypes.Money;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidMoment.Constraint;
import acme.client.components.validation.ValidUrl;
import acme.constraints.ValidText;
import acme.realms.Sponsor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Sponsorship extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	// TODO: implement validator
	// @ValidTicker
	@Column(unique = true)
	private String				ticker;

	@Mandatory
	// TODO: implement validator
	// @ValidHeader
	@Column
	private String				name;

	@Mandatory
	// TODO: implement validator
	@ValidText
	@Column
	private String				description;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	// TODO: @Temporal should only be set on a java.util.Date or java.util.Calendar property
	// @Temporal(TemporalType.TIMESTAMP)
	private Moment				startMoment;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	// TODO: @Temporal should only be set on a java.util.Date or java.util.Calendar property
	// @Temporal(TemporalType.TIMESTAMP)
	private Moment				endMoment;

	@Optional
	@ValidUrl
	@Column
	private String				moreInfo;

	@Mandatory
	@Valid
	@Column
	private Boolean				draftMode;

	// Derived attributes -----------------------------------------------------


	// TODO: raises error
	// @Mandatory
	@Valid
	@Transient
	private Double monthsActive() {
		// TODO: implement calculation
		return null;
	}

	// TODO: raises error
	// @Mandatory
	// @ValidMoney
	@Transient
	private Money totalMoney() {
		// TODO: implement calculation
		return null;
	}
	// Relationships ----------------------------------------------------------


	@Mandatory
	@Valid
	@ManyToOne
	private Sponsor sponsor;

}
