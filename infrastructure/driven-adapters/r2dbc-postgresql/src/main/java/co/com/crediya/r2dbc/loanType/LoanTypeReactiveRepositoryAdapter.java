package co.com.crediya.r2dbc.loanType;

import co.com.crediya.model.loantype.LoanType;
import co.com.crediya.model.loantype.gateways.LoanTypeRepository;
import co.com.crediya.r2dbc.entities.LoanTypeData;
import co.com.crediya.r2dbc.mapper.LoanTypeEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
@RequiredArgsConstructor
public class LoanTypeReactiveRepositoryAdapter implements LoanTypeRepository {

    private final LoanTypeReactiveRepository repository;
    private final LoanTypeEntityMapper mapper;


    @Override
    public Mono<LoanType> findById(Long loanTypeId) {
        return repository.findById(loanTypeId)
                .doOnSubscribe(s -> log.trace(
                        "BuscarPorIdLoanType - Buscando tipo de solicitid de prestamo: {}", loanTypeId))
                .doOnNext(u -> log.debug(
                        "BuscarPorIdLoanType - Tipo de solicitid de prestamo encontrado con ID: {}", u.getId()))
                .doOnError(e -> log.error(
                        "BuscarPorIdLoanType - Error buscando {}: {}",
                        loanTypeId, e.getMessage()))
                .map(mapper::toEntity);
    }


}
