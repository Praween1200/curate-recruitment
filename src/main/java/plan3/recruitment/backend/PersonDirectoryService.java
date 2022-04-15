package plan3.recruitment.backend;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import plan3.recruitment.backend.dao.PersonStorageImpl;
import plan3.recruitment.backend.model.PersonStorage;
import plan3.recruitment.backend.resources.PersonResource;

public class PersonDirectoryService extends Application<Configuration> {

    @Override
    public void initialize(final Bootstrap<Configuration> bootstrap) {}

    public static void main(final String[] args) throws Exception {
        new PersonDirectoryService().run(args);
    }

    @Override
    public void run(final Configuration configuration, final Environment environment) throws Exception {
    	PersonStorage storage = new PersonStorageImpl();
    	PersonResource personResource = new PersonResource(storage);
    	environment.jersey().register(personResource);
    }
}
