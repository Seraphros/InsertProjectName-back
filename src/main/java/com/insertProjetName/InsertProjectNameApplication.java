package com.insertProjetName;

import com.insertProjetName.resources.MaintenerResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

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
        // nothing to do yet
    }

    @Override
    public void run(InsertProjetNameConfiguration configuration,
                    Environment environment) {
        final MaintenerResource maintenerResource = new MaintenerResource(configuration.getMaintenedBy());

        environment.jersey().register(maintenerResource);
    }
}
