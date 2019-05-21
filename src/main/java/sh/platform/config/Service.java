package sh.platform.config;

import java.util.Map;

public interface Service {

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
