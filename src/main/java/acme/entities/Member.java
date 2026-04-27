
package acme.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.realms.ProjectMember;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member extends AbstractEntity {
	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	@Mandatory
	@Valid
	@ManyToMany
	private ProjectMember		projectMember;

	@Mandatory
	@Valid
	@ManyToMany
	private Project				project;
}
