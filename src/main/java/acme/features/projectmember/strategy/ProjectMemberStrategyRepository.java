package acme.features.projectmember.strategy;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.Project;
import acme.entities.Strategy;

@Repository
public interface ProjectMemberStrategyRepository extends AbstractRepository {

	@Query("select s from Strategy s where s.project.id = :projectId")
	Collection<Strategy> findStrategiesByProjectId(int projectId);

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);
	
	@Query("select s from Strategy s where s.id = :id")
	Strategy findStrategyById(int id);
	
	@Query("select count(m) from Member m where m.project.id = :projectId and m.projectMember.userAccount.id = :accountId")
	int countProjectMembership(int projectId, int accountId);
}
