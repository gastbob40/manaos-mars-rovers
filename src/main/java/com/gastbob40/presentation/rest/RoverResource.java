package com.gastbob40.presentation.rest;

import com.gastbob40.domain.service.RoverService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/rovers")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)

public class RoverResource {
    @Inject RoverService roverService;

    @POST @Path("/simulate")
    @Operation(
            summary = "Simulate a rover movement based on input commands",
            description = "Apply commands on multiple rovers to simulate their movements",
            operationId = "rover-simulate"
    )
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Rover simulation successful"),
            @APIResponse(responseCode = "400", description = "Invalid input"),
    })
    public String simulateRovers(String request) {
        return roverService.simulate(request);
    }
}
