package co.com.crediya.r2dbc.loan;

import co.com.crediya.r2dbc.entities.LoanData;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface LoanReactiveRepository extends ReactiveCrudRepository<LoanData, Long>, ReactiveQueryByExampleExecutor<LoanData> {

}
