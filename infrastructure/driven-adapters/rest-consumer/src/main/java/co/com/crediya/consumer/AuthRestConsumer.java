package co.com.crediya.consumer;

import co.com.crediya.consumer.dto.user.UserResponse;
import co.com.crediya.consumer.mapper.UserEntityMapper;
import co.com.crediya.model.auth.User;
import co.com.crediya.model.auth.gateways.AuthService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import static co.com.crediya.consumer.constants.Path.SEARCH_BY_IDENTIFICATION_PATH;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthRestConsumer implements AuthService {

    private final WebClient client;

    @Override
    public Mono<User> findUserByDocumentNumber(String identification) {
        return client
                .get()
                .uri(SEARCH_BY_IDENTIFICATION_PATH.concat(identification))
                .retrieve()
                .bodyToMono(UserResponse.class)
                .doOnSubscribe(subscription -> log.trace("Comenzando llamado a Auth Service para encontrar el usuario con documento de identificación: {}", identification))
                .doOnNext(response -> log.debug("Respuesta recibida de Auth Service para el documento de identificación {}: {}", identification, response))
                .map(UserEntityMapper.INSTANCE::toEntity)
                .onErrorResume(throwable -> throwable instanceof HttpClientErrorException.NotFound, throwable -> Mono.empty())
                .onErrorResume(WebClientResponseException.class::isInstance, throwable -> {
                    log.error("Error ocurrido mientras se llamaba a Auth Service: {}", throwable.getMessage());
                    return Mono.empty();
                });
    }

}
