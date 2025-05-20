package it.infocube.management.profile;

import it.infocube.management.user.Level;
import lombok.Data;

@Data
public class AddKnowledgeToProfileRequest {
    private Short knowledgeId;
    private Level level;
}
