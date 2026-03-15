
package acme.features.any.part;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.Invention;
import acme.entities.Part;

@Repository
public interface AnyPartRepository extends AbstractRepository {

	@Query("select p from Part p where p.invention.id = :inventionId")
	Collection<Part> findPartsByInvention(int inventionId);

	@Query("select p from Part p where p.id = :partId")
	Part findPart(int partId);

	@Query("select i from Invention i where i.id = :inventionId")
	Invention findInvention(int inventionId);

}
