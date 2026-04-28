
package acme.features.administrator.adbanner;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Administrator;
import acme.client.services.AbstractService;
import acme.entities.AdBanner;

@Service
public class AdministratorAdBannerListService extends AbstractService<Administrator, AdBanner> {

	@Autowired
	private AdministratorAdBannerRepository	repository;

	private Collection<AdBanner>			adBanners;


	@Override
	public void load() {
		this.adBanners = this.repository.findAllAdBanners();
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.adBanners, "slogan", "targetUrl", "pictureUrl");
	}
}
