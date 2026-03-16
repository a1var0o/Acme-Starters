
package acme.features.inventor.invention;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.Invention;
import acme.entities.Part;
import acme.realms.Inventor;

@Repository
public interface InventorInventionRepository extends AbstractRepository {

	@Query("select i from Inventor i where i.id = :inventorId")
	Inventor findInventor(int inventorId);

	@Query("select i from Invention i where i.id = :id")
	Invention findInvention(int id);

	@Query("select i from Invention i where i.inventor.userAccount.id = :accountId")
	Collection<Invention> findInventionsByInventorAccountId(int accountId);

	@Query("select p from Part p where p.invention.id = :inventionId")
	Collection<Part> findPartsByInventionId(int inventionId);

}
