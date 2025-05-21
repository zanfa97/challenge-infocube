package it.infocube.management.users_knowledge;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class UsersKnowledgeId implements Serializable {
    @Serial
    private static final long serialVersionUID = -7258131131394570970L;
    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull
    @Column(name = "knowledge_id", nullable = false)
    private Short knowledgeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UsersKnowledgeId entity = (UsersKnowledgeId) o;
        return Objects.equals(this.knowledgeId, entity.knowledgeId) &&
                Objects.equals(this.userId, entity.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(knowledgeId, userId);
    }

}