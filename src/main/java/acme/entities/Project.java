
package acme.entities;

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
import acme.constraints.ValidHeader;
import acme.constraints.ValidText;
import acme.realms.Manager;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Project extends AbstractEntity {
	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@ValidHeader
	@Column
	private String				title;

	@Mandatory
	@ValidHeader
	@Column
	private String				keywords;

	@Mandatory
	@ValidText
	@Column
	private String				description;

	@Mandatory
	@Temporal(TemporalType.TIMESTAMP)
	private Date				kickOffMoment;

	@Mandatory
	@Temporal(TemporalType.TIMESTAMP)
	private Date				closeOutMoment;

	@Mandatory
	@Valid
	@Column
	private Boolean				draftMode;

	@Mandatory
	@Valid
	@ManyToOne
	private Manager				manager;

	@Autowired
	@Transient
	private ProjectRepository	repository;


		@Mandatory
		@ValidNumber
		@Transient
		public Double getEffort() {
			Double inventionsMonthsActive = this.repository.getProjectInventions(this.getId()).stream().mapToDouble(i -> i.getMonthsActive()).sum();
			Double campaignsMonthsActive = this.repository.getProjectCampaigns(this.getId()).stream().mapToDouble(c -> c.getMonthsActive()).sum();
			Double strategiesMonthsActive = this.repository.getProjectStrategies(this.getId()).stream().mapToDouble(s -> s.getMonthsActive()).sum();
			double sum = inventionsMonthsActive + campaignsMonthsActive + strategiesMonthsActive;
			Long nMembers = this.repository.getProjectMembers(this.getId());
			if (nMembers == null)
				return sum;
			else
				return sum / nMembers;
		};
}
