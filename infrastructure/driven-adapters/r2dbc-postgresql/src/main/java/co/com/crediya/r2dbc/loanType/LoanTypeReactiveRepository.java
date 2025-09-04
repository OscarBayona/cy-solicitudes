package co.com.crediya.r2dbc.loanType;

import co.com.crediya.r2dbc.entities.LoanTypeData;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface LoanTypeReactiveRepository extends ReactiveCrudRepository<LoanTypeData, Long>, ReactiveQueryByExampleExecutor<LoanTypeData> {

}
