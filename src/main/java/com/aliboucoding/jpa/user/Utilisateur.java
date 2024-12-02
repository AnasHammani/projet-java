package com.aliboucoding.jpa.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity

//UserDetails est une interface fournie par Spring Security. Elle définit un contrat
// (ou un ensemble de méthodes) que les classes représentant des utilisateurs doivent respecter
// pour que Spring Security puisse les gérer efficacement, en particulier dans le cadre de
// l’authentification et de l’autorisation.
public class Utilisateur implements UserDetails {

    @Id

/* Exemple de commentaire : GeneratedValue annotation
    permet de générer automatiquemet le prochain numéro lors de l'ajout
    */
    @GeneratedValue(strategy = GenerationType.IDENTITY)

/* si le nom de la colonne dans la BDD est idPizza alr on peut juste mettre l'annotation @Column
    Si on veut avoir des noms différents alr on rajoute (name="nom_du_champ_ds_bdd)
    */
    @Column(name = "id_utilisateur")
    private Integer id_user;

    @NotNull(message="nom user null !")
    @NotEmpty(message="nom user Empty !")
    @Column(name = "nom_utilisateur")
    private String nom_user;

    @NotNull(message="email user null !")
    @NotEmpty(message="email user Empty !")
    @Column(name = "email_utilisateur",unique=true)
    private String email;

    @NotNull(message="password user null !")
    @NotEmpty(message="password user Empty !")
    @Column(name = "mdp_utilisateur")
    private String password;

    @NotNull(message="phone number user null !")
    @NotEmpty(message="phone number user Empty !")
    @Column(name = "gsm_utilisateur",unique=true)
    private String gsm_user;

    @NotNull(message="adress user null !")
    @NotEmpty(message="adress user Empty !")
    @Column(name = "adresse_utilisateur")
    private String adresse_user;

    //@NotNull(message="role user null !")
    //@NotEmpty(message="role user Empty !")
    @Column(name = "role_utilisateur")
    @Enumerated(EnumType.STRING)
    private Role role;


    @Override //fournit les rôles (ou autorités) de l’utilisateur
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override  //Indique si le compte de l’utilisateur est expiré.
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override //Indique si le compte de l’utilisateur est verrouillé.
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override //Indique si les informations d’identification (mot de passe) de l’utilisateur ont expiré.
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override //Indique si l’utilisateur est activé
    public boolean isEnabled() {
        return true;
    }
}

