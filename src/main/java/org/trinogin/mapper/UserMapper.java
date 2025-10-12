package org.trinogin.mapper;

import org.trinogin.UserEntity;
import org.trinogin.dto.UserCreateRequest;
import org.trinogin.dto.UserDTO;
import org.trinogin.dto.UserUpdateRequest;

public class UserMapper {

    public static UserEntity fromUserUpdateRequest(UserUpdateRequest userUpdateRequest) {
        return new UserEntity(
                userUpdateRequest.getUsername(),
                userUpdateRequest.getEmail(),
                userUpdateRequest.getDisplayName(),
                null,
                null,
                null);
    }

    public static UserEntity fromUserCreateRequest(UserCreateRequest userCreateRequest) {
        return new UserEntity(
                userCreateRequest.getUsername(),
                userCreateRequest.getEmail(),
                userCreateRequest.getDisplayName(),
                null,
                userCreateRequest.getPassword(),
                null);
    }

    public static UserDTO toUserDTO(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        return new UserDTO(
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getDisplayName()
        );
    }

}
