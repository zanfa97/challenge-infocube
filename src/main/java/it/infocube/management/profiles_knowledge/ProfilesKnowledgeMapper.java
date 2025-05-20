package it.infocube.management.profiles_knowledge;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfilesKnowledgeMapper {

    @Mapping(target = "name", source = "knowledge.name")
    ProfilesKnowledgeDto toDto(ProfilesKnowledge profilesKnowledge);

}