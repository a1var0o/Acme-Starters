
package acme.features.any.milestone;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.Campaign;
import acme.entities.Milestone;

@Repository
public interface AnyMilestoneRepository extends AbstractRepository {

	@Query("select m from Milestone m where m.campaign.id = :campaignId")
	Collection<Milestone> findMilestonesByCampaign(int campaignId);

	@Query("select m from Milestone m where m.id = :milestoneId")
	Milestone findMilestone(int milestoneId);

	@Query("select c from Campaign c where c.id = :campaignId")
	Campaign findCampaign(int campaignId);

}
