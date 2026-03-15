
package acme.features.auditor.auditreport;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.AuditReport;

@Repository
public interface AuditorAuditReportRepository extends AbstractRepository {

	@Query("SELECT c from AuditReport c where c.auditor.userAccount.id = :accountId")
	Collection<AuditReport> findAuditReportsByAuditorAccountId(int accountId);

	@Query("SELECT c from AuditReport c where c.id = :id")
	AuditReport findAuditReportById(int id);

}
