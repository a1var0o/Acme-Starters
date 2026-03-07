
package acme.features.authenticated.strategy;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.Strategy;
import acme.realms.Fundraiser;

@Controller
public class AuthenticatedStrategyController extends AbstractController<Fundraiser, Strategy> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", AuthenticatedStrategyListService.class);
		super.addBasicCommand("show", AuthenticatedStrategyShowService.class);
	}
}
