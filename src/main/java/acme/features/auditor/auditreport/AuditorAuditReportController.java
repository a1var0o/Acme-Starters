
package acme.features.auditor.auditreport;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.AuditReport;
import acme.realms.Auditor;

@Controller
public class AuditorAuditReportController extends AbstractController<Auditor, AuditReport> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", AuditorAuditReportListService.class);
		super.addBasicCommand("show", AuditorAuditReportShowService.class);
	}
}
