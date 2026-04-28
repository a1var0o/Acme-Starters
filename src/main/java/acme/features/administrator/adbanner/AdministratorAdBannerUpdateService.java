
package acme.features.administrator.adbanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Administrator;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.entities.AdBanner;

@Service
public class AdministratorAdBannerUpdateService extends AbstractService<Administrator, AdBanner> {

	@Autowired
	private AdministratorAdBannerRepository	repository;
	private AdBanner						adBanner;


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.adBanner = this.repository.findAdBannerById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.adBanner != null && super.getRequest().getPrincipal().hasRealmOfType(Administrator.class);
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
