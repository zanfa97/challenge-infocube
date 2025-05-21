package it.infocube.management.user;

import it.infocube.management.knowledge.Knowledge;
import it.infocube.management.knowledge.KnowledgeRepository;
import it.infocube.management.users_knowledge.UsersKnowledge;
import it.infocube.management.users_knowledge.UsersKnowledgeId;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "User Management", description = "API endpoints for managing users")
@AllArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UsersKnowledgeRepository usersKnowledgeRepository;
    private final KnowledgeRepository knowledgeRepository;

    @Operation(
        summary = "Get all users",
        description = "Retrieve a paginated list of all users in the system",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved users",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
            )
        }
    )
    @GetMapping
    public List<UserDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Operation(
        summary = "Get user by ID",
        description = "Retrieve a user's information by their unique identifier",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved user",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "User not found"
            )
        }
    )
    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUser(@Parameter(description = "User ID") @PathVariable Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @Operation(
        summary = "Add knowledge to user",
        description = "Associate a knowledge item with a user",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Successfully added knowledge to user",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "User or knowledge not found"
            )
        }
    )
    @PostMapping("{id}/knowledge")
    public ResponseEntity<UserDto> addKnowledgeToUser(
            @Parameter(description = "User ID") @PathVariable("id") Long userId,
            @RequestBody AddKnowledgeToUserRequest request) {
        var user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        var newUsersKnowldege = new UsersKnowledge();
        var usersKnowldegeId = new UsersKnowledgeId();

        usersKnowldegeId.setUserId(userId);

        var knowledge = knowledgeRepository.findById(request.getKnowledgeId()).orElse(null);
        if (knowledge == null) {
            return ResponseEntity.badRequest().build();
        }

        usersKnowldegeId.setKnowledgeId(request.getKnowledgeId());

        newUsersKnowldege.setId(usersKnowldegeId);
        newUsersKnowldege.setLevel(request.getLevel().toString());
        newUsersKnowldege.setKnowledge(knowledge);
        newUsersKnowldege.setUser(user);


        usersKnowledgeRepository.save(newUsersKnowldege);

        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toDto(user));
    }

    @Operation(
        summary = "Remove knowledge from user",
        description = "Remove a knowledge association from a user",
        responses = {
            @ApiResponse(
                responseCode = "204",
                description = "Successfully removed knowledge"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "User or knowledge association not found"
            )
        }
    )
    @DeleteMapping("/{userId}/knowledge/{knowledgeId}")
    public ResponseEntity<Void> removeKnowledgeFromUser(
            @Parameter(description = "User ID") @PathVariable("userId") Long userId,
            @Parameter(description = "Knowledge ID") @PathVariable("knowledgeId") Long knowledgeId) {

        if (userId == null || knowledgeId == null) {
            return ResponseEntity.badRequest().build();
        }

        UsersKnowledgeId usersKnowledgeId = new UsersKnowledgeId();
        usersKnowledgeId.setUserId(userId);
        usersKnowledgeId.setKnowledgeId(knowledgeId.shortValue());

        if (!usersKnowledgeRepository.existsById(usersKnowledgeId)) {
            return ResponseEntity.notFound().build();
        }

        usersKnowledgeRepository.deleteById(usersKnowledgeId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Update user information",
        description = "Update a user's personal information",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully updated user",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "User not found"
            )
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @Parameter(description = "User ID") @PathVariable("id") Long id,
            @RequestBody UpdateUserRequest request) {
        var existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }

        existingUser.setFirstName(request.getFirstName());
        existingUser.setLastName(request.getLastName());

        var savedUser = userRepository.save(existingUser);
        return ResponseEntity.ok(userMapper.toDto(savedUser));
    }

    @Operation(
        summary = "Delete user",
        description = "Remove a user from the system",
        responses = {
            @ApiResponse(
                responseCode = "204",
                description = "Successfully deleted user"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "User not found"
            )
        }
    )
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@Parameter(description = "User ID") @PathVariable("userId") Long userId) {
        var user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }

}
