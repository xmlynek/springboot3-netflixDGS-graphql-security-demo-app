package com.example.graphqlcourse.datasource.repository;

import org.mapstruct.Mapper;
import org.ocpsoft.prettytime.PrettyTime;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Mapper
public interface HelperFieldMapper {

    PrettyTime PRETTY_TIME = new PrettyTime();

    ZoneOffset ZONE_OFFSET = ZoneOffset.ofHours(7);

    default String mapUUIDToStringId(UUID uuid) {
        return uuid.toString();
    }

    default UUID mapStringToUUID(String uuid) {
        return UUID.fromString(uuid);
    }

    default String mapCreateTimeStampToPrettyCreateDateTime(LocalDateTime dateTime) {
        return PRETTY_TIME.format(dateTime);
    }

    default OffsetDateTime mapCreateTimeStampToOffsetDateTime(LocalDateTime dateTime) {
        return dateTime.atOffset(ZONE_OFFSET);
    }
}
