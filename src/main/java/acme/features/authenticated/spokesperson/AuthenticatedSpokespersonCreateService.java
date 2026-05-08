
package acme.features.authenticated.spokesperson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.components.principals.UserAccount;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.realms.ProjectMember;
import acme.realms.Spokesperson;

@Service
public class AuthenticatedSpokespersonCreateService extends AbstractService<Authenticated, Spokesperson> {

	@Autowired
	private AuthenticatedSpokespersonRepository	repository;

	private Spokesperson						spokesperson;

	private ProjectMember						projectMember;


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

		this.spokesperson = this.newObject(Spokesperson.class);
		this.spokesperson.setUserAccount(userAccount);
	}

	@Override
	public void authorise() {
		boolean status;

		status = !super.getRequest().getPrincipal().hasRealmOfType(Spokesperson.class);
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.spokesperson, "cv", "achievements", "licensed");
	}

	@Override
	public void validate() {
		super.validateObject(this.spokesperson);
	}

	@Override
	public void execute() {
		this.repository.save(this.spokesperson);
		this.repository.save(this.projectMember);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.spokesperson, "cv", "achievements", "licensed");
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}
}
