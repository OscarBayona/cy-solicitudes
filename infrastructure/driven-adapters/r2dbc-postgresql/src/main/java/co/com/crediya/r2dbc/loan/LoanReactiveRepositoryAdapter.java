package co.com.crediya.r2dbc.loan;

import co.com.crediya.model.loan.Loan;
import co.com.crediya.model.loan.gateways.LoanRepository;
import co.com.crediya.r2dbc.entities.LoanData;
import co.com.crediya.r2dbc.helper.ReactiveAdapterOperations;
import co.com.crediya.r2dbc.mapper.LoanEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
@RequiredArgsConstructor
public class LoanReactiveRepositoryAdapter implements LoanRepository {

    private final LoanReactiveRepository loanRepository;
    private final TransactionalOperator transactionalOperator;

    @Override
    public Mono<Loan> createLoan(Loan loan) {
        LoanData loanData = LoanEntityMapper.INSTANCE.toData(loan);

        return transactionalOperator.transactional(
                loanRepository.save(loanData)
                        .doOnSubscribe(s -> log.trace(
                                "RegistrandoPrestamo - Guardando prestamo en la base de datos: {}",
                                loanData.getEmail()))
                        .doOnNext(saved -> log.debug(
                                "RegistrandoPrestamo - Prestamo guardado con ID: {}",
                                saved.getIdLoan()))
                        .doOnError(e -> log.error(
                                "RegistrandoPrestamo - Error al guardar el prestamo {}: {}",
                                loanData.getEmail(), e.getMessage()))
                        .flatMap(this::mapToLoanModel)
        );
    }

    private Mono<Loan> mapToLoanModel(LoanData data) {
        if (data == null) return Mono.empty();
        Loan loan = LoanEntityMapper.INSTANCE.toEntity(data);
        return Mono.just(loan);
    }
}
