package fr.sleafy;

import fr.sleafy.dao.ESPDao;
import fr.sleafy.resources.ESPResource;
import fr.sleafy.resources.MaintenerResource;
import fr.sleafy.security.AppAuthorizer;
import fr.sleafy.security.AppBasicAuthenticator;
import fr.sleafy.security.User;
import fr.sleafy.services.DBService;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

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


        final MaintenerResource maintenerResource = new MaintenerResource(configuration.getMaintenedBy());
        final ESPResource espResource = new ESPResource(espDao);

        environment.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
                .setAuthenticator(new AppBasicAuthenticator(espDao))
                .setAuthorizer(new AppAuthorizer())
                .setRealm("BASIC-AUTH-REALM")
                .buildAuthFilter()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));


        environment.jersey().register(maintenerResource);
        environment.jersey().register(espResource);
    }
}
