
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.Member;
import acme.entities.Campaign;
import acme.entities.Invention;
import acme.entities.Project;
import acme.entities.Strategy;

@Repository
public interface ManagerProjectRepository extends AbstractRepository {

	@Query("select p from Project p where p.manager.userAccount.id = :accountId")
	Collection<Project> findProjectsByManagerAccountId(int accountId);

	@Query("select p from Project p where p.id = :id")
	Project findProject(int id);

	@Query("select m from Member m where m.project.id = :projectId")
	Collection<Member> findMembersByProjectId(int projectId);

	@Query("select i from Invention i where i.project.id = :projectId")
	Collection<Invention> findInventionsByProjectId(int projectId);

	@Query("select c from Campaign c where c.project.id = :projectId")
	Collection<Campaign> findCampaignsByProjectId(int projectId);

	@Query("select s from Strategy s where s.project.id = :projectId")
	Collection<Strategy> findStrategiesByProjectId(int projectId);
}
