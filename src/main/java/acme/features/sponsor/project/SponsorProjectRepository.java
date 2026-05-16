
package acme.features.sponsor.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.Project;
import acme.entities.Sponsorship;

@Repository
public interface SponsorProjectRepository extends AbstractRepository {

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);

	@Query("select s from Sponsorship s where s.sponsor.userAccount.id = :sponsorId and s.draftMode = false and s.project = null")
	Collection<Sponsorship> findPublishAndNotAttachedSponsorships(int sponsorId);

	@Query("select s from Sponsorship s where s.id = :id")
	Sponsorship findSponsorshipById(int id);
}
