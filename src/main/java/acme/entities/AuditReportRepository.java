
package acme.entities;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface AuditReportRepository extends AbstractRepository {

	@Query("SELECT sum(t.hours) FROM AuditSection t WHERE t.auditReport.id = :auditReportId")
	Integer getTotalHours(int auditReportId);

	@Query("SELECT m from AuditSection m where m.auditReport.id = :auditReportId")
	Collection<AuditSection> getAuditSectionsByAuditReportId(int auditReportId);

	@Query("SELECT c from AuditReport c where c.ticker = :ticker")
	AuditReport findAuditReportByTicker(String ticker);
}
