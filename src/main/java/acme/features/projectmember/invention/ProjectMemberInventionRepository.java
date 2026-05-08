package acme.features.projectmember.invention;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.Invention;
import acme.entities.Project;
import acme.realms.Inventor;

@Repository
public interface ProjectMemberInventionRepository extends AbstractRepository {

	@Query("select i from Invention i where i.project.id = :projectId")
	Collection<Invention> findInventionsByProjectId(int projectId);

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);
	
	@Query("select i from Inventor i where i.userAccount.id = :accountId")
	Inventor findInventorByAccountId(int accountId);

	@Query("select i from Invention i where i.inventor.id = :inventorId and i.project is null")
	Collection<Invention> findAvailableInventions(int inventorId);
	
	@Query("select i from Invention i where i.id = :id")
	Invention findInventionById(int id);
	
	@Query("select count(m) > 0 from Member m where m.project.id = :projectId and m.projectMember.userAccount.id = :accountId")
	boolean isProjectMember(int projectId, int accountId);
}
