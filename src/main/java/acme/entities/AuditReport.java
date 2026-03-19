
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
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidNumber;
import acme.client.components.validation.ValidUrl;
import acme.client.helpers.MomentHelper;
import acme.constraints.ValidAuditReport;
import acme.constraints.ValidHeader;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import acme.realms.Auditor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidAuditReport
public class AuditReport extends AbstractEntity {

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
	@Column
	private Boolean				draftMode;

	// Derived attributes -----------------------------------------------------


	@Mandatory
	@Valid
	@Transient
	private Double monthsActive() {
		double d = 0.;
		if (this.getStartMoment() != null && this.getEndMoment() != null) {
			d = MomentHelper.computeDifference(this.getStartMoment(), this.getEndMoment(), ChronoUnit.MONTHS);
		}
		return d;
	}


	@Autowired
	@Transient
	private AuditReportRepository repository;


	@Mandatory
	@ValidNumber(min = 0)
	@Transient
	private Integer totalHours() {
		return this.repository.getTotalHours(this.getId());
	}

	// Relationships ----------------------------------------------------------


	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Auditor auditor;
}
