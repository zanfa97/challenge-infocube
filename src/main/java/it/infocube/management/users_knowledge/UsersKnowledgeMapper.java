package it.infocube.management.users_knowledge;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsersKnowledgeMapper {

    @Mapping(target = "name", source = "knowledge.name")
    UsersKnowledgeDto toDto(UsersKnowledge usersKnowledge);

}
