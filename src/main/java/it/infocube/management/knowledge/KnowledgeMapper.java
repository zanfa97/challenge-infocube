package it.infocube.management.knowledge;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface KnowledgeMapper {
    KnowledgeDto toDto(Knowledge knowledge);
}
