
package acme.entities;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidMoment.Constraint;
import acme.client.components.validation.ValidUrl;
import acme.client.helpers.MomentHelper;
import acme.constraints.ValidTicker;
import acme.constraints.ValidHeader;
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
	@ValidTicker
	@Column(unique = true)
	private String				ticker;

	@Mandatory
	@ValidHeader
	@Column
	private String				name;

	@Mandatory
	@ValidText
	@Column
	private String				description;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				startMoment;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				endMoment;

	@Optional
	@ValidUrl
	@Column
	private String				moreInfo;

	@Mandatory
	@Valid
	@Column
	private Boolean				draftMode;

	// Derived attributes -----------------------------------------------------


	// TODO: raises error, annotation disallowed for this location
	// @Mandatory
	@Valid
	@Transient
	private Double monthsActive() {
		Double result = 0.0;

		// TODO: this doesn't work
		// Duration duration = MomentHelper.computeDuration(this.startMoment, this.endMoment);
		// result = Double.valueOf(duration.get(ChronoUnit.MONTHS));

		return result;
	}


	@Autowired
	@Transient
	private SponsorshipRepository repository;


	// TODO: raises error, annotation disallowed for this location
	// @Mandatory
	// @ValidMoney
	@Transient
	private Money totalMoney() {
		Money result = new Money();

		Double totalDonations = this.repository.getTotalDonations(this.getId());
		result.setAmount(totalDonations == null ? 0 : totalDonations);
		result.setCurrency("EUR");

		return result;
	}

	// Relationships ----------------------------------------------------------


	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Sponsor sponsor;

}
