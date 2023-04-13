package com.challenge.middleware.controller;

import com.challenge.middleware.dto.MemberDTO;
import com.challenge.middleware.dto.UserStoryDTO;
import com.challenge.middleware.service.UserStoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "user stories")
@RequestMapping("/sessions")
public class UserStoriesController {

    private UserStoryService userStoryService;

    public UserStoriesController(UserStoryService userStoryService) {
        this.userStoryService = userStoryService;
    }

    @Operation(summary = "List of user stories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The Resource", content = {@Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = UserStoryDTO.class))
            )}),            @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) })
    })
    @GetMapping(path = "/{idSession}/stories")
    public ResponseEntity<List<UserStoryDTO>> getUserStoriesFromSession(@Parameter(description = "session id", required = true) @PathVariable(value = "idSession") String idSession) {
        return new ResponseEntity<>(userStoryService.getUserStoriesFromSession(idSession), HttpStatus.OK);
    }

    @Operation(summary = "Creation of a user story")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created JSON object", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserStoryDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) })
    })
    @PostMapping(path = "/{idSession}/stories")
    public ResponseEntity<UserStoryDTO> createUserStory(@Parameter(description = "session id", required = true)@PathVariable(value = "idSession") String idSession,
                                                        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "user story id", required = true) @RequestBody UserStoryDTO userStoryDto) {
        return new ResponseEntity<>(userStoryService.createUserStory(idSession, userStoryDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Update story information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created JSON object", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserStoryDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) })
    })
    @PutMapping(path = "/{idSession}/stories/{idUserStory}")
    public ResponseEntity<UserStoryDTO> updateUserStory(@Parameter(description = "session id", required = true) @PathVariable(value = "idSession") String idSession,
                                                        @Parameter(description = "user story id", required = true) @PathVariable(value = "idUserStory") String idUserStory,
                                                        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "user story data", required = true) @RequestBody UserStoryDTO userStoryDto) {
        return new ResponseEntity<>(userStoryService.updateUserStory(idSession, idUserStory, userStoryDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete user story")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created JSON object", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserStoryDTO.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) })
    })
    @DeleteMapping(path = "/{idSession}/stories/{idUserStory}")
    public ResponseEntity<UserStoryDTO> deleteUserStory(@Parameter(description = "session id", required = true)@PathVariable(value = "idSession") String idSession,
                                                        @Parameter(description = "user story id", required = true) @PathVariable(value = "idUserStory") String idUserStory,
                                                        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "member data", required = true) @RequestBody MemberDTO memberDto) {
        return new ResponseEntity<>(userStoryService.deleteUserStory(idSession, idUserStory, memberDto), HttpStatus.OK);
    }


}
