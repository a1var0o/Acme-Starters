
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
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidMoney;
import acme.client.components.validation.ValidUrl;
import acme.client.helpers.MomentHelper;
import acme.constraints.ValidHeader;
import acme.constraints.ValidInvention;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import acme.realms.Inventor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidInvention
public class Invention extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// ------------------------------------------------------------------------

	@Autowired
	@Transient
	InventionRepository			repository;

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
	@ValidMoment
	@Temporal(TemporalType.TIMESTAMP)
	private Date				startMoment;

	@Mandatory
	@ValidMoment
	@Temporal(TemporalType.TIMESTAMP)
	private Date				endMoment;

	@Optional
	@ValidUrl
	@Column
	private String				moreInfo;


	@Mandatory
	@Valid
	@Transient
	public Double getMonthsActive() {
		double res = 0.;

		if (this.getStartMoment() != null && this.getEndMoment() != null)
			res = MomentHelper.computeDifference(this.getStartMoment(), this.getEndMoment(), ChronoUnit.MONTHS);
		return res;
	}

	@Mandatory
	@ValidMoney
	@Transient
	public Money getCost() {
		Money res = new Money();

		res.setCurrency("EUR");
		Double cost = this.repository.computeTotalCost(this.getId());
		res.setAmount(cost == null ? 0 : cost);
		return res;
	}


	@Mandatory
	@Valid
	@Column
	private Boolean		draftMode;

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Inventor	inventor;
}
