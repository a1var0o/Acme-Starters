
package acme.features.any.auditsection;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.AuditReport;
import acme.entities.AuditSection;

@Repository
public interface AnyAuditSectionRepository extends AbstractRepository {

	@Query("SELECT m from AuditSection m where m.auditReport.id = :auditReportId")
	Collection<AuditSection> findAuditSectionsByAuditReport(int auditReportId);

	@Query("SELECT m from AuditSection m where m.id = :auditSectionId")
	AuditSection findAuditSection(int auditSectionId);

	@Query("SELECT c from AuditReport c where c.id = :auditReportId")
	AuditReport findAuditReport(int auditReportId);

}
