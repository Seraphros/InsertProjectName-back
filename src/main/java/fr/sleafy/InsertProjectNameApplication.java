package fr.sleafy;

import fr.sleafy.dao.ESPDao;
import fr.sleafy.resources.ESPResource;
import fr.sleafy.resources.MaintenerResource;
import fr.sleafy.services.DBService;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class InsertProjectNameApplication extends Application<InsertProjetNameConfiguration> {

    public static void main(String[] args) throws Exception {
        new InsertProjectNameApplication().run(args);
    }

    @Override
    public String getName() {
        return "InsertProjectNameApplication";
    }

    @Override
    public void initialize(Bootstrap<InsertProjetNameConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<InsertProjetNameConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(InsertProjetNameConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
    }

    @Override
    public void run(InsertProjetNameConfiguration configuration,
                    Environment environment) {

        DBService dbService = new DBService(configuration.getDatabase());
        ESPDao espDao = new ESPDao(dbService);


        final MaintenerResource maintenerResource = new MaintenerResource(configuration.getMaintenedBy());
        final ESPResource espResource = new ESPResource(espDao);

        environment.jersey().register(maintenerResource);
        environment.jersey().register(espResource);
    }
}
