
package acme.features.auditor.auditsection;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.AuditReport;
import acme.entities.AuditSection;

@Repository
public interface AnyAuditSectionRepository extends AbstractRepository {

	@Query("select p from AuditSection p where p.auditReport.id = :auditreportId")
	Collection<AuditSection> findAuditSectionsByAuditReport(int auditreportId);

	@Query("select p from AuditSection p where p.id = :auditsectionId")
	AuditSection findAuditSection(int auditsectionId);

	@Query("select i from AuditReport i where i.id = :auditreportId")
	AuditReport findAuditReport(int auditreportId);

}
