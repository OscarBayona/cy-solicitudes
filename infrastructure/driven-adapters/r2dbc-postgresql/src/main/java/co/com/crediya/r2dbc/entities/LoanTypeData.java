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
@Table(name = "tipo_prestamo")
public class LoanTypeData {

    @Id
    @Column("id_tipo_prestamo")
    private Long id;

    @Column("nombre")
    private String name;

    @Column("monto_minimo")
    private Double minAmount;

    @Column("monto_maximo")
    private Double maxAmount;

    @Column("tasa_interes")
    private Double interestRate;

    @Column("validacion_automatica")
    private Boolean autoValidation;
}