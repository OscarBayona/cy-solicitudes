package co.com.crediya.r2dbc.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table("solicitud")
public class LoanData {
    @Id
    @Column("id_solicitud")
    private Long idLoan;

    @Column("id_tipo_prestamo")
    private Long idLoanType;

    @Column("id_estado")
    private Long idState;

    @Column("monto")
    private Double amount;

    @Column("plazo")
    private Integer term;

    @Column("email")
    private String email;
}
