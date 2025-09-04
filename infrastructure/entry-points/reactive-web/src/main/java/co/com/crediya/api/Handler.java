package co.com.crediya.api;

import co.com.crediya.api.constants.swagger.UserDocApi;
import co.com.crediya.api.dto.CreateLoanDTO;
import co.com.crediya.api.dto.LoanResponseDTO;
import co.com.crediya.api.mapper.LoanDTOMapper;
import co.com.crediya.model.exceptions.BusinessException;
import co.com.crediya.model.exceptions.loan.InvalidLoanException;
import co.com.crediya.usecase.loan.CreateLoanApplicationUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {
    private final RequestValidator validator;
    private final CreateLoanApplicationUseCase createLoanUseCase;
    private final LoanDTOMapper loanDTOMapper;

    @Operation(
            operationId = "CreateLoan",
            summary = UserDocApi.SUMMARY_CREATE,
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateLoanDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = UserDocApi.DESCRIPTION_CREATED,
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = LoanResponseDTO.class
                                    )
                            )
                    )
            }
    )
    public Mono<ServerResponse> createLoanPOSTUseCase(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(CreateLoanDTO.class)
                .flatMap(validator::validate)
                .map(loanDTOMapper::toModel)
                .flatMap(createLoanUseCase::execute)
                .flatMap(saveUser->ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(loanDTOMapper.toResponse(saveUser)))
                .onErrorResume(BusinessException.class,
                        e -> ServerResponse.badRequest().bodyValue(e.getMessage()))
                .onErrorResume(InvalidLoanException.class,
                        e -> ServerResponse.badRequest().bodyValue(e.getMessage()))
                .onErrorResume(RuntimeException.class,
                        e -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(e.getMessage()));
    }
}
