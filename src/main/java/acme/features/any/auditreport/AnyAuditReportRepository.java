
package acme.features.any.auditreport;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.AuditReport;

@Repository
public interface AnyAuditReportRepository extends AbstractRepository {

	@Query("SELECT c from AuditReport c where c.id = :id")
	AuditReport findAuditReportById(int id);

	@Query("SELECT c from AuditReport c where c.draftMode = false")
	Collection<AuditReport> findPublishedAuditReports();
}
