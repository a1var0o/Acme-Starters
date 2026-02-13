
package acme.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidNumber;
import acme.datatypes.MilestoneKind;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Milestone extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@Mandatory
	//@ValidHeader 
	@Column
	public String				title;

	@Mandatory
	//@ValidText
	@Column
	public String				achievements;

	@Mandatory
	@ValidNumber(min = 0)
	@Column
	public Double				effort;

	@Mandatory
	@Valid
	@Column
	public MilestoneKind		kind;

	//Relationships ------------------------------------------------
	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	public Campaign				campaign;
}
