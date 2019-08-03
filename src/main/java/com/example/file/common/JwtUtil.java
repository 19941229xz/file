package com.example.file.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JwtUtil {


    private static final long expire_time = 5 * 60 * 1000; //五分钟内有效

    //通过邮箱 邮箱内容 和后台私钥 生成emailToken
    public static String createEmailToken(String email, String content, String privateKey,String title) {
        //过期时间
        Date date = new Date(System.currentTimeMillis() + expire_time);
        Algorithm algorithm = Algorithm.HMAC256(privateKey);
        return JWT.create().withClaim("email", email).withClaim("title",title)
                .withClaim("content", content)
                .withExpiresAt(date).sign(algorithm);
    }

    //解码emailToken 获取邮箱地址
    public static String getEmailfromEmailToken(String emailToken){
        try {
            DecodedJWT jwt = JWT.decode(emailToken);
            return jwt.getClaim("email").asString();
        } catch (Exception e) {
            return null;
        }
    }
    //解码emailToken 获取邮件标题
    public static String getTitlefromEmailToken(String emailToken){
        try {
            DecodedJWT jwt = JWT.decode(emailToken);
            return jwt.getClaim("title").asString();
        } catch (Exception e) {
            return null;
        }
    }


    //解码emailToken 获取发送内容
    public static String getContentfromEmailToken(String emailToken){
        try {
            DecodedJWT jwt = JWT.decode(emailToken);
            return jwt.getClaim("content").asString();
        } catch (Exception e) {
            return null;
        }
    }

    // 校验方法
    public static boolean verify(String token, String username, String secret) {

        try {
            //校验器
            Algorithm algorithm = Algorithm.HMAC256(secret);
            //吧校验器加载到验证器
            JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username)
                    .build();


            DecodedJWT verify = verifier.verify(token);
            return true;
        } catch (Exception e) {
            log.debug("ex happen");
            return false;
        }


    }

    //签名方法
    public static String sign(String username, String secret) {
        //过期时间
        Date date = new Date(System.currentTimeMillis() + expire_time);

        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create().withClaim("username", username)
                .withExpiresAt(date).sign(algorithm);


    }


    public static String getUsername(String token) {

        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (Exception e) {
            return null;
        }


    }


}
