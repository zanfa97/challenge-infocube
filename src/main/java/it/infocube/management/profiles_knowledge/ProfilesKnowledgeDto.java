package it.infocube.management.profiles_knowledge;

import it.infocube.management.user.Level;
import lombok.Data;

@Data
public class ProfilesKnowledgeDto {
    private String name;
    private Level level;
}
