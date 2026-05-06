
package acme.features.manager.member;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.Member;
import acme.realms.Manager;

@Controller
public class ManagerMemberController extends AbstractController<Manager, Member> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("create", ManagerMemberCreateService.class);
	}
}
