package fr.sleafy.resources;

import com.codahale.metrics.annotation.Timed;
import de.ahus1.keycloak.dropwizard.User;
import fr.sleafy.api.ESP;
import fr.sleafy.api.utils.IDSecretKey;
import fr.sleafy.controllers.ESPController;
import fr.sleafy.dao.ESPDao;
import fr.sleafy.services.UserService;
import io.dropwizard.auth.Auth;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Slf4j
@Path("/esp")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "/esp")
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
    private final UserService userService;

    public ESPResource(ESPDao espDao, UserService userService) {
        this.espController = new ESPController(espDao);
        this.userService = userService;
    }

    @POST
    @Timed
    @ApiOperation(value = "Declare a new ESP", authorizations = @Authorization(value = "oauth2"))
    public IDSecretKey declareESP(ESP esp) {
        return espController.createNewESP(0);
    }

    @GET
    @Timed
    @ApiOperation(value = "Get all ESPs according to the user", authorizations = @Authorization(value = "oauth2"))
    public List<ESP> getUsersESP(@ApiParam(hidden = true) @HeaderParam("Authorization") String authString, @QueryParam("userID") int userID) {
        log.info(this.userService.retrieveUserNameFromHeader(authString));
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
