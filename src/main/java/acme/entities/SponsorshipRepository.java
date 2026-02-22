
package acme.entities;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface SponsorshipRepository extends AbstractRepository {

	@Query("SELECT sum(d.money.amount) FROM Donation d WHERE d.sponsorship.id = :sponsorshipId")
	Double getTotalDonations(int sponsorshipId);
}
