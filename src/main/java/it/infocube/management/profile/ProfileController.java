package it.infocube.management.profile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.infocube.management.exception.ResourceNotFoundException;
import it.infocube.management.knowledge.AddKnowledgeRequest;
import it.infocube.management.knowledge.KnowledgeRepository;
import it.infocube.management.profiles_knowledge.ProfilesKnowledge;
import it.infocube.management.profiles_knowledge.ProfilesKnowledgeId;
import it.infocube.management.profiles_knowledge.ProfilesKnowledgeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profiles")
@AllArgsConstructor
@Tag(name = "Profile Management", description = "APIs for managing profiles and their knowledge associations")
public class ProfileController {
    private final ProfileRepository profileRepository;
    private final ProfilesKnowledgeRepository profilesKnowledgeRepository;
    private final ProfileMapper profileMapper;
    private final KnowledgeRepository knowledgeRepository;

    @Operation(summary = "Get a profile by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Profile found",
            content = @Content(schema = @Schema(implementation = ProfileDto.class))),
        @ApiResponse(responseCode = "404", description = "Profile not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProfileDto> getProfile(
            @Parameter(description = "ID of the profile to retrieve") @PathVariable("id") Short id) {
        return profileRepository.findById(id)
                .map(profileMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all profiles with pagination")
    @ApiResponse(responseCode = "200", description = "List of profiles retrieved successfully")
    @GetMapping
    public ResponseEntity<List<ProfileDto>> getAllProfiles(
            @Parameter(description = "Pagination parameters")
            @PageableDefault(page = 0, size = 10, sort = "name")
            Pageable pageable) {
        var profiles = profileRepository.findAll(pageable);
        return ResponseEntity.ok(
            profiles.stream()
                .map(profileMapper::toDto)
                .toList()
        );
    }

    @Operation(summary = "Add knowledge to a profile")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Knowledge successfully added to profile"),
        @ApiResponse(responseCode = "404", description = "Profile or Knowledge not found"),
        @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping("/{profileId}/knowledge")
    public ResponseEntity<ProfileDto> addKnowledgeToProfile(
            @Parameter(description = "ID of the profile") @PathVariable("profileId") Short profileId,
            @Parameter(description = "Knowledge details to add") @RequestBody AddKnowledgeToProfileRequest request) {
        var profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));

        var knowledge = knowledgeRepository.findById(request.getKnowledgeId())
                .orElseThrow(() -> new ResourceNotFoundException("Knowledge not found"));

        var profilesKnowledgeId = new ProfilesKnowledgeId();
        profilesKnowledgeId.setProfileId(profileId);
        profilesKnowledgeId.setKnowledgeId(request.getKnowledgeId());

        var profileKnowledge = profilesKnowledgeRepository
                .findById(profilesKnowledgeId)
                .orElseGet(() -> {
                    var pk = new ProfilesKnowledge();
                    pk.setId(profilesKnowledgeId);
                    pk.setProfile(profile);
                    pk.setKnowledge(knowledge);
                    pk.setLevel(request.getLevel().toString());
                    return profilesKnowledgeRepository.save(pk);
                });

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(profileMapper.toDto(profile));
    }
    
    

    @Operation(summary = "Remove knowledge from a profile")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Knowledge successfully removed from profile"),
        @ApiResponse(responseCode = "404", description = "Profile-Knowledge association not found")
    })
    @DeleteMapping("/{profileId}/knowledge/{knowledgeId}")
    public ResponseEntity<Void> removeKnowledgeFromProfile(
            @Parameter(description = "ID of the profile") @PathVariable("profileId") Short profileId,
            @Parameter(description = "ID of the knowledge to remove") @PathVariable("knowledgeId") Short knowledgeId) {
        var profilesKnowledgeId = new ProfilesKnowledgeId();
        profilesKnowledgeId.setProfileId(profileId);
        profilesKnowledgeId.setKnowledgeId(knowledgeId);

        return profilesKnowledgeRepository.findById(profilesKnowledgeId)
                .map(profileKnowledge -> {
                    profilesKnowledgeRepository.delete(profileKnowledge);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a profile")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Profile successfully deleted"),
        @ApiResponse(responseCode = "404", description = "Profile not found")
    })
    @DeleteMapping("/{profileId}")
    public ResponseEntity<Void> deleteProfile(
            @Parameter(description = "ID of the profile to delete") @PathVariable("profileId") Short profileId) {
            var profile = profileRepository.findById(profileId).orElse(null);
            if (profile == null) {
                return ResponseEntity.notFound().build();
            }
            profileRepository.delete(profile);
            return ResponseEntity.noContent().build();

    }
}