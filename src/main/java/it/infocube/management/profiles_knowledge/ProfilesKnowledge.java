package it.infocube.management.profiles_knowledge;

import it.infocube.management.knowledge.Knowledge;
import it.infocube.management.profile.Profile;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "profiles_knowledge")
public class ProfilesKnowledge {
    @EmbeddedId
    private ProfilesKnowledgeId id;

    @MapsId("profileId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @MapsId("knowledgeId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "knowledge_id")
    private Knowledge knowledge;

    @Column(name = "level")
    private String level;

}