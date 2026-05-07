
package acme.features.authenticated.fundraiser;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.principals.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.realms.Fundraiser;
import acme.realms.ProjectMember;

@Repository
public interface AuthenticatedFundraiserRepository extends AbstractRepository {

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findUserAccountById(int id);

	@Query("select f from Fundraiser f where f.userAccount.id = :id")
	Fundraiser findFundraiserByUserAccountId(int id);

	@Query("select pm from ProjectMember pm where pm.userAccount.id = :userAccountId")
	ProjectMember findProjectMemberByUserAccountId(int userAccountId);
}
