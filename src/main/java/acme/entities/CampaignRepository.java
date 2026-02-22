
package acme.entities;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;

public interface CampaignRepository extends AbstractRepository {

	@Query("select sum(m.effort) from Milestone m where m.campaign.id = :campaignId")
	Double totalEffort(int campaignId);
}
