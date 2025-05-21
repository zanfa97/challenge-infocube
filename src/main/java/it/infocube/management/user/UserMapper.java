package it.infocube.management.user;

import it.infocube.management.users_knowledge.UsersKnowledgeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UsersKnowledgeMapper.class})
public interface UserMapper {

    @Mapping(target = "knowledge", source = "usersKnowledges")
    UserDto toDto(User user);
}
