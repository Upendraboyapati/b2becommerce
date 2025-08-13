package com.b2b.mapper;

import com.b2b.dto.UserDTO;
import com.b2b.dto.UserRegistrationRequest;
import com.b2b.entity.User;
import org.mapstruct.*;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {
    
    // Remove this line as we're using Spring component model
    // UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "password", ignore = true) // Don't map password in DTO for security
    UserDTO toDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isBlocked", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toEntity(UserDTO userDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "blocked", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateUserFromDTO(UserDTO userDTO, @MappingTarget User user);

    // Add mapping for registration request
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isBlocked", constant = "false")
    User toEntity(UserRegistrationRequest registrationRequest);
}