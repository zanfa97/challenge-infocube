package it.infocube.management.users_knowledge;

import it.infocube.management.user.Level;
import lombok.Data;

@Data
public class UsersKnowledgeDto {
    private String name;
    private Level level;
}
