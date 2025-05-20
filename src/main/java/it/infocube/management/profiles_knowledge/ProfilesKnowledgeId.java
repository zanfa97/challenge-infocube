package it.infocube.management.profiles_knowledge;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class ProfilesKnowledgeId implements Serializable {

    private static final long serialVersionUID = -6178713761321516596L;
    @Column(name = "profile_id", nullable = false)

    private Short profileId;

    @Column(name = "knowledge_id", nullable = false)
    private Short knowledgeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ProfilesKnowledgeId entity = (ProfilesKnowledgeId) o;
        return Objects.equals(this.knowledgeId, entity.knowledgeId) &&
                Objects.equals(this.profileId, entity.profileId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(knowledgeId, profileId);
    }

}