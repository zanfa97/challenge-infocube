package it.infocube.management.category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Byte> {
    boolean existsByName(String name);
}
