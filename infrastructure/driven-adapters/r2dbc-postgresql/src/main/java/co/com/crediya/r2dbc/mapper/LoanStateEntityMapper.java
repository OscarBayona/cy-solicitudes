package co.com.crediya.r2dbc.mapper;

import co.com.crediya.model.state.State;
import co.com.crediya.r2dbc.entities.LoanStateData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LoanStateEntityMapper {

    LoanStateEntityMapper INSTANCE = Mappers.getMapper(LoanStateEntityMapper.class);

    State toEntity(LoanStateData data);

    LoanStateData toData(State entity);
}