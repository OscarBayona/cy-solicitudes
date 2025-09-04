package co.com.crediya.consumer.mapper;

import co.com.crediya.consumer.dto.user.UserResponse;
import co.com.crediya.model.auth.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserEntityMapper {

    UserEntityMapper INSTANCE = Mappers.getMapper(UserEntityMapper.class);

    User toEntity(UserResponse userResponse);
}