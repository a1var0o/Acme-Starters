
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.Member;
import acme.entities.Project;

@Repository
public interface ManagerProjectRepository extends AbstractRepository {

	@Query("select p from Project p where p.manager.userAccount.id = :accountId")
	Collection<Project> findProjectsByManagerAccountId(int accountId);

	@Query("select p from Project p where p.id = :id")
	Project findProject(int id);

	@Query("select m from Member m where m.project.id = :projectId")
	Collection<Member> findMembersByProjectId(int projectId);

}
