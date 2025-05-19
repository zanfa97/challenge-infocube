package it.infocube.management.categories_knowledge;

import it.infocube.management.category.Category;
import it.infocube.management.knowledge.Knowledge;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "categories_knowledge")
public class CategoriesKnowledge {

    @EmbeddedId
    private CategoriesKnowledgeId id;

    @MapsId("categoryId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @MapsId("knowledgeId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "knowledge_id")
    private Knowledge knowledge;

}