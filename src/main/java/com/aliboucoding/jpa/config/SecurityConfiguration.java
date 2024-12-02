package com.aliboucoding.jpa.config;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationProvider;

@Configuration
@EnableWebSecurity //Cette annotation active la configuration de sécurité Web de Spring Security.
// Elle permet à Spring de gérer la sécurité pour ton application web,
// en utilisant les classes et configurations définies dans SecurityConfiguration.
@RequiredArgsConstructor

//cette classe sert à configurer le comportement de sécurité global de ton application.
//C’est grâce à cette configuration que ton filtre JWT est effectivement appliqué à chaque requête client.
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider ;
    //	AuthenticationProvider est une interface fournie par Spring Security.
    // Ce “provider” gère la logique d’authentification, c’est-à-dire :
    //	1.	Vérifier si l’utilisateur existe.
    //	2.	Comparer son mot de passe avec celui stocké.

    @Bean
    //La méthode securityFilterChain configure les règles de sécurité de ton application.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }


}
