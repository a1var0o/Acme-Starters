
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

	@Query("select count(i) from Invention i where i.project.id = :projectId")
	long countInventionsByProjectId(int projectId);

	@Query("select count(i) from Invention i where i.project.id = :projectId and i.startMoment < :kickOffMoment")
	long countInventionsWithStartMomentBefore(int projectId, java.util.Date kickOffMoment);

	@Query("select count(c) from Campaign c where c.project.id = :projectId and c.startMoment < :kickOffMoment")
	long countCampaignsWithStartMomentBefore(int projectId, java.util.Date kickOffMoment);

	@Query("select count(s) from Strategy s where s.project.id = :projectId and s.startMoment < :kickOffMoment")
	long countStrategiesWithStartMomentBefore(int projectId, java.util.Date kickOffMoment);

	@Query("select count(i) from Invention i where i.project.id = :projectId and i.endMoment > :closeOutMoment")
	long countInventionsWithEndMomentAfter(int projectId, java.util.Date closeOutMoment);

	@Query("select count(c) from Campaign c where c.project.id = :projectId and c.endMoment > :closeOutMoment")
	long countCampaignsWithEndMomentAfter(int projectId, java.util.Date closeOutMoment);

	@Query("select count(s) from Strategy s where s.project.id = :projectId and s.endMoment > :closeOutMoment")
	long countStrategiesWithEndMomentAfter(int projectId, java.util.Date closeOutMoment);

	// Publication Constraints
	@Query("select count(i) from Invention i where i.project.id = :projectId and i.id not in (select p.invention.id from Part p)")
	long countInventionsWithoutParts(int projectId);

	@Query("select count(c) from Campaign c where c.project.id = :projectId and c.id not in (select m.campaign.id from Milestone m)")
	long countCampaignsWithoutMilestones(int projectId);

	@Query("select count(s) from Strategy s where s.project.id = :projectId and s.id not in (select t.strategy.id from Tactic t)")
	long countStrategiesWithoutTactics(int projectId);

	@Query("select count(i) from Invention i where i.project.id = :projectId and i.startMoment <= :now")
	long countInventionsWithStartMomentInPast(int projectId, java.util.Date now);

	@Query("select count(c) from Campaign c where c.project.id = :projectId and c.startMoment <= :now")
	long countCampaignsWithStartMomentInPast(int projectId, java.util.Date now);

	@Query("select count(s) from Strategy s where s.project.id = :projectId and s.startMoment <= :now")
	long countStrategiesWithStartMomentInPast(int projectId, java.util.Date now);

	@Query("select count(i) from Invention i where i.project.id = :projectId and i.startMoment >= i.endMoment")
	long countInventionsWithInvalidInterval(int projectId);

	@Query("select count(c) from Campaign c where c.project.id = :projectId and c.startMoment >= c.endMoment")
	long countCampaignsWithInvalidInterval(int projectId);

	@Query("select count(s) from Strategy s where s.project.id = :projectId and s.startMoment >= s.endMoment")
	long countStrategiesWithInvalidInterval(int projectId);
}
