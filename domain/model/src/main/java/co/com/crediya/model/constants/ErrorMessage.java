package co.com.crediya.model.constants;

public class ErrorMessage {
    private ErrorMessage() {}

    public static final String NULL_LOAN_APPLICATION = "La solicitud no puede ser null";
    public static final String AMOUNT_REQUESTED_NOT_WITHIN_LIMIT = "El monto solicitado no esta permitido en los limites base del tipo de solicitud";
    public static final String INVALID_TERM_IN_MONTHS = "El plazo en meses es requerido y debe ser un valor numérico mayor a 0";
    public static final String INVALID_ID_LOAN_TYPE = "Id del tipo de solicitud es requerido y debe ser mayor a 0";
    public static final String LOAN_TYPE_NOT_FOUND = "Tipo de solicitud no encontrado";
    public static final String LOAN_STATE_NOT_CONFIGURED = "Estado inicial de la solicitud no configurado";
    public static final String USER_NOT_FOUND = "Usuario no encontrado";

}
