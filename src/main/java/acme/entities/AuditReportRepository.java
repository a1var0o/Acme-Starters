
package acme.entities;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface AuditReportRepository extends AbstractRepository {

	@Query("SELECT sum(t.hours) FROM AuditSection t WHERE t.auditReport.id = :auditReportId")
	Integer getTotalHours(int auditReportId);
}
