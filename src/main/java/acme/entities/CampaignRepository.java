
package acme.entities;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;

public interface CampaignRepository extends AbstractRepository {

	@Query("select sum(m.effort) from Milestone m where m.campaign.id = :campaignId")
	Double totalEffort(int campaignId);

	@Query("select m from Milestone m where m.campaign.id = :campaignId")
	Collection<Milestone> getMilestonesByCampaignId(int campaignId);

	@Query("select c from Campaign c where c.ticker = :ticker")
	Campaign findCampaignByTicker(String ticker);

}
