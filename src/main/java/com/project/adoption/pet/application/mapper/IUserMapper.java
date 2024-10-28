package com.project.adoption.pet.application.mapper;

import com.project.adoption.pet.application.dto.UserRequest;
import com.project.adoption.pet.application.dto.UserResponse;
import com.project.adoption.pet.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserMapper {

    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);

    User toUser(UserRequest userRequest);

    UserResponse toUserRequest(User user);

    List<UserResponse> toUserRequestList(List<User> user);
}
