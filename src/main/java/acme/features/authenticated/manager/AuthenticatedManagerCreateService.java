
package acme.features.authenticated.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.components.principals.UserAccount;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.realms.Manager;
import acme.realms.ProjectMember;

@Service
public class AuthenticatedManagerCreateService extends AbstractService<Authenticated, Manager> {

	@Autowired
	private AuthenticatedManagerRepository	repository;

	private Manager							manager;

	private ProjectMember					projectMember;


	@Override
	public void load() {
		int userAccountId;
		UserAccount userAccount;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		userAccount = this.repository.findUserAccountById(userAccountId);

		this.manager = this.newObject(Manager.class);
		this.manager.setUserAccount(userAccount);

		this.projectMember = this.repository.findProjectMemberByUserAccountId(userAccountId);
		if (this.projectMember == null) {
			this.projectMember = super.newObject(ProjectMember.class);
			this.projectMember.setUserAccount(userAccount);
		}
	}

	@Override
	public void authorise() {
		boolean status;

		status = !super.getRequest().getPrincipal().hasRealmOfType(Manager.class);
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.manager, "position", "skills", "executive");
	}

	@Override
	public void validate() {
		super.validateObject(this.manager);
	}

	@Override
	public void execute() {
		this.repository.save(this.manager);
		this.repository.save(this.projectMember);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.manager, "position", "skills", "executive");
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}
}
