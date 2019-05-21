package sh.platform.config;

import java.util.Map;

//make it concrete
public interface Credential {

    String getName();

    String getScheme();

    String getIp();

    String getType();

    int getPort();

    String getHost();

    String getCluster();

    String getRelationship();

    Map<String, Object> toMap();
}
