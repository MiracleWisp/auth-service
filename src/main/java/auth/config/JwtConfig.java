package auth.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class JwtConfig {
    @Value("${security.jwt.uri:/login/**}")
    private String Uri;

    @Value("${security.jwt.header:Authorization}")
    private String header;

    @Value("${security.jwt.prefix:Bearer }")
    private String prefix;

    @Value("${security.jwt.expiration:#{24*60*60}}")
    private int expiration;

    @Value("${security.jwt.secret:secret}")
    private String secret;

    @Value("${security.jwt.keypass}")
    private String keypass;

    @Value("${security.jwt.jks-file:jwt.jks}")
    private String jksFile;

    @Value("${security.jwt.key-alias}")
    private String keyAlias;

}