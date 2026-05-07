
package acme.features.spokesperson.campaign;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.Campaign;
import acme.entities.Milestone;
import acme.entities.Project;
import acme.realms.Spokesperson;

@Repository
public interface SpokespersonCampaignRepository extends AbstractRepository {

	@Query("select c from Campaign c where c.spokesperson.userAccount.id = :accountId")
	Collection<Campaign> findCampaignsBySpokespersonAccountId(int accountId);

	@Query("select c from Campaign c where c.id = :id")
	Campaign findCampaignById(int id);

	@Query("select s from Spokesperson s where s.userAccount.id = :id")
	Spokesperson findSpokespersonByUserAccountId(int id);

	@Query("select m from Milestone m where m.campaign.id = :campaignId")
	Collection<Milestone> findMilestonesByCampaignId(int campaignId);

	@Query("select distinct m.project from Member m where m.project.draftMode = true and m.projectMember.userAccount.id = :accountId")
	Collection<Project> findDraftProjectsByAccountId(int accountId);

}
