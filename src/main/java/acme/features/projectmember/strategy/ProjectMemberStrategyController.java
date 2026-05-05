package acme.features.projectmember.strategy;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.Strategy;
import acme.realms.ProjectMember;

@Controller
public class ProjectMemberStrategyController extends AbstractController<ProjectMember, Strategy> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", ProjectMemberStrategyListService.class);
		super.addCustomCommand("list-available", "list", ProjectMemberStrategyListAvailableService.class);
		super.addBasicCommand("show", ProjectMemberStrategyShowService.class);
		super.addCustomCommand("assign", "update", ProjectMemberStrategyAssignService.class);
	}
}
