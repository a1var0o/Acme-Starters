
package acme.features.administrator.adbanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Administrator;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.entities.AdBanner;

@Service
public class AdministratorAdBannerCreateService extends AbstractService<Administrator, AdBanner> {

	@Autowired
	private AdministratorAdBannerRepository	repository;

	private AdBanner						adBanner;


	@Override
	public void load() {

		this.adBanner = super.newObject(AdBanner.class);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRealmOfType(Administrator.class);
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.adBanner, "slogan", "targetUrl", "pictureUrl");
	}

	@Override
	public void validate() {
		super.validateObject(this.adBanner);
	}

	@Override
	public void execute() {
		this.repository.save(this.adBanner);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.adBanner, "slogan", "targetUrl", "pictureUrl");
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}
}
