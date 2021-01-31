package xyz.litterboys.esblog.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "pic")
@PropertySource(value = "classpath:config.properties")
public class ConfigKey {
    private String serviceType;

    private String giteeAccessToken;
    private String giteeOwner;
    private String giteeRepo;

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getGiteeAccessToken() {
        return giteeAccessToken;
    }

    public void setGiteeAccessToken(String giteeAccessToken) {
        this.giteeAccessToken = giteeAccessToken;
    }

    public String getGiteeOwner() {
        return giteeOwner;
    }

    public void setGiteeOwner(String giteeOwner) {
        this.giteeOwner = giteeOwner;
    }

    public String getGiteeRepo() {
        return giteeRepo;
    }

    public void setGiteeRepo(String giteeRepo) {
        this.giteeRepo = giteeRepo;
    }
}
