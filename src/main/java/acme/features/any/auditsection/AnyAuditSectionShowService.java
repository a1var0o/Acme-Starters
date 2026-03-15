
package acme.features.any.auditsection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.AuditSection;

@Service
public class AnyAuditSectionShowService extends AbstractService<Any, AuditSection> {

	@Autowired
	private AnyAuditSectionRepository	repository;

	private AuditSection				auditsection;


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);

		this.auditsection = this.repository.findAuditSection(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.auditsection != null && !this.auditsection.getAuditReport().getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.auditsection, "name", "notes", "hours", "kind");
	}
}
