package co.com.crediya.consumer.dto.user;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String address,
        String phone,
        LocalDate birthDate,
        BigDecimal salaryBase,
        String identityDocument
) {}