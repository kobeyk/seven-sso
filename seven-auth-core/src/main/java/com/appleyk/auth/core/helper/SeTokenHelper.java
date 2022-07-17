package com.appleyk.auth.core.helper;
import com.appleyk.auth.common.helper.SeLoggerHelper;
import com.appleyk.auth.common.util.SeGeneralUtils;
import com.appleyk.auth.core.model.SeAuthUser;
import com.appleyk.auth.core.model.session.SeSsoInfo;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *     用户令牌相关操作辅助类
 * </p>
 *
 * JSON Web Token由三部分组成，它们之间用圆点(.)连接。这三部分分别是：
 *
 * Header
 * Payload
 * Signature
 *
 * 因此，一个典型的JWT看起来是这个样子的：aaaaaa.bbbbbb.ccccccc
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/23-9:40
 */
public class SeTokenHelper {

    private static final String TYPE = "JWT";
    /**HS256：使用同一个「secret_key」进行签名与验证（对称加密），一旦 secret_key 泄漏，就毫无安全性可言了*/
    private static final String ALGORITHM = "HS256";
    private static final String ISSUER = "https://github.com/kobeyk";

    public static final String USER_ID = "uid";
    public static final String APP_ID = "app";
    public static final String USER_NAME = "userName";
    private static final String SPLIT = "_";

    /**秘钥*/
    public static final String SECRET_KEY = "SEVEN-SSO-SERVER";

    /**
     * 基于服务端sso登录信息创建对应的服务端用户令牌
     */
    public static SeSsoInfo createToken(SeSsoInfo ssoInfo) throws UnsupportedEncodingException {
        Long systemTime = System.currentTimeMillis();
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        /**JWT签名头部*/
        Map<String, Object> header = new HashMap<>(2);
        header.put("typ", TYPE);
        header.put("alg", ALGORITHM);
        Long appId = ssoInfo.getAppId();
        String localToken = ssoInfo.getLocalToken();
        String clientToken = ssoInfo.getClientToken();
        long userId = ssoInfo.fetchUserId();
        String userName = ssoInfo.fetchUserName();
        if (SeGeneralUtils.isEmpty(appId) && SeGeneralUtils.isEmpty(localToken)){
            String token = JWT.create()
                    .withClaim(USER_ID,userId)
                    .withClaim(USER_NAME,userName)
                    .withHeader(header)
                    .withIssuer(ISSUER)
                    .withIssuedAt(new Date(systemTime))
                    .sign(algorithm);
            ssoInfo.setLocalToken(token);
        }else if (SeGeneralUtils.isNotEmpty(appId) && SeGeneralUtils.isEmpty(clientToken)){
            String token = JWT.create()
                    .withClaim(USER_ID,userId)
                    .withClaim(USER_NAME,userName)
                    .withClaim(APP_ID, appId)
                    .withHeader(header)
                    .withIssuer(ISSUER)
                    .withIssuedAt(new Date(systemTime))
                    .sign(algorithm);
            ssoInfo.setClientToken(token);
        }
        ssoInfo.setLastAccessTime(new Date());
        return ssoInfo;
    }

    /**
     * 首先验证令牌是否合法，如果合法，反解token中的uid，如果返回0，显示不ok啊！
     * @param token
     * @return
     * @throws UnsupportedEncodingException
     */
    public static long verifyToken(String token) throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build();
        DecodedJWT jwt = verifier.verify(token);
        Claim claim = jwt.getClaim(USER_ID);
        if (!claim.isNull()) {
            return claim.asLong();
        }
        return 0;
    }

    /**
     * 根据标签反解令牌信息
     */
    public static long decodeToken(String token, String name) {
        if (SeGeneralUtils.isNotEmpty(token)) {
            try {
                DecodedJWT jwt = JWT.decode(token);
                Claim claim = jwt.getClaim(name);
                if (!claim.isNull()) {
                    return claim.asLong();
                }
            } catch (Exception e) {
                SeLoggerHelper.error(e.getMessage(),e);
            }
        }
        return 0L;
    }

    /**
     * 根据标签反解令牌信息
     */
    public static String decodePayLoadToken(String token, String name) {
        if (SeGeneralUtils.isNotEmpty(token)) {
            try {
                DecodedJWT jwt = JWT.decode(token);
                Claim claim = jwt.getClaim(name);
                if (!claim.isNull()) {
                    return claim.asString();
                }
            } catch (Exception e) {
                SeLoggerHelper.error(e.getMessage(),e);
            }
        }
        return "";
    }

    public static void main(String[] args) throws Exception{
        SeSsoInfo seSsoInfo = new SeSsoInfo();
        seSsoInfo.setAppId(121111L);
        SeAuthUser authUser = new SeAuthUser();
        authUser.setId(11111L);
        authUser.setName("appleyk");
        seSsoInfo.setUser(authUser);
        SeSsoInfo info = createToken(seSsoInfo);
        String token = info.getClientToken();
        System.out.println(token);
        long uid = verifyToken(token);
        System.out.println(uid);
    }

}
