package fr.sleafy;

import fr.sleafy.dao.ESPDao;
import fr.sleafy.dao.HumidityDao;
import fr.sleafy.dao.TemperatureDao;
import fr.sleafy.resources.ESPResource;
import fr.sleafy.resources.HumidityResource;
import fr.sleafy.resources.MaintenerResource;
import fr.sleafy.resources.TemperatureResource;
import fr.sleafy.services.DBService;
import fr.sleafy.services.SecurityService;
import fr.sleafy.services.UserService;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class SleafyBackApplication extends Application<SleafyBackConfiguration> {

    public static void main(String[] args) throws Exception {
        new SleafyBackApplication().run(args);
    }

    @Override
    public String getName() {
        return "SleafyBackApplication";
    }

    @Override
    public void initialize(Bootstrap<SleafyBackConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<SleafyBackConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(SleafyBackConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
    }

    @Override
    public void run(SleafyBackConfiguration configuration,
                    Environment environment) {

        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin,Authorization");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        DBService dbService = new DBService(configuration.getDatabase());
        ESPDao espDao = new ESPDao(dbService);
        HumidityDao humidityDao = new HumidityDao(dbService);
        TemperatureDao temperatureDao = new TemperatureDao(dbService);
        UserService userService = new UserService(configuration.getKeycloakRSAPublicKey());

        SecurityService securityService = new SecurityService(configuration.getKeycloakConfiguration(), espDao, environment);

        final MaintenerResource maintenerResource = new MaintenerResource(configuration.getMaintenedBy());
        final ESPResource espResource = new ESPResource(espDao, userService);
        final HumidityResource humidityResource = new HumidityResource(espDao, humidityDao);
        final TemperatureResource temperatureResource = new TemperatureResource(espDao, temperatureDao);

        environment.jersey().register(maintenerResource);
        environment.jersey().register(humidityResource);
        environment.jersey().register(temperatureResource);
        environment.jersey().register(espResource);


    }
}
