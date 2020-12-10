package fr.sleafy.resources;

import com.codahale.metrics.annotation.Timed;
import fr.sleafy.api.Humidity;
import fr.sleafy.controllers.HumidityController;
import fr.sleafy.dao.ESPDao;
import fr.sleafy.dao.HumidityDao;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/humidity")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(value="/humidity")
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
public class HumidityResource {

    private final HumidityController humidityController;

    public HumidityResource(ESPDao espDao, HumidityDao humidityDao) {
        this.humidityController = new HumidityController(espDao, humidityDao);
    }

    @POST
    @Path("/reading")
    @Timed
    @ApiOperation(value = "Declare a new humidity reading", authorizations = @Authorization(value = "espAuth"))
    public void declareHumidityReading(Humidity reading) {
        humidityController.storeHumidityReadingFromUUID(reading);
    }
}