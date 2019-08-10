package bds.resource;

import bds.security.UserProfile;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

// TODO change to JPA repo

@Component
public class UserRepository {
    Map<String, UserProfile> repository;
    public UserRepository() {
        this.repository = new ConcurrentHashMap<>();
    }

    public Optional<UserProfile> getUser(String userId) {
        return Optional.ofNullable(repository.get(userId));
    }

    public void addUser(UserProfile userProfile) {
        repository.put(userProfile.getUsername(), userProfile);
    }
}
