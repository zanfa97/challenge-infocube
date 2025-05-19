package it.infocube.management.knowledge;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.infocube.management.category.Category;
import it.infocube.management.profiles_knowledge.ProfilesKnowledge;
import it.infocube.management.users_knowledge.UsersKnowledge;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "knowledge")
public class Knowledge {

    public Knowledge(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "knowledge", cascade = CascadeType.PERSIST)
    private Set<Category> categories = new LinkedHashSet<>();

    @OneToMany(mappedBy = "knowledge")
    private Set<UsersKnowledge> usersKnowledge = new LinkedHashSet<>();

    @OneToMany(mappedBy = "knowledge", orphanRemoval = true)
    private Set<ProfilesKnowledge> profilesKnowledge = new LinkedHashSet<>();

    public void addCategory(Category category) {
        categories.add(category);
        category.getKnowledge().add(this);
    }

}