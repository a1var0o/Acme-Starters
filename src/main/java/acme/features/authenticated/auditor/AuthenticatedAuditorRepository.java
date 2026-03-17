
package acme.features.authenticated.auditor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.principals.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.realms.Auditor;

@Repository
public interface AuthenticatedAuditorRepository extends AbstractRepository {

	@Query("SELECT ua from UserAccount ua where ua.id = :id")
	UserAccount findUserAccountById(int id);

	@Query("SELECT s from Auditor s where s.userAccount.id = :id")
	Auditor findAuditorByUserAccountId(int id);
}
