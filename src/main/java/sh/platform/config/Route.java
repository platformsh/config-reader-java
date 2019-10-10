package sh.platform.config;

import com.google.gson.annotations.SerializedName;

import java.util.Optional;

public class Route {

    private String id;

    private String upstream;

    @SerializedName("original_url")
    private String originalUrl;

    private boolean primary;

    private String type;

    private String to;

    public String getId() {
        return id;
    }

    public Optional<String> getUpstream() {
        return Optional.ofNullable(upstream);
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public boolean isPrimary() {
        return primary;
    }

    public String getType() {
        return type;
    }

    public String getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id='" + id + '\'' +
                ", upstream='" + upstream + '\'' +
                ", originalUrl='" + originalUrl + '\'' +
                ", primary=" + primary +
                ", type='" + type + '\'' +
                ", to='" + to + '\'' +
                '}';
    }
}
