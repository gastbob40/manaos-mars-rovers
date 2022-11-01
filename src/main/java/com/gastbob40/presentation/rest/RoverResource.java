package com.gastbob40.presentation.rest;

import com.gastbob40.domain.service.RoverService;

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
    public String simulateRovers(String request) {
        return roverService.simulate(request);
    }
}
