package it.infocube.management.knowledge;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class AddKnowledgeRequest {

    @NotBlank(message = "You have to provide a name")
    private String name;
    private String description;
    @ArraySchema(schema = @Schema(type = "integer", format = "int32"))
    private List<Byte> categoriesIds;

}
