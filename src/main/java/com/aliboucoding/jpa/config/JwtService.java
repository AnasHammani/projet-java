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


//CETTE CLASSE PERMET DE :
//	1.	générer un token JWT pour un utilisateur
//	2.	Extraire des informations (comme l’email ou l’expiration) du token.
//	3.	verifier la validité d'un token.
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


    //Le But : Récupérer le “sujet” du token, souvent l’email ou le nom d’utilisateur.
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
        //	Elle appelle extractClaim, qui permet de lire n’importe quelle information du token.
        //	Le Claims::getSubject indique qu’on souhaite récupérer le “sujet” du token.

    }

    //Le But : Lire une donnée précise dans le token (par exemple l’email).
    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {

        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
        //	Elle appelle extractAllClaims pour décoder tout le contenu du token.
        // Ensuite, elle utilise une “fonction” (claimsResolver) pour récupérer uniquement ce qui nous intéresse.
    }


    // Le But : Décoder tout le contenu du token JWT (c’est comme ouvrir une boîte pour lire tout ce qu’elle contient).
    private Claims extractAllClaims(String token) {

        return Jwts
                .parserBuilder()  // Crée un "parseur" (ou décodeur) pour le token JWT
                .setSigningKey(getSignInKey())// On lui dit avec quelle clé il doit déchiffrer le message
                .build()// Construction du parseur
                .parseClaimsJws(token)// Ici, on analyse et verifie le token
                .getBody();// On obtient les "revendications" (les infos à l'intérieur du token)
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
