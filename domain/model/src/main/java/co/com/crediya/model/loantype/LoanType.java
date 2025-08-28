package co.com.crediya.model.loantype;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
public class LoanType {
    private Long id;
    private String name;
    private Double minAmount;
    private Double maxAmount;
    private Double interestRate;
    private Boolean autoValidation;
    private Boolean automaticValidation;
}
