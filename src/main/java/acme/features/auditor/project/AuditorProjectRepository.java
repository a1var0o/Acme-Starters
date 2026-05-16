
package acme.features.auditor.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.AuditReport;
import acme.entities.Project;

@Repository
public interface AuditorProjectRepository extends AbstractRepository {

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);

	@Query("SELECT c from AuditReport c where c.auditor.userAccount.id = :accountId and c.draftMode = false and c.project = null")
	Collection<AuditReport> findPublishedAndNotAttachedReports(int accountId);

	@Query("SELECT c from AuditReport c where c.id = :id")
	AuditReport findAuditReportById(int id);
}
