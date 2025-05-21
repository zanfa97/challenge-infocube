package it.infocube.management.user;

import lombok.Data;

@Data
public class AddKnowledgeToUserRequest {
    private Short knowledgeId;
    private Level level;
}
