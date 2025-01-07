package com.aliboucoding.jpa.config;

import io.jsonwebtoken.Claims; //Claims : représente le contenu du JWT, comme l’email encodé.
import io.jsonwebtoken.Jwts; //classe utilitaire pour travailler avec JWTs (par exemple, pour générer/analyser les tokens).
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders; //Decoders: classe utilitaire qui aide à décoder des données encodées en base64.
import io.jsonwebtoken.security.Keys; //Keys : une classe utilitaire pour créer des clés de signature.
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service; //annotation Spring indiquant que c est un service qui gère la logique métier.

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {


    private  final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);


    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(),userDetails);
    }


    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

    }


    private Key getSignInKey() {

        return this.SECRET_KEY;

    }



    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);


    }


    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {

        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);

    }



    private Claims extractAllClaims(String token) {

        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }



    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = this.extractUsername(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

}
