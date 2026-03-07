
package acme.features.authenticated.tactic;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.Tactic;

@Repository
public interface AuthenticatedTacticRepository extends AbstractRepository {

	@Query("select t from Tactic t where t.id = :id")
	Tactic findTacticById(int id);

	@Query("select t from Tactic t where t.strategy.id == :strategyId")
	Collection<Tactic> findTacticsByStrategy(int strategyId);
}
