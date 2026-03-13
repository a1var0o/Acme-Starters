
package acme.features.fundraiser.strategy;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.Strategy;

@Repository
public interface FundraiserStrategyRepository extends AbstractRepository {

	@Query("select s from Strategy s where s.fundraiser.userAccount.id = :accountId")
	Collection<Strategy> findStrategiesByFundraiserAccountId(int accountId);

	@Query("select s from Strategy s where s.id = :id")
	Strategy findStrategyById(int id);

}
