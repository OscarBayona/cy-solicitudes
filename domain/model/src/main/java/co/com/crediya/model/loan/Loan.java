package co.com.crediya.model.loan;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Loan {

    private String id;
    private String customerDocument;
    private Double amount;
    private Integer term;
    private String email;
    private String stateId;
    private String loanTypeId;

}
