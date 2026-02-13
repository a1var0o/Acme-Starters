
package acme.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Moment;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidMoment.Constraint;
import acme.client.components.validation.ValidUrl;
import acme.realms.Spokesperson;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Campaign extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@Mandatory
	//@ValidTicker 
	@Column(unique = true)
	public String				ticker;

	@Mandatory
	//@ValidHeader
	@Column
	public String				name;

	@Mandatory
	//@ValidText
	@Column
	public String				description;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	public Moment				startMoment;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	public Moment				endMoment;

	@Optional
	@ValidUrl
	@Column
	public String				moreInfo;

	@Mandatory
	@Valid
	@Column
	public Boolean				draftMode;

	//Derived attributes

	/*
	 * we will learn this in the next class
	 * 
	 * @Transient
	 * public double monthsActive() {
	 * return 0;
	 * }
	 * 
	 * @Transient
	 * public double effort() {
	 * return 0;
	 * }
	 */

	//Relationships ------------------------------------------------

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	public Spokesperson			spokesperson;
}
