
package acme.features.any.invention;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.Invention;

@Repository
public interface AnyInventionRepository extends AbstractRepository {

	@Query("select i from Invention i where i.draftMode = false")
	Collection<Invention> findPublishedInventions();

	@Query("select i from Invention i where i.id = :inventionId")
	Invention findInvention(int inventionId);
}
