package sh.platform.config;

import java.util.List;
import java.util.Map;

public interface Application {


    /**
     * @return The name of the application, as configured in the .platform.app.yaml file.
     */
    String getName();

    /**
     * @return The ID of the project.
     */
    String getId();

    /**
     * @return object which keys are variables names and values are variable values from the the: PLATFORM_VARIABLES
     * OS environment
     */
    Map<String, String> getVariables();

    /**
     * @return the available services
     */
    List<Service> getServices();

}
