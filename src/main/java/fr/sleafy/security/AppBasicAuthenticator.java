package fr.sleafy.security;

import fr.sleafy.api.ESP;
import fr.sleafy.api.utils.IDSecretKey;
import fr.sleafy.dao.ESPDao;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppBasicAuthenticator implements Authenticator<BasicCredentials, User> {

    private final ESPDao espDao;

    public AppBasicAuthenticator(ESPDao espDao) {
        super();
        this.espDao = espDao;
    }


    private static final Map<String, Set<String>> VALID_USERS = ImmutableMap.of(
            "guest", ImmutableSet.of(),
            "user", ImmutableSet.of("USER"),
            "admin", ImmutableSet.of("ADMIN", "USER")
    );

    @Override
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
        ESP espFound = espDao.getESPfromUUID(credentials.getUsername());
        String encodedKey = IDSecretKey.get_SHA_512_SecurePassword(credentials.getPassword());
        if (encodedKey.equals(espFound.getSecretKey())) {
            return Optional.of(new User(credentials.getUsername(), ImmutableSet.of("USER")));
        } else {
            return Optional.empty();
        }
    }
}
