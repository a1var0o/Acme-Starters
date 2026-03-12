
package acme.entities;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface InventionRepository extends AbstractRepository {

	@Query("select sum(p.cost.amount) from Part p  where p.invention.id = :inventionId")
	Double computeTotalCost(int inventionId);

	@Query("select p from Part p where p.invention.id = :inventionId")
	Collection<Part> getPartsByInventionId(int inventionId);

	@Query("select i from Invention i where i.ticker = :ticker")
	Invention findInventionByTicker(String ticker);

}
