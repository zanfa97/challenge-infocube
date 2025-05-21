package it.infocube.management.users_knowledge;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.infocube.management.knowledge.Knowledge;
import it.infocube.management.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users_knowledge")
public class UsersKnowledge {
    @EmbeddedId
    @JsonIgnore
    private UsersKnowledgeId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @MapsId("knowledgeId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "knowledge_id")
    private Knowledge knowledge;

    @Column(name = "level")
    private String level;

}