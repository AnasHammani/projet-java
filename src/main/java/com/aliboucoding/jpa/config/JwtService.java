package com.aliboucoding.jpa.config;

import io.jsonwebtoken.Claims; //Claims : représente le contenu du JWT, comme l’email encodé.
import io.jsonwebtoken.Jwts; //classe utilitaire pour travailler avec JWTs (par exemple, pour générer/analyser les tokens).
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders; //Decoders: classe utilitaire qui aide à décoder des données encodées en base64.
import io.jsonwebtoken.security.Keys; //Keys : une classe utilitaire pour créer des clés de signature.
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service; //annotation Spring indiquant que c est un service qui gère la logique métier.

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

    //C’est la clé secrète utilisée pour signer le JWT.
    private static final String SECRET_KEY = "6a324c39396d474d3777424b66793636323571424b6f3466504e4d5563644151";


//	•	But : Offrir une version simplifiée de la génération de token, où tu n’as pas besoin de fournir de données `
//  	supplémentaires (les “claims”).
//	•	Utilisation typique : Quand tu veux simplement générer un token pour un utilisateur,
//  	sans ajouter d’informations personnalisées.

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(),userDetails);
    }


//  •	But : Offrir une version avancée où tu peux ajouter des “claims” supplémentaires
//     (des données spécifiques) au token.
//	•	Utilisation typique : Si tu veux inclure des informations personnalisées
//   	(comme un rôle ou une autre propriété utilisateur) dans le token.

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.ES256)
                .compact();

    }

    //Décode la clé secrète (en base64) pour la transformer en une clé utilisable par l’algorithme de signature HMAC-SHA.
    private Key getSignInKey() {

        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY); //decode secret_key en tableau d octets.
        return Keys.hmacShaKeyFor(keyBytes);
        // Utilise le tableau d’octets comme clé pour générer une clé HMAC-SHA
        // (un algorithme de signature utilisé pour valider les JWT).
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
                .parseClaimsJws(token)// Ici, on déchiffre le token
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


//  L’enchaînement des méthodes de CETTE classe :

//	•	Lire les données d’un token (comme ici avec extractClaim).
//	•	Vérifier la validité (comme ici avec isTokenValid).
//	•	Générer un token (comme ici avec generateToken).



//                  Comment retenir tout ça ?
//
// Penser à cette classe en trois blocs :
//	1.	Créer un token → Méthodes generateToken.
//	2.	Lire un token → Méthodes extractClaim, extractUsername.
//	3.	Vérifier un token → Méthodes isTokenValid, isTokenExpired.