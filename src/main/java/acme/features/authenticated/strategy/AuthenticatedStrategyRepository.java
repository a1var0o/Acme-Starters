
package acme.features.authenticated.strategy;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.Strategy;

@Repository
public interface AuthenticatedStrategyRepository extends AbstractRepository {

	@Query("select s from Strategy s where s.id = :id")
	Strategy findStrategyById(int id);

	@Query("select s from Strategy s where s.draftMode == :draftMode")
	Collection<Strategy> findStrategiesByDraftMode(boolean draftMode);
}
