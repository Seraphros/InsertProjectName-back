package fr.sleafy.services;

import de.ahus1.keycloak.dropwizard.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.jose4j.keys.RsaKeyUtil;
import org.jose4j.lang.JoseException;

import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

@Slf4j
public class UserService {

    String publicKeyPEM;

    public UserService(String publickeyPEM) {
        this.publicKeyPEM = publickeyPEM;
    }

    public String retrieveUserNameFromHeader(String authorizationHeader) {

        try {
            Jws<Claims> jws;
            RsaKeyUtil rsaKeyUtil = new RsaKeyUtil();
            PublicKey publicKey = rsaKeyUtil.fromPemEncoded(publicKeyPEM);


            jws = Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(authorizationHeader.split(" ")[1]);
            return jws.getBody().get("preferred_username").toString();
        }
        catch (JwtException | InvalidKeySpecException | JoseException ex) {
            // we *cannot* use the JWT as intended by its creator
            log.error("error decoding jwt : " + ex);
            return "";
        }
    }
}
