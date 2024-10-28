package com.project.adoption.pet.infrastructure.output.dynamo.mapper;

import com.project.adoption.pet.domain.model.User;
import com.project.adoption.pet.infrastructure.output.dynamo.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDynamoMapper {
    IDynamoMapper INSTANCE = Mappers.getMapper(IDynamoMapper.class);

    UserEntity toUserEntity(User user);
    User toUser(UserEntity entity);
    List<UserEntity> toUserEntityList(List<User> user);
    List<User> toUserList(List<UserEntity> entity);
}
