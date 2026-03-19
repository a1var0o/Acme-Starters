
package acme.features.auditor.auditsection;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.AuditReport;
import acme.entities.AuditSection;

@Repository
public interface AuditorAuditSectionRepository extends AbstractRepository {

	@Query("SELECT m from AuditSection m where m.id = :id")
	AuditSection findAuditSectionById(int id);

	@Query("SELECT c from AuditReport c where c.id = :id")
	AuditReport findAuditReportById(int id);

	@Query("SELECT m from AuditSection m where m.auditReport.id = :auditReportId")
	Collection<AuditSection> findAuditSectionsByAuditReport(int auditReportId);
}
