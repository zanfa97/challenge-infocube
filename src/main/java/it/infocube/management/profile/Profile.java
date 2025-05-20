package it.infocube.management.profile;

import it.infocube.management.knowledge.Knowledge;
import it.infocube.management.profiles_knowledge.ProfilesKnowledge;
import it.infocube.management.profiles_knowledge.ProfilesKnowledgeId;
import it.infocube.management.user.Level;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "profiles")
public class Profile {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "profile", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<ProfilesKnowledge> profilesKnowledges = new LinkedHashSet<>();

}