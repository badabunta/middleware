package com.challenge.middleware.controller;

import com.challenge.middleware.dto.MemberDTO;
import com.challenge.middleware.dto.PokerPlanningSessionDTO;
import com.challenge.middleware.service.MemberService;
import com.challenge.middleware.service.PokerPlanningSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
@Tag(name = "session management")
@RequestMapping("/sessions")
public class SessionManagementController {

    private PokerPlanningSessionService sessionService;
    private MemberService memberService;

    public SessionManagementController(PokerPlanningSessionService sessionService, MemberService memberService) {
        this.sessionService = sessionService;
        this.memberService = memberService;
    }

    @Operation(summary = " List of existing poker planning sessions")
    @ApiResponse(responseCode = "200", description = " A JSON array of session", content = {
            @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = PokerPlanningSessionDTO.class))
            )
        }
    )
    @GetMapping
    public ResponseEntity<List<PokerPlanningSessionDTO>> getSessions() {
        return new ResponseEntity<>(sessionService.getAllSessions(), HttpStatus.OK);
    }

    @Operation(summary = "Session information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The Resource", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PokerPlanningSessionDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) })
    })
    @GetMapping(path = "/{idSession}")
    public ResponseEntity<PokerPlanningSessionDTO> getSession(@Parameter(description = "id of session", required = true) @PathVariable(value = "idSession") String idSession) {
        return new ResponseEntity<>(sessionService.getSessionBySessionId(idSession), HttpStatus.OK);
    }

    @Operation(summary = "List of Members in the session")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of Members join in", content = {@Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = MemberDTO.class)),
                    examples = {
                            @ExampleObject()
                    }
            )}),
            @ApiResponse(responseCode = "204", description = "No content", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class)) }),
            @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) })
    })
    @GetMapping(path = "/{idSession}/members")
    public ResponseEntity<List<MemberDTO>> getSessionMembers(@Parameter(description = "id of session", required = true) @PathVariable(value = "idSession") String idSession) {
        List<MemberDTO> members = memberService.getSessionMembers(idSession);
        if (members.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(members, HttpStatus.OK);
        }
    }

    @Operation(summary = "Logout a Member")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Remain members", content = {@Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = MemberDTO.class))
            )}),
            @ApiResponse(responseCode = "204", description = "No content", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class)) }),
    })
    @GetMapping(path = "/{idSession}/member/{idMember}")
    public ResponseEntity<List<MemberDTO>> logoutMember(@Parameter(description = "id of session", required = true) @PathVariable(value = "idSession") String idSession,
                                                        @Parameter(description = "id of member", required = true) @PathVariable(value = "idMember") String idMember) {
        List<MemberDTO> memberDto =  memberService.logoutMember(idSession, idMember);
        if (memberDto.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(memberDto, HttpStatus.OK);
        }
    }

    @Operation(summary = "Creation of a new session")
    @ApiResponse(responseCode = "201", description = "Created JSON object", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PokerPlanningSessionDTO.class)) })
    @PostMapping
    public ResponseEntity<PokerPlanningSessionDTO> createSession(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Session data", required = true) @RequestBody PokerPlanningSessionDTO sessionDTO) {
        return new ResponseEntity<>(sessionService.createSession(sessionDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Join in the session")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created JSON object", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MemberDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) })
    })
    @PostMapping(path = "/{idSession}/members")
    public ResponseEntity<MemberDTO> joinSession(@Parameter(description = "Session id", required = true) @PathVariable(value = "idSession") String idSession,
                                                 @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Member data", required = true) @RequestBody MemberDTO member) {
        return new ResponseEntity<>(memberService.joinSession(idSession, member), HttpStatus.CREATED);
    }

    @Operation(summary = "Destroy session")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Session destroyed info", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PokerPlanningSessionDTO.class)) }),
            @ApiResponse(responseCode = "404", description = ": Not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) })
    })
    @DeleteMapping(path = "/{idSession}")
    public ResponseEntity<PokerPlanningSessionDTO> destroySession(@Parameter(description = "session id", required = true) @PathVariable(value = "idSession") String idSession) {
        return new ResponseEntity<>(sessionService.destroySession(idSession), HttpStatus.OK);
    }
}
