package co.com.crediya.usecase.createloanapplication;

import co.com.crediya.model.exceptions.loan.InvalidLoanException;
import co.com.crediya.model.loan.Loan;
import co.com.crediya.model.loan.gateways.LoanRepository;
import co.com.crediya.model.loantype.LoanType;
import co.com.crediya.model.loantype.gateways.LoanTypeRepository;
import co.com.crediya.model.state.gateways.StateRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import static co.com.crediya.model.constants.ErrorMessage.*;

@RequiredArgsConstructor
public class CreateLoanApplicationUseCase {

    private final LoanRepository loanRepository;
    private final LoanTypeRepository loanTypeRepository;
    private final StateRepository stateRepository;

    public Mono<Loan> execute(Loan request) {
        return loanTypeRepository.findById(request.getLoanTypeId())
                .switchIfEmpty(Mono.error(new InvalidLoanException(LOAN_TYPE_NOT_FOUND)))
                .flatMap(loanType ->
                    validateAmount(request, loanType)
                            .then(stateRepository.findByName("Pendiente de revisión")
                                .switchIfEmpty(Mono.error(new InvalidLoanException(LOAN_STATE_NOT_CONFIGURED)))
                                .flatMap(state -> {
                                    request.setStateId(state.getId());
                                    return loanRepository.save(request);
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

}
