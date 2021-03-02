package fr.sleafy.resources;

import com.codahale.metrics.annotation.Timed;
import fr.sleafy.api.Temperature;
import fr.sleafy.controllers.TemperatureController;
import fr.sleafy.dao.ESPDao;
import fr.sleafy.dao.TemperatureDao;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/temperature")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(value="/temperature")
@SwaggerDefinition(securityDefinition = @SecurityDefinition(
        oAuth2Definitions = @OAuth2Definition(
                flow = OAuth2Definition.Flow.IMPLICIT,
                authorizationUrl = "https://www.sleafy.fr/keycloak/auth/realms/Sleafy/protocol/openid-connect/auth",
                tokenUrl = "https://www.sleafy.fr/keycloak/auth/realms/Sleafy/protocol/openid-connect/token",
                key = "oauth2"
        ),
        basicAuthDefinitions = @BasicAuthDefinition(key = "espAuth")
        )
    )
public class TemperatureResource {

    private final TemperatureController temperatureController;

    public TemperatureResource(ESPDao espDao, TemperatureDao temperatureDao) {
        this.temperatureController = new TemperatureController(espDao, temperatureDao);
    }

    @POST
    @Path("/reading")
    @Timed
    @ApiOperation(value = "Declare a new temperature reading", authorizations = @Authorization(value = "espAuth"))
    public Response declareTemperatureReading(Temperature reading) {
        Temperature inserted = temperatureController.storeTemperatureReadingFromUUID(reading);
        if (inserted.getId() == 0) {
            return Response.status(500).build();
        } else {
            return Response.status(201).build();
        }
    }

    @GET
    @Path("/readings/{esp}")
    @Timed
    @ApiOperation(value = "Get the temperature value", authorizations = @Authorization(value = "oauth2"))
    public Response getTemperatureValue(@QueryParam("size") Integer size, @PathParam("esp") Integer esp){
        List<Temperature> temperatures = temperatureController.getTemperatureValues(size, esp);
        if(temperatures == null){
            return Response.status(500).build();
        }
        if(temperatures.isEmpty()){
            return Response.noContent().build();
        }
        else{
            return Response.ok().entity(temperatures).build();
        }
    }
}
