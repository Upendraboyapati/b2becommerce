package com.b2b.mapper;

import com.b2b.dto.NotificationDTO;
import com.b2b.entity.NotificationLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    @Mapping(source = "user.id", target = "userId")
    NotificationDTO toDTO(NotificationLog notificationLog);

    @Mapping(source = "userId", target = "user.id")
    NotificationLog toEntity(NotificationDTO notificationDTO);
}
