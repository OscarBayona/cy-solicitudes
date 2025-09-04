package co.com.crediya.api.dto;


public record LoanResponseDTO (
    Double amount,
    Integer term,
    String email,
    String customerDocument,
    Long loanTypeId
) {}
