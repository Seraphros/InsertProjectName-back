package com.insertProjetName.resources;

import com.codahale.metrics.annotation.Timed;
import com.insertProjetName.api.Maintener;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/maintener")
@Produces(MediaType.APPLICATION_JSON)
public class MaintenerResource {

    private final Maintener maintener;

    public MaintenerResource(Maintener maintener) {
        this.maintener = maintener;
    }

    @GET
    @Timed
    public Maintener retrieveMaintener() {
        return this.maintener;
    }
}
