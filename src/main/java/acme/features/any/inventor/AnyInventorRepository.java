
package acme.features.any.inventor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.Invention;

@Repository
public interface AnyInventorRepository extends AbstractRepository {

	@Query("select i from Invention i where i.id = :inventionId")
	Invention findInvention(int inventionId);

}
