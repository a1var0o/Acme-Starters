
package acme.features.administrator.adbanner;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.AdBanner;

public interface AdministratorAdBannerRepository extends AbstractRepository {

	@Query("select a from AdBanner a")
	List<AdBanner> findAllAdBanners();

	@Query("select a from AdBanner a where a.id = :id")
	AdBanner findAdBannerById(int id);
}
