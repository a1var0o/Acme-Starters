package acme.features.projectmember.campaign;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.Campaign;
import acme.entities.Project;
import acme.realms.Spokesperson;

@Repository
public interface ProjectMemberCampaignRepository extends AbstractRepository {

	@Query("select c from Campaign c where c.project.id = :projectId")
	Collection<Campaign> findCampaignsByProjectId(int projectId);

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);
	
	@Query("select c from Campaign c where c.id = :id")
	Campaign findCampaignById(int id);
	
	@Query("select count(m) > 0 from Member m where m.project.id = :projectId and m.projectMember.userAccount.id = :accountId")
	boolean isProjectMember(int projectId, int accountId);

	@Query("select s from Spokesperson s where s.userAccount.id = :accountId")
	Spokesperson findSpokespersonByAccountId(int accountId);

	@Query("select c from Campaign c where c.spokesperson.id = :spokespersonId and c.project is null")
	Collection<Campaign> findAvailableCampaigns(int spokespersonId);
}
