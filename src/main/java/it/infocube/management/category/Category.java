package it.infocube.management.category;

import it.infocube.management.knowledge.Knowledge;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id;

    @Column(name = "name")
    private String name;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "categories_knowledge",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "knowledge_id"))
    private Set<Knowledge> knowledge = new LinkedHashSet<>();

}