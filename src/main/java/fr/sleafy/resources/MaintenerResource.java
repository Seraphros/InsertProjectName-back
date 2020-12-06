package fr.sleafy.resources;

import com.codahale.metrics.annotation.Timed;
import fr.sleafy.api.Maintener;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/maintener")
@Produces(MediaType.APPLICATION_JSON)
@Api(value="/maintener")
public class MaintenerResource {

    private final Maintener maintener;

    public MaintenerResource(Maintener maintener) {
        this.maintener = maintener;
    }

    @GET
    @Timed
    @ApiOperation(value="Return the project maintener")
    public Maintener retrieveMaintener() {
        return this.maintener;
    }
}
