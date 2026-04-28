
package acme.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidUrl;
import acme.constraints.ValidHeader;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AdBanner extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// ------------------------------------------------------------------------

	@Mandatory
	@ValidHeader
	@Column
	private String				slogan;

	@Mandatory
	@ValidUrl
	@Column
	private String				targetUrl;

	@Mandatory
	@ValidUrl
	@Column
	private String				pictureUrl;
}
