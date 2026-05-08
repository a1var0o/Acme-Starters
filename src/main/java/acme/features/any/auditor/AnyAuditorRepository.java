
package acme.features.any.auditor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.AuditReport;

@Repository
public interface AnyAuditorRepository extends AbstractRepository {

	@Query("SELECT c from AuditReport c where c.id = :AuditReportId")
	AuditReport findAuditReport(int auditReportId);

}
