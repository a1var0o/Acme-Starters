
package acme.features.authenticated.inventor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.principals.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.realms.Inventor;
import acme.realms.ProjectMember;

@Repository
public interface AuthenticatedInventorRepository extends AbstractRepository {

	@Query("select ua from UserAccount ua where ua.id = :accountId")
	UserAccount findUserAccountById(int accountId);

	@Query("select i from Inventor i where i.userAccount.id = :userAccountId")
	Inventor findInventorByUserAccountId(int userAccountId);

	@Query("select pm from ProjectMember pm where pm.userAccount.id = :userAccountId")
	ProjectMember findProjectMemberByUserAccountId(int userAccountId);

}
