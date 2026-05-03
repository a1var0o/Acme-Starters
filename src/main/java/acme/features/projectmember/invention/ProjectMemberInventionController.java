package acme.features.projectmember.invention;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.Invention;
import acme.realms.ProjectMember;

@Controller
public class ProjectMemberInventionController extends AbstractController<ProjectMember, Invention> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", ProjectMemberInventionListService.class);
		super.addCustomCommand("list-available", "list", ProjectMemberInventionListAvailableService.class);
		super.addBasicCommand("show", ProjectMemberInventionShowService.class);
		super.addCustomCommand("assign", "update", ProjectMemberInventionAssignService.class);
	}
}
