package co.com.crediya.r2dbc.mapper;


import co.com.crediya.model.loan.Loan;
import co.com.crediya.model.loantype.LoanType;
import co.com.crediya.model.state.State;
import co.com.crediya.r2dbc.entities.LoanData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LoanEntityMapper {

    LoanEntityMapper INSTANCE = Mappers.getMapper(LoanEntityMapper.class);

    @Mapping(target = "id", source = "idLoan")
    @Mapping(target = "loanType", expression = "java(mapLoanType(loanData.getIdLoanType()))")
    @Mapping(target = "state", expression = "java(mapState(loanData.getIdState()))")
    Loan toEntity(LoanData loanData);

    @Mapping(target = "idLoan", source = "id")
    @Mapping(target = "idState", source = "state.id")
    @Mapping(target = "idLoanType", source = "loanType.id")
    LoanData toData(Loan loan);

    // --- Helpers ---
    default State mapState(Long stateId) {
        if (stateId == null) return null;
        State state = new State();
        state.setId(stateId);
        return state;
    }

    default LoanType mapLoanType(Long loanTypeId) {
        if (loanTypeId == null) return null;
        LoanType loanType = new LoanType();
        loanType.setId(loanTypeId);
        return loanType;
    }


}
