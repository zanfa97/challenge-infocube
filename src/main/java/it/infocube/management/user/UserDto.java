package it.infocube.management.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import it.infocube.management.users_knowledge.UsersKnowledge;
import it.infocube.management.users_knowledge.UsersKnowledgeDto;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    private String firstName;
    private String lastName;
    private List<UsersKnowledgeDto> knowledge;
}
