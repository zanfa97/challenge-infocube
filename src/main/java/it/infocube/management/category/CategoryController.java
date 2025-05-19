package it.infocube.management.category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("/categories")
@RestController
@Tag(name = "Category Management", description = "APIs for managing categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Operation(summary = "Get a category by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Category found",
            content = @Content(schema = @Schema(implementation = CategoryDto.class))),
        @ApiResponse(responseCode = "404", description = "Category not found", 
            content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(
            @Parameter(description = "ID of the category to retrieve", schema = @Schema(type = "integer", format = "int32"))
            @PathVariable Byte id) {
        var category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoryMapper.toDto(category));
    }

    @Operation(summary = "Create a new category")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Category created successfully"),
        @ApiResponse(responseCode = "400", description = "Category with this name already exists",
            content = @Content)
    })
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(
            @Parameter(description = "Category details") @RequestBody CategoryDto request) {
        if (categoryRepository.existsByName(request.getName())) {
            return ResponseEntity.badRequest().build();
        }

        categoryRepository.save(categoryMapper.toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Delete a category")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Category not found",
            content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(
            @Parameter(description = "ID of the category to delete", schema = @Schema(type = "integer", format = "int32"))
            @PathVariable Byte id ) {
        if (!categoryRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        categoryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update a category")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Category updated successfully",
            content = @Content(schema = @Schema(implementation = CategoryDto.class))),
        @ApiResponse(responseCode = "404", description = "Category not found",
            content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(
            @Parameter(description = "ID of the category to update", schema = @Schema(type = "integer", format = "int32"))
            @PathVariable Byte id,
            @Parameter(description = "Updated category details") @RequestBody CategoryDto request) {
        var category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        category.setName(request.getName());
        categoryRepository.save(category);
        return ResponseEntity.ok(categoryMapper.toDto(category));
    }
}