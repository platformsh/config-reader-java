package sh.platform.config;

import java.util.Map;

public interface Service {

    String getName();

    String getIp();

    String getType();

    int getPort();

    String getHost();

    String getCluster();

    Map<String, String> toMap();

}
