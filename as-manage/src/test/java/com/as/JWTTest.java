package com.as;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;

public class JWTTest {
	public static void main(String[] args) throws IllegalArgumentException, UnsupportedEncodingException {
		Builder cr = JWT.create();
		String secret="xxssdd";
		
		cr.withClaim("id","1");
		cr.withClaim("lat","22");
		
		Algorithm algorithm=Algorithm.HMAC256(secret);
		String sign = cr.sign(algorithm);
		
		DecodedJWT de = JWT.decode("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjEiLCJsYXQiOiIyMiJ9"
				+ ".1gAd-yIEtuJBHowFZwh5Pxey4zvZ59ckoH7O0C4Fr3M");
		Claim id = de.getClaim("id");
		algorithm.verify(de);
		System.out.println(sign);
		

//		v.withClaim(name, value);
	}
}
