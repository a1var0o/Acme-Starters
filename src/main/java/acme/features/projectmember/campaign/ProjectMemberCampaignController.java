package acme.features.projectmember.campaign;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.Campaign;
import acme.realms.ProjectMember;

@Controller
public class ProjectMemberCampaignController extends AbstractController<ProjectMember, Campaign> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", ProjectMemberCampaignListService.class);
		super.addBasicCommand("show", ProjectMemberCampaignShowService.class);
	}
}
