package xyz.litterboys.esblog.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.litterboys.esblog.exception.NormalException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtils {

    private static final long EXPIRE_TIME = 1000*60*60*24*3;
//    private static final long EXPIRE_TIME = 1000*60;
    private static final String TOKEN_SECRET = "ZSdgShjdhFGFSCwfsjGxDWDd@";

    private static final Logger logger = LoggerFactory.getLogger(TokenUtils.class);

    public static String getToken(String username){
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);

        Map<String, Object> header = new HashMap<>(2);
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        String token = JWT.create().withHeader(header).withClaim("username", username).withExpiresAt(date).sign(algorithm);
        logger.info("token={} be created", token);
        return token;
    }

    public static String verify(String token){
        logger.info("token={} to be verified", token);
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT jwt = jwtVerifier.verify(token);
            return jwt.getClaims().get("username").asString();
        }catch (Exception e){
            logger.info("token={} is error", token);
            throw new NormalException("登录失效");
        }
    }
}
