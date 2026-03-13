
package acme.entities;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface StrategyRepository extends AbstractRepository {

	@Query("select sum(t.expectedPercentage) from Tactic t where t.strategy.id = :strategyId")
	Double getSumExpectedPercentage(int strategyId);

	@Query("select t from Tactic t where t.strategy.id = :strategyId")
	Collection<Tactic> findTacticsByStrategy(int strategyId);

	@Query("select s from Strategy s where s.ticker = :ticker")
	Strategy findStrategyByTicker(String ticker);
}
