
package acme.features.authenticated.fundraiser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.components.principals.UserAccount;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.realms.Fundraiser;
import acme.realms.ProjectMember;

@Service
public class AuthenticatedFundraiserCreateService extends AbstractService<Authenticated, Fundraiser> {

	@Autowired
	private AuthenticatedFundraiserRepository	repository;

	private Fundraiser							fundraiser;

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

		this.fundraiser = this.newObject(Fundraiser.class);
		this.fundraiser.setUserAccount(userAccount);
	}

	@Override
	public void authorise() {
		boolean status;

		status = !super.getRequest().getPrincipal().hasRealmOfType(Fundraiser.class);
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.fundraiser, "bank", "statement", "agent");
	}

	@Override
	public void validate() {
		super.validateObject(this.fundraiser);
	}

	@Override
	public void execute() {
		this.repository.save(this.fundraiser);
		this.repository.save(this.projectMember);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.fundraiser, "bank", "statement", "agent");
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}
}
