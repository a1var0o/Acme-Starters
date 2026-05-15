
package acme.features.auditor.project;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.Project;
import acme.realms.Auditor;

@Controller
public class AuditorProjectController extends AbstractController<Auditor, Project> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addCustomCommand("attach", "update", AuditorProjectAttachService.class);

	}
}
