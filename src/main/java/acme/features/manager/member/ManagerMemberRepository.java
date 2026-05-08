
package acme.features.manager.member;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.principals.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.entities.Project;
import acme.realms.ProjectMember;

@Repository
public interface ManagerMemberRepository extends AbstractRepository {

	@Query("select p from Project p where p.id = :id")
	Project findProject(int id);

	@Query("select pm from ProjectMember pm where pm.userAccount.id = :userAccountId")
	ProjectMember findProjectMemberByUserAccountId(int userAccountId);

	@Query("select ua from UserAccount ua where ua.id = :userAccountId")
	UserAccount findUserByAccountId(int userAccountId);

	@Query("select pm from ProjectMember pm where pm.id not in (select m.projectMember.id from Member m where m.project.id = :projectId) and (pm.userAccount.id in (select i.userAccount.id from Inventor i) or pm.userAccount.id in (select f.userAccount.id from Fundraiser f) or pm.userAccount.id in (select s.userAccount.id from Spokesperson s))")
	Collection<ProjectMember> findAllProjectMembersNotInProject(int projectId);

}
