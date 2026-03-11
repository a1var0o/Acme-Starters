
package acme.features.auditor.auditreport;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.AuditReport;

@Repository
public interface AnyAuditReportRepository extends AbstractRepository {

	@Query("select i from AuditReport i where i.draftMode = false")
	Collection<AuditReport> findPublishedAuditReports();

	@Query("select i from Invention i where i.id = :auditreportId")
	AuditReport findAuditReport(int auditreportId);
}
