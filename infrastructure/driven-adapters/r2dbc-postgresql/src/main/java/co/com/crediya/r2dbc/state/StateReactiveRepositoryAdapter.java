package co.com.crediya.r2dbc.state;

import co.com.crediya.model.state.State;
import co.com.crediya.model.state.gateways.StateRepository;
import co.com.crediya.r2dbc.entities.LoanStateData;
import co.com.crediya.r2dbc.mapper.LoanStateEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
@RequiredArgsConstructor
public class StateReactiveRepositoryAdapter implements StateRepository {

    private final StateReactiveRepository repository;

    @Override
    public Mono<State> findByName(String statusName) {
        return repository.findByName(statusName)
                .doOnSubscribe(s -> log.trace(
                        "BuscarPorNombre - Buscando tipo de estado de prestamo: {}", statusName))
                .doOnNext(u -> log.debug(
                        "BuscarPorNombre - Tipo de estado de prestamo encontrado con ID: {}", u.getId()))
                .doOnError(e -> log.error(
                        "BuscarPorNombre - Error buscando {}: {}",
                        statusName, e.getMessage()))
                .flatMap(this::mapToStateModel);
    }

    private Mono<State> mapToStateModel(LoanStateData data) {
        if (data == null) return Mono.empty();
        State state = LoanStateEntityMapper.INSTANCE.toEntity(data);
        return Mono.just(state);
    }
}
