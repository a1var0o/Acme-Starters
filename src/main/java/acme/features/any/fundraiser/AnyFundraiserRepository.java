
package acme.features.any.fundraiser;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.Strategy;

@Repository
public interface AnyFundraiserRepository extends AbstractRepository {

	@Query("select s From Strategy s where s.id = :id")
	Strategy findStrategyById(int id);
}
