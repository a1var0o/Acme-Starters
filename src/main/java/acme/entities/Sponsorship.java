
package acme.entities;

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
import acme.client.components.validation.ValidMoney;
import acme.client.components.validation.ValidUrl;
import acme.client.helpers.MomentHelper;
import acme.constraints.ValidHeader;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
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
	// TODO: implement dynamic enforcement of future moments
	@Temporal(TemporalType.TIMESTAMP)
	private Date				startMoment;

	@Mandatory
	// TODO: implement dynamic enforcement of future moments
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


	@Mandatory
	@Valid
	@Transient
	private Double monthsActive() {
		return MomentHelper.computeDifference(this.startMoment, this.endMoment, ChronoUnit.MONTHS);
	}


	@Autowired
	@Transient
	private SponsorshipRepository repository;


	@Mandatory
	@ValidMoney
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
