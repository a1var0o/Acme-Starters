package acme.features.projectmember.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.Project;

@Repository
public interface ProjectMemberProjectRepository extends AbstractRepository {

	@Query("select m.project from Member m where m.projectMember.userAccount.id = :accountId")
	Collection<Project> findProjectsByProjectMemberAccountId(int accountId);

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);
}
