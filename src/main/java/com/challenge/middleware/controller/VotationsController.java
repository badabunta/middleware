package com.challenge.middleware.controller;

import com.challenge.middleware.dto.VoteDTO;
import com.challenge.middleware.service.VoteService;
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
@Tag(name = "votations")
@RequestMapping("/sessions")
public class VotationsController {

    private VoteService voteService;

    public VotationsController(VoteService voteService) {
        this.voteService = voteService;
    }

    @Operation(summary = " List of votes emmited")
    @ApiResponse(responseCode = "200", description = "List of votes", content = {@Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = VoteDTO.class))
    )})
    @GetMapping(path = "/{idSession}/votes")
    public ResponseEntity<List<VoteDTO>> getVotes(@Parameter(description = "session id", required = true) @PathVariable(value = "idSession") String idSession) {
        return new ResponseEntity<>(voteService.getAllVotes(idSession), HttpStatus.OK);

    }

    @Operation(summary = "Emit a vote")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created JSON object", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = VoteDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) })
    })
    @PostMapping(path = "/{idSession}/votes")
    public ResponseEntity<VoteDTO> emitVote(@Parameter(description = "session id", required = true) @PathVariable(value = "idSession") String idSession,
                                            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "vote data", required = true) @RequestBody VoteDTO vote) {
        return new ResponseEntity<>(voteService.emitVote(idSession, vote), HttpStatus.OK);
    }

}
