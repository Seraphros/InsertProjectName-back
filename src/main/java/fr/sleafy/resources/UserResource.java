package fr.sleafy.resources;

import com.codahale.metrics.annotation.Timed;
import fr.sleafy.api.ESP;
import fr.sleafy.api.UserInformation;
import fr.sleafy.controllers.UserController;
import fr.sleafy.dao.UserDao;
import fr.sleafy.services.UserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Slf4j
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "/user")
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
public class UserResource {

    private UserService userService;
    private UserController userController;


    public UserResource(UserService userService, UserDao userDao) {
        this.userService = userService;
        this.userController = new UserController(userDao);
    }

    @GET
    @Timed
    @ApiOperation(value = "Get user Information", authorizations = @Authorization(value = "oauth2"))
    public Response getUserInformations(@ApiParam(hidden = true) @HeaderParam("Authorization") String authString) {
        String user = this.userService.retrieveUserNameFromHeader(authString);
        if(user != null){
            UserInformation userInformation = userController.getUserInformation(user);
            if(userInformation != null){
                return Response.ok(userInformation).build();
            }
            return Response.status(404).build();
        }
        return Response.status(401).build();
    }

    @POST
    @Timed
    @ApiOperation(value = "Save new city fr user", authorizations = @Authorization(value = "oauth2"))
    public Response saveUserCity(@ApiParam(hidden = true) @HeaderParam("Authorization") String authString, @QueryParam("city") String city) {
        String user = this.userService.retrieveUserNameFromHeader(authString);
        if(user != null){
            UserInformation userInformation = userController.saveUserCity(user, city);
            if(userInformation != null){
                return Response.status(201).entity(userInformation).build();
            }
            return Response.status(404).build();
        }
        return Response.status(401).build();
    }

    @PUT
    @Timed
    @ApiOperation(value = "Update city of user", authorizations = @Authorization(value = "oauth2"))
    public Response updateUserCity(@ApiParam(hidden = true) @HeaderParam("Authorization") String authString, @QueryParam("city") String city) {
        String user = this.userService.retrieveUserNameFromHeader(authString);
        if(user != null){
            UserInformation userInformation = userController.saveUserCity(user, city);
            if(userInformation != null){
                return Response.status(201).entity(userInformation).build();
            }
            return Response.status(404).build();
        }
        return Response.status(401).build();
    }
}
