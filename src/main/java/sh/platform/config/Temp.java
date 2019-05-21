package sh.platform.config;

import java.util.Map;
import java.util.Optional;

public class Temp {

    public static void main(String[] args) {
        Config config = Config.get();
        CredentialFormatter<CustomCredentials> formatter = CustomCredentials::new;
        Optional<CustomCredentials> credentials = config.getCredential("as", formatter);
    }

    static class CustomCredentials {

        public CustomCredentials(Map<String, Object> stringObjectMap) {
        }
    }
}
