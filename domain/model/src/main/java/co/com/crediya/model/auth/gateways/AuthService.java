package co.com.crediya.model.auth.gateways;

import co.com.crediya.model.auth.User;
import reactor.core.publisher.Mono;

public interface AuthService {

    Mono<User> findUserByDocumentNumber(String email);

}
