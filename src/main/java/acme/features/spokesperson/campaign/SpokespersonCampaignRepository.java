
package acme.features.spokesperson.campaign;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.Campaign;

@Repository
public interface SpokespersonCampaignRepository extends AbstractRepository {

	@Query("select c from Campaign c where c.spokesperson.userAccount.id = :accountId")
	Collection<Campaign> findCampaignsBySpokespersonAccountId(int accountId);

	@Query("select c from Campaign c where c.id = :id")
	Campaign findCampaignById(int id);

}
