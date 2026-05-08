
package acme.features.manager.dashboard;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.forms.Dashboard;
import acme.realms.Manager;

@Service
public class ManagerDashboardShowService extends AbstractService<Manager, Dashboard> {

	@Autowired
	private ManagerDashboardRepository	repository;

	private Dashboard					dashboard;


	@Override
	public void load() {
		int managerId;

		managerId = super.getRequest().getPrincipal().getActiveRealm().getId();

		Integer numberOfProjects;
		Double avgNumberOfProjects;
		Double numberOfProjectsDeviation;

		Double minEffort;
		Double maxEffort;
		Double avgEffort;
		Double effortSD;

		Map<Integer, Integer> activeDaysOfInventionsByProjectId = new HashMap<Integer, Integer>();
		Map<Integer, Integer> activeDaysOfCampaignsByProjectId = new HashMap<Integer, Integer>();
		Map<Integer, Integer> activeDaysOfStrategiesByProjectId = new HashMap<Integer, Integer>();
		Map<Integer, Integer> membersByProjects = new HashMap<Integer, Integer>();
		Map<Integer, Double> efforts = new HashMap<Integer, Double>();
		List<Integer> projectIds;

		numberOfProjects = this.repository.numberOfProjectsByManagerId(managerId);
		avgNumberOfProjects = this.repository.avgNumberOfProjectsFromOtherManagers(managerId);
		numberOfProjectsDeviation = 1. * numberOfProjects - avgNumberOfProjects;

		activeDaysOfInventionsByProjectId = this.repository.activeDaysOfInventionsFromProjectsByManagerId(managerId).stream().collect(Collectors.toMap(t -> t.getKey(), t -> t.getValue()));
		activeDaysOfCampaignsByProjectId = this.repository.activeDaysOfCampaignsFromProjectsByManagerId(managerId).stream().collect(Collectors.toMap(t -> t.getKey(), t -> t.getValue()));
		activeDaysOfStrategiesByProjectId = this.repository.activeDaysOfStrategiesFromProjectsByManagerId(managerId).stream().collect(Collectors.toMap(t -> t.getKey(), t -> t.getValue()));
		membersByProjects = this.repository.membersOfProjectsByManagerId(managerId).stream().collect(Collectors.toMap(t -> t.getKey(), t -> t.getValue()));

		projectIds = this.repository.findProjectIdsByManagerId(managerId);

		for (Integer pId : projectIds) {
			int totalActiveDays = 0;

			if (activeDaysOfInventionsByProjectId.containsKey(pId))
				totalActiveDays += activeDaysOfInventionsByProjectId.get(pId);

			if (activeDaysOfCampaignsByProjectId.containsKey(pId))
				totalActiveDays += activeDaysOfCampaignsByProjectId.get(pId);

			if (activeDaysOfStrategiesByProjectId.containsKey(pId))
				totalActiveDays += activeDaysOfStrategiesByProjectId.get(pId);

			if (totalActiveDays > 0)
				efforts.put(pId, totalActiveDays / (double) membersByProjects.get(pId));
			else
				efforts.put(pId, 0.);
		}

		if (efforts.isEmpty()) {
			minEffort = 0.;
			maxEffort = 0.;
			avgEffort = 0.;
			effortSD = 0.;
		} else {
			minEffort = Collections.min(efforts.values());
			maxEffort = Collections.max(efforts.values());
			avgEffort = efforts.values().stream().reduce(Double::sum).orElse(0.) / numberOfProjects;
			effortSD = Math.sqrt(efforts.values().stream().map(e -> Math.pow(e - avgEffort, 2)).collect(Collectors.summingDouble(Double::doubleValue)) / (efforts.size() - 1));
		}

		this.dashboard = super.newObject(Dashboard.class);
		this.dashboard.setNumberOfProjects(numberOfProjects);
		this.dashboard.setNumberOfProjectsDeviation(numberOfProjectsDeviation);
		this.dashboard.setMinEffort(minEffort);
		this.dashboard.setMaxEffort(maxEffort);
		this.dashboard.setAvgEffort(avgEffort);
		this.dashboard.setEffortSD(effortSD);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRealmOfType(Manager.class);

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.dashboard, //
			"numberOfProjects", "numberOfProjectsDeviation", // 
			"minEffort", "maxEffort", //
			"avgEffort", "effortSD");
	}

}
