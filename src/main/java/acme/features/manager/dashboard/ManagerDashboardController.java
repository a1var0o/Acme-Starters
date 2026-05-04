
package acme.features.manager.dashboard;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.forms.Dashboard;
import acme.realms.Manager;

@Controller
public class ManagerDashboardController extends AbstractController<Manager, Dashboard> {

	// Constructors -----------------------------------------------------------

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("show", ManagerDashboardShowService.class);
	}

}
