package it.infocube.management.profile;

import it.infocube.management.profiles_knowledge.ProfilesKnowledgeDto;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ProfileDto {

    private String name;
    private Set<ProfilesKnowledgeDto> knowledge = new HashSet<>();
}
