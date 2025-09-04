package co.com.crediya.api.dto;

import jakarta.validation.constraints.*;

import static co.com.crediya.api.constants.ValidationMessage.*;

public record CreateLoanDTO(

        @NotNull(message = AMOUNT_NOT_NULL)
        @Positive(message = AMOUNT_POSITIVE)
        Double amount,

        @NotNull(message = TERM_NOT_NULL)
        @Positive(message = TERM_POSITIVE)
        Integer term,

        @NotNull(message = EMAIL_NOT_BLANK)
        @Email(message = EMAIL_FORMAT)
        String email,

        @NotNull(message = IDENTIFICATION_NOT_BLANK)
        @NotBlank(message = IDENTIFICATION_NOT_BLANK)
        @Pattern(regexp = "^\\d+$", message = INVALID_IDENTIFICATION)
        @Positive(message = INVALID_IDENTIFICATION)
        String customerDocument,

        @NotNull(message = CREDIT_TYPE_ID_NOT_NULL)
        @Positive(message = CREDIT_TYPE_ID_POSITIVE)
        Long loanTypeId
) {}
