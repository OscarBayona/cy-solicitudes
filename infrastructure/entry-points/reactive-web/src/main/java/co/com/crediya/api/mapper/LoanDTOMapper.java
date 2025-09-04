package co.com.crediya.api.mapper;


import co.com.crediya.api.dto.CreateLoanDTO;
import co.com.crediya.api.dto.LoanResponseDTO;
import co.com.crediya.model.loan.Loan;
import co.com.crediya.model.loantype.LoanType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring")
public interface LoanDTOMapper {

    LoanDTOMapper INSTANCE = Mappers.getMapper(LoanDTOMapper.class);

    LoanResponseDTO toResponse(Loan loan);

    // Mapear DTO -> Loan
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "loanType", expression = "java(mapLoanType(dto.loanTypeId()))")
    Loan toModel(CreateLoanDTO dto);

    default LoanType mapLoanType(Long loanTypeId) {
        if (loanTypeId == null) {
            return null;
        }
        LoanType loanType = new LoanType();
        loanType.setId(loanTypeId);
        return loanType;
    }
}