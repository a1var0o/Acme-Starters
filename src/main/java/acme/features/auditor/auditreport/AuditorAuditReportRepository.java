
package acme.features.auditor.auditreport;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.AuditReport;
import acme.entities.AuditSection;
import acme.realms.Auditor;

@Repository
public interface AuditorAuditReportRepository extends AbstractRepository {

	@Query("SELECT c from AuditReport c where c.auditor.userAccount.id = :accountId")
	Collection<AuditReport> findAuditReportsByAuditorAccountId(int accountId);

	@Query("SELECT c from AuditReport c where c.id = :id")
	AuditReport findAuditReportById(int id);

	@Query("SELECT s from Auditor s where s.userAccount.id = :id")
	Auditor findAuditorByUserAccountId(int id);

	@Query("SELECT m from AuditSection m where m.auditReport.id = :auditReportId")
	Collection<AuditSection> findAuditSectionsByAuditReportId(int auditReportId);
}
