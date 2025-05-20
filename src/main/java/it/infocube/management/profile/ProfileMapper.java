package it.infocube.management.profile;

import it.infocube.management.profiles_knowledge.ProfilesKnowledgeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProfilesKnowledgeMapper.class})
public interface ProfileMapper {
    @Mapping(target = "knowledge", source = "profilesKnowledges")
    ProfileDto toDto(Profile profile);
}
