package co.com.crediya.r2dbc.mapper;

import co.com.crediya.model.loantype.LoanType;
import co.com.crediya.r2dbc.entities.LoanTypeData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LoanTypeEntityMapper {

    LoanType toEntity(LoanTypeData data);

    LoanTypeData toData(LoanType entity);
}