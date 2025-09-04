package co.com.crediya.usecase.loan;

import co.com.crediya.model.auth.gateways.AuthService;
import co.com.crediya.model.exceptions.loan.InvalidLoanException;
import co.com.crediya.model.exceptions.user.UserNotFoundException;
import co.com.crediya.model.loan.Loan;
import co.com.crediya.model.loan.gateways.LoanRepository;
import co.com.crediya.model.loantype.LoanType;
import co.com.crediya.model.loantype.gateways.LoanTypeRepository;
import co.com.crediya.model.state.gateways.StateRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import static co.com.crediya.model.constants.ErrorMessage.*;
import static co.com.crediya.model.constants.LoanStates.*;

@RequiredArgsConstructor
public class CreateLoanApplicationUseCase {

    private final LoanRepository loanRepository;
    private final LoanTypeRepository loanTypeRepository;
    private final StateRepository stateRepository;
    private final AuthService authService;

    public Mono<Loan> execute(Loan request) {
        if (request.getLoanType() == null || request.getLoanType().getId() == null) {
            return Mono.error(new InvalidLoanException(LOAN_TYPE_NOT_FOUND));
        }
        return loanTypeRepository.findById(request.getLoanType().getId())
                .switchIfEmpty(Mono.error(new InvalidLoanException(LOAN_TYPE_NOT_FOUND)))
                .flatMap(loanType ->
                    validateAmount(request, loanType)
                            .then(stateRepository.findByName(PENDING)
                                .switchIfEmpty(Mono.error(new InvalidLoanException(LOAN_STATE_NOT_CONFIGURED)))
                                .flatMap(state -> {
                                    request.setState(state);
                                    return validateDocument(request)
                                            .flatMap(validated -> loanRepository.createLoan(request));
                                })
                            )
                );
    }

    private Mono<Void> validateAmount(Loan request, LoanType loanType) {
        if (request.getAmount() < loanType.getMinAmount() ||
                request.getAmount() > loanType.getMaxAmount()) {
            return Mono.error(new InvalidLoanException(AMOUNT_REQUESTED_NOT_WITHIN_LIMIT));
        }
        return Mono.empty();
    }

    private Mono<Loan> validateDocument(Loan loan) {
        if (loan.getCustomerDocument() == null || loan.getCustomerDocument().trim().isEmpty()) {
            return Mono.error(new InvalidLoanException("Número de identificación requerido y no puede estar vacio"));
        }
        return authService.findUserByDocumentNumber(loan.getCustomerDocument())
                .switchIfEmpty(Mono.error(new UserNotFoundException(USER_NOT_FOUND)))
                .filter(user -> user.getEmail() != null && user.getEmail().equals(loan.getEmail()))
                .switchIfEmpty(Mono.error(new InvalidLoanException("La información del usuario no concuerda")))
                .thenReturn(loan);
    }

}
