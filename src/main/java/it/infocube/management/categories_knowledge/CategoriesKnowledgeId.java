package it.infocube.management.categories_knowledge;

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
public class CategoriesKnowledgeId implements Serializable {
    private static final long serialVersionUID = -636577651057723954L;
    @NotNull
    @Column(name = "category_id", nullable = false)
    private Byte categoryId;

    @NotNull
    @Column(name = "knowledge_id", nullable = false)
    private Short knowledgeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CategoriesKnowledgeId entity = (CategoriesKnowledgeId) o;
        return Objects.equals(this.knowledgeId, entity.knowledgeId) &&
                Objects.equals(this.categoryId, entity.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(knowledgeId, categoryId);
    }

}