package co.com.crediya.model.loan;
import co.com.crediya.model.loantype.LoanType;
import co.com.crediya.model.state.State;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Loan {

    private Long id;
    private String customerDocument;
    private Double amount;
    private Integer term;
    private String email;
    private State state;
    private LoanType loanType;
}
