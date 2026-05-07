
package acme.features.authenticated.inventor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.components.principals.UserAccount;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.realms.Inventor;
import acme.realms.ProjectMember;

@Service
public class AuthenticatedInventorCreateService extends AbstractService<Authenticated, Inventor> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedInventorRepository	repository;

	private Inventor						inventor;

	private ProjectMember					projectMember;

	// AbstractService<Authenticated, Inventor> ---------------------------


	@Override
	public void load() {
		int userAccountId;
		UserAccount userAccount;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		userAccount = this.repository.findUserAccountById(userAccountId);

		this.projectMember = this.repository.findProjectMemberByUserAccountId(userAccountId);
		if (this.projectMember == null) {
			this.projectMember = super.newObject(ProjectMember.class);
			this.projectMember.setUserAccount(userAccount);
		}

		this.inventor = super.newObject(Inventor.class);
		this.inventor.setUserAccount(userAccount);
	}

	@Override
	public void authorise() {
		boolean status;

		status = !super.getRequest().getPrincipal().hasRealmOfType(Inventor.class);
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.inventor, "bio", "keyWords", "licensed");
	}

	@Override
	public void validate() {
		super.validateObject(this.inventor);
	}

	@Override
	public void execute() {
		this.repository.save(this.inventor);
		this.repository.save(this.projectMember);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.inventor, "bio", "keyWords", "licensed");
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}
}
