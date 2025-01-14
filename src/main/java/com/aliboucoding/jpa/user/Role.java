package com.aliboucoding.jpa.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor

public enum Role {

    USER(Collections.emptySet()),
    ADMIN(
            Set.of(
                    Permission.ADMIN_DELETE,
                    Permission.ADMIN_UPDATE,
                    Permission.ADMIN_CREATE,
                    Permission.ADMIN_READ
            )
    );

@Getter
private final Set<Permission> permissions; // on utilise SET au lieu d'une simple liste pour eviter les doublons !!



    //Cette méthode permet de transformer les rôles et les permissions d’un utilisateur en authorities
    // que Spring Security pourra utiliser pour gérer l’accès aux ressources.
public List<SimpleGrantedAuthority> getAuthorities() {
//Elle retourne une liste d’authorities (permissions et rôle) associées à un rôle (USER ou ADMIN).


    //1. Première étape : récupérer les permissions
    var authorities = getPermissions()
            .stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toList());
//•	Pour chaque permission (ADMIN_READ, ADMIN_UPDATE, …), on crée une authority que Spring Security comprend.
//	•	permission.name() donne le nom de la permission sous forme de texte (ex. : "ADMIN_READ").
//	•	new SimpleGrantedAuthority() transforme cette permission en “authority”.


    authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    //	•	this.name() : Retourne le nom du rôle actuel (par exemple : "ADMIN" ou "USER").
    //	•	"ROLE_" + this.name() :On préfixe le nom du rôle avec "ROLE_" pour que Spring Security
    //	comprenne qu’il s’agit d’un rôle.
    //	•	authorities.add(...) : On ajoute l’autorité du rôle à la liste d’authorities.
    return authorities;
    }

}
