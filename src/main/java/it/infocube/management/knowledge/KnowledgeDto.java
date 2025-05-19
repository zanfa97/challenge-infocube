package it.infocube.management.knowledge;

import it.infocube.management.category.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class KnowledgeDto {
    private final String name;
    private final String description;
}