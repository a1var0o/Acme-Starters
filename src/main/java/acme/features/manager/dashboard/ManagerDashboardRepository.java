
package acme.features.manager.dashboard;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.datatypes.IntTuple;

@Repository
public interface ManagerDashboardRepository extends AbstractRepository {

	@Query("select count(p) from Project p where p.manager.id = :managerId")
	Integer numberOfProjectsByManagerId(int managerId);

	@Query("select avg(select count(p) from Project p where p.manager.id = m.id) from Manager m where m.id != :managerId")
	Double avgNumberOfProjectsFromOtherManagers(int managerId);

	@Query("select new acme.datatypes.IntTuple(i.project.id, sum(datediff(i.endMoment, i.startMoment))) from Invention i where i.project.manager.id = :managerId group by i.project.id")
	List<IntTuple> activeDaysOfInventionsFromProjectsByManagerId(int managerId);

	@Query("select new acme.datatypes.IntTuple(c.project.id, sum(datediff(c.endMoment, c.startMoment))) from Campaign c where c.project.manager.id = :managerId group by c.project.id")
	List<IntTuple> activeDaysOfCampaignsFromProjectsByManagerId(int managerId);

	@Query("select new acme.datatypes.IntTuple(s.project.id, sum(datediff(s.endMoment, s.startMoment))) from Strategy s where s.project.manager.id = :managerId group by s.project.id")
	List<IntTuple> activeDaysOfStrategiesFromProjectsByManagerId(int managerId);

	@Query("select new acme.datatypes.IntTuple(m.project.id, count(m)) from Member m where m.project.manager.id = :managerId group by m.project.id")
	List<IntTuple> membersOfProjectsByManagerId(int managerId);

	@Query("select distinct(p.id) from Project p where p.manager.id = :managerId")
	List<Integer> findProjectIdsByManagerId(int managerId);

}
