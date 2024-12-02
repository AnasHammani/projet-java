package com.aliboucoding.jpa.config;

import com.aliboucoding.jpa.user.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
// OBJECTIF DE CETTE CLASSE :
//	1.	Trouver les informations d’un utilisateur dans la base de données.
//	2.	Charger ses données (comme l’email, les rôles) pour l’authentification.
public class ApplicationConfig {

    private final UtilisateurRepository repository;
    //	•	Cet attribut permet d’accéder à la base de données pour rechercher les utilisateurs.
    //	•	UtilisateurRepository est l’interface qui gère les requêtes à la base de données pour les entités Utilisateur.

    @Bean
    //Cette méthode configure un bean UserDetailsService qui interroge la base de données via UtilisateurRepository
    // pour trouver l’utilisateur en fonction de son email.
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


    @Bean
    public AuthenticationProvider authenticationProvider() {
        //•	DaoAuthenticationProvider est configuré pour utiliser UserDetailsService (c’est-à-dire la méthode
        // que tu as définie précédemment (ci-dessus) pour charger l’utilisateur depuis la base de données).
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        // • Ce provider est chargé de la logique d’authentification : il vérifie si l’utilisateur existe
        // (via UserDetailsService) et valide son mot de passe.
        // • Le DaoAuthenticationProvider utilise un PasswordEncoder pour comparer le mot de passe saisi avec celui
        //	stocké dans la base de données de manière sécurisée.
        return authProvider;
    }

    @Bean
    //AuthenticationManager est un composant de Spring Security qui gère l’authentification des utilisateurs.
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
        //Cette méthode crée un bean AuthenticationManager qui sera utilisé pour valider les informations d’identification
        // (email et mot de passe) lors de l’authentification.
    }

    @Bean
    // PasswordEncoder est utilisé pour encoder et vérifier les mots de passe de manière sécurisée.
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
        //Ici, BCryptPasswordEncoder est utilisé, un des algorithmes les plus sûrs pour gérer les mots de passe.
        // Il permet de comparer les mdp de manière sécurisée en les “hashant” (cad en les transformant de manière irréversible).
    }


}
