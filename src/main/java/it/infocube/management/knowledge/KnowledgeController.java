package it.infocube.management.knowledge;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.infocube.management.category.CategoryRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Knowledge", description = "Knowledge management APIs")
@RequestMapping("/knowledge")
@AllArgsConstructor
@RestController
public class KnowledgeController {

    private final KnowledgeRepository knowledgeRepository;
    private final KnowledgeMapper knowledgeMapper;
    private final CategoryRepository categoryRepository;

    @Operation(summary = "Get a knowledge item by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Knowledge found",
                    content = @Content(schema = @Schema(implementation = KnowledgeDto.class))),
            @ApiResponse(responseCode = "404", description = "Knowledge not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<KnowledgeDto> getKnowledge(
            @Parameter(description = "ID of the knowledge to retrieve") @PathVariable("id") Short knowledgeId) {
        var knowledge = knowledgeRepository.findById(knowledgeId).orElse(null);
        if (knowledge == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(knowledgeMapper.toDto(knowledge));
    }

    @Operation(summary = "Get all knowledge items with pagination and sorting")
    @ApiResponse(responseCode = "200", description = "List of knowledge items retrieved successfully",
            content = @Content(schema = @Schema(implementation = KnowledgeDto.class)))
    @GetMapping
    public List<KnowledgeDto> getAllKnowledges(
            @Parameter(description = "Pagination and sorting parameters") Pageable pageable) {
        return knowledgeRepository.findAll(pageable).stream()
                .map(knowledgeMapper::toDto)
                .toList();
    }

    @Operation(summary = "Create a new knowledge item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Knowledge created successfully",
                    content = @Content(schema = @Schema(implementation = KnowledgeDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or category not found", content = @Content)
    })
    @PostMapping
    public ResponseEntity<KnowledgeDto> createKnowledge(
            @Parameter(description = "Knowledge creation request") @Valid @RequestBody AddKnowledgeRequest request) {
        var newKnowledge = new Knowledge(request.getName(), request.getDescription());

        for (Byte categoryId : request.getCategoriesIds()) {
            var categoryFound = categoryRepository.findById(categoryId).orElse(null);
            if (categoryFound == null) {
                return ResponseEntity.badRequest().build();
            }
            newKnowledge.addCategory(categoryFound);
        }

        knowledgeRepository.save(newKnowledge);
        var knowledgeDto = knowledgeMapper.toDto(newKnowledge);

        return ResponseEntity.status(HttpStatus.CREATED).body(knowledgeDto);
    }

    @Operation(summary = "Delete a knowledge item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Knowledge deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Knowledge not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKnowledge(
            @Parameter(description = "ID of the knowledge to delete") @PathVariable("id") Short knowledgeId) {
        var knowledge = knowledgeRepository.findById(knowledgeId).orElse(null);
        if (knowledge == null) {
            return ResponseEntity.notFound().build();
        }
        knowledgeRepository.delete(knowledge);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update a knowledge item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Knowledge updated successfully",
                    content = @Content(schema = @Schema(implementation = KnowledgeDto.class))),
            @ApiResponse(responseCode = "404", description = "Knowledge not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<KnowledgeDto> updateKnowledge(
            @Parameter(description = "ID of the knowledge to update") @PathVariable("id") Short knowledgeId,
            @Parameter(description = "Knowledge update request") @Valid @RequestBody UpdateKnowledgeRequest request) {
        var knowledge = knowledgeRepository.findById(knowledgeId).orElse(null);
        if (knowledge == null) {
            return ResponseEntity.notFound().build();
        }
        if (request.getName() != null) {
            knowledge.setName(request.getName());
        }
        if (request.getDescription() != null) {
            knowledge.setDescription(request.getDescription());
        }

        knowledgeRepository.save(knowledge);
        return ResponseEntity.ok(knowledgeMapper.toDto(knowledge));
    }
}