package fr.sleafy;

import fr.sleafy.dao.ESPDao;
import fr.sleafy.dao.HumidityDao;
import fr.sleafy.resources.ESPResource;
import fr.sleafy.resources.HumidityResource;
import fr.sleafy.resources.MaintenerResource;
import fr.sleafy.services.DBService;
import fr.sleafy.services.SecurityService;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

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
        DBService dbService = new DBService(configuration.getDatabase());
        ESPDao espDao = new ESPDao(dbService);
        HumidityDao humidityDao = new HumidityDao(dbService);

        SecurityService securityService = new SecurityService(configuration.getKeycloakConfiguration(), espDao, environment);

        final MaintenerResource maintenerResource = new MaintenerResource(configuration.getMaintenedBy());
        final ESPResource espResource = new ESPResource(espDao);
        final HumidityResource humidityResource = new HumidityResource(espDao, humidityDao);

        environment.jersey().register(maintenerResource);
        environment.jersey().register(humidityResource);
        environment.jersey().register(espResource);
    }
}
