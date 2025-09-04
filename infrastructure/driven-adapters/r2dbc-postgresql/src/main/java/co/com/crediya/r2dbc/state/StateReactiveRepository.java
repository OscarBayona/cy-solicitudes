package co.com.crediya.r2dbc.state;

import co.com.crediya.r2dbc.entities.LoanStateData;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface StateReactiveRepository extends ReactiveCrudRepository<LoanStateData, Long>, ReactiveQueryByExampleExecutor<LoanStateData> {
    Mono<LoanStateData> findByName(String state);
}
