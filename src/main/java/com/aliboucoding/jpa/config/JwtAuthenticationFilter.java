package com.aliboucoding.jpa.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
//CETTE CLASSE PERMET DE :
//	1.	Vérifier si une requête contient un token JWT valide.
//	•	Elle regarde le header Authorization de la requête pour trouver le token.
//	2.	Extraire l’email (ou l’identifiant utilisateur) du token.
//	•	Une fois le token trouvé, elle utilise JwtService pour lire son contenu.
//	3.	Configurer la sécurité de la requête.
//	•	Si le token est valide, elle attache l’utilisateur associé au SecurityContext de Spring,
//	permettant ainsi de sécuriser l’accès aux ressources.

@Component
@RequiredArgsConstructor

//OncePerRequestFilter est une classe de Spring qui garantit qu’un filtre (comme ici) s’exécute
// une seule fois par requête, même si la requête passe par plusieurs couches.
//Les filtres sont des composants qui permettent de traiter les requêtes HTTP
// avant qu’elles atteignent les contrôleurs.

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    //Cette méthode est automatiquement appelée par Spring chaque fois qu’une requête HTTP arrive.
    // C’est ici que j ajoute la logique (comme vérifier un JWT token pour sécuriser l’accès).
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        //Le header Authorization est souvent utilisé pour transporter des informations d’authentification
        //	Ce header contient généralement un token JWT sous la forme :
        //	Authorization: Bearer <le-token-JWT>

        final String jwt;
        final String userEmail;

        //traitement dans le cas ou JWT token n est pas valide alors :
        // quand le header n est pas valide soit il est null soit ne commence pas par bearer !
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {

           filterChain.doFilter(request, response);
           //	La requête est envoyée au filtre suivant sans traitement supplémentaire.
           return;
           // return : Arrête l’exécution de cette méthode car il n’y a rien d’autre à faire.
        }

        //Si le traitement est valide alors on recupere/extrait le jwt token en 7eme position
        // apres B.E.A.R.E.R
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt) ; // todo extract the userEMail from JWT Token


        //userEmail != null : Vérifie qu’un email a bien été extrait du token.
        // && : Vérifie qu’aucun utilisateur n’est déjà authentifié pour cette requête.
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            //Cette ligne utilise userDetailsService pour récupérer les informations de l’utilisateur
            // (comme son rôle ou ses permissions) à partir de l’email.
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if(jwtService.isTokenValid(jwt,userDetails)){ // CHECK que :
                //Le token n’a pas expiré. ET QUE•	Le token correspond bien à l’utilisateur chargé (userDetails).

                //Si le token est valide, on passe à l’étape suivante pour authentifier cet utilisateur.
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        //	UsernamePasswordAuthenticationToken : Représente une authentification réussie.
                        userDetails, //Identité de l’utilisateur.
                        null, //Mot de passe (pas nécessaire ici car on utilise un token JWT).
                        userDetails.getAuthorities() //Permissions ou rôles associés à cet utilisateur.
                );
                authToken.setDetails( //Lie l’authentification à la requête HTTP en cours.
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
                //Enregistre cet utilisateur comme étant authentifié pour cette requête.

            }
        }
        filterChain.doFilter(request, response);
        //	Cette ligne permet de continuer le traitement de la requête, en envoyant les informations
        //	d’authentification à l’étape suivante.


    }
}


//Résumé des étapes après jwt = authHeader.substring(7);
//
//  1.	On vérifie que le token est valide.
//  2.  si il est valide , on le recupere en 7eme position
//	2.	On extrait l’email du token. ( avec extract de JwtService)
//	3.	Si l’utilisateur n’est pas déjà authentifié, on charge ses informations.
//	4.	Si oui, on crée une authentification pour cet utilisateur.
//	5.	On enregistre cette authentification dans le SecurityContext.
//	6.	La requête continue son traitement normalement.