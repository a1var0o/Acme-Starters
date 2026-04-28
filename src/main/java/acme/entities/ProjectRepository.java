
package acme.entities;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface ProjectRepository extends AbstractRepository {

	@Query("select i from Invention i where i.project.id = :projectId")
	Collection<Invention> getProjectInventions(int projectId);

	@Query("select c from Campaign c where c.project.id = :projectId")
	Collection<Campaign> getProjectCampaigns(int projectId);

	@Query("select s from Strategy s where s.project.id = :projectId")
	Collection<Strategy> getProjectStrategies(int projectId);

	@Query("select count(m.projectMember) from Member m where m.project.id = :projectId")
	Long getProjectMembers(int projectId);
}
