package com.aliboucoding.jpa.config;

import com.aliboucoding.jpa.user.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
// OBJECTIF DE CETTE CLASSE :
//	1.	Trouver les informations d’un utilisateur dans la base de données.
//	2.	Charger ses données (comme l’email, les rôles) pour l’authentification.
public class ApplicationConfig {

    private final UtilisateurRepository repository;
    //	•	Cet attribut permet d’accéder à la base de données pour rechercher les utilisateurs.
    //	•	UtilisateurRepository est l’interface qui gère les requêtes à la base de données pour les entités Utilisateur.

    @Bean //@Bean : Indique que cette méthode retourne un “bean” Spring, cad un composant qui sera automatiquement géré par Spring.
    public UserDetailsService userDetailsService() {
        //•	Un UserDetailsService est une interface utilisée par Spring Security pour charger un utilisateur
        // en fonction de son nom d’utilisateur (dans ce cas, son email).
        return username -> repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
//	•	username -> : La lambda prend en entrée un username (l’email ici).
//	•	repository.findByEmail(username) : Requête dans la base de données pour trouver un utilisateur dont l’email correspond à username.
//	•	findByEmail(username) : Méthode de votre UtilisateurRepository.
//	•	orElseThrow(... : Si aucun utilisateur n’est trouvé, une exception UsernameNotFoundException est levée.
//	•	Cela signale à Spring Security que cet utilisateur n’existe pas.
}
