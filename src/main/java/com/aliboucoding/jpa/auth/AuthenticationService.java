package com.aliboucoding.jpa.auth;

import com.aliboucoding.jpa.config.JwtService;
import com.aliboucoding.jpa.user.Role;
import com.aliboucoding.jpa.user.Utilisateur;
import com.aliboucoding.jpa.user.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UtilisateurRepository repository;
    private final PasswordEncoder passwordEncoder; //Sert à chiffrer les mots de passe avant de les sauvegarder
    // dans la base de données, pour des raisons de sécurité.
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager; //Vérifie les identifiants (email et mot de passe)
    // lors de la connexion en utilisant Spring Security.( .authenticate() )

    public AuthenticationResponse register(RegisterRequest request) {

        var user= Utilisateur.builder()
                .nom_user(request.getNom_user()) // request.get ça sert à recuperer les infos envoyées par le client
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) //chiffrer le mdp
                .gsm_user(request.getGsm_user())
                .adresse_user(request.getAdresse_user())
                .role(Role.USER)
                .build();

        repository.save(user);

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        //•	Utilisation de authenticationManager.authenticate(...) pour valider les infos fournies par l’utilisateur.
        //•	Si les informations sont incorrectes, une exception est levée.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        //En créant cet objet avec l’email et le mot de passe de l’utilisateur, nous préparons les informations
        // nécessaires pour que authenticationManager puisse les vérifier.



        var user = repository.findByEmail(request.getEmail()).orElseThrow();

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();


    }


}
