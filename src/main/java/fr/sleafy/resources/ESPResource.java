package fr.sleafy.resources;

import com.codahale.metrics.annotation.Timed;
import de.ahus1.keycloak.dropwizard.User;
import fr.sleafy.api.ESP;
import fr.sleafy.api.utils.IDSecretKey;
import fr.sleafy.controllers.ESPController;
import fr.sleafy.dao.ESPDao;
import io.dropwizard.auth.Auth;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/esp")
@Produces(MediaType.APPLICATION_JSON)
@Api(value="/esp")
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
public class ESPResource {

    private final ESPController espController;

    public ESPResource(ESPDao espDao) {
        this.espController = new ESPController(espDao);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed
    @ApiOperation(value = "Declare a new ESP", authorizations = @Authorization(value = "oauth2"))
    public IDSecretKey declareESP(@ApiParam(hidden = true) @Auth User user, ESP esp) {
        return espController.createNewESP(user, esp);
    }

    @GET
    @Timed
    @ApiOperation(value = "Get all ESPs according to the user", authorizations = @Authorization(value = "oauth2"))
    public List<ESP> getUsersESP(@ApiParam(hidden = true) @Auth User user, @QueryParam("userID") int userID) {
        return espController.getUsersESP(userID);
    }

    @GET
    @Path("/informations/{uuid}")
    @Timed
    @ApiOperation(value = "Retrieve ESP according to its UUID", authorizations = @Authorization(value = "espAuth"))
    public ESP getESPfromUUID(@PathParam("uuid") String uuid) {
        return espController.getESPfromUUID(uuid);
    }

    @POST
    @Path("/{uuid}/name")
    @Timed
    @ApiOperation(value = "Change ESP Name", authorizations = @Authorization(value = "oauth2"))
    public ESP getESPfromUUID(@PathParam("uuid") String uuid, @QueryParam("name") String name) {
        return espController.changeESPName(uuid, name);
    }
}
