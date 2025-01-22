package com.aliboucoding.jpa.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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


public class Utilisateur implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utilisateur")
    private Integer id_user;


    @NotEmpty(message="nom user Empty !")
    @Column(name = "nom_utilisateur")
    private String nom_user;


    @NotEmpty(message="email user Empty !")
    @Email(message="Invalid email format !")
    @Column(name = "email_utilisateur",unique=true)
    private String email;


    @NotEmpty(message="password user Empty !")
    @Column(name = "mdp_utilisateur")
    private String password;


    @NotEmpty(message="phone number user Empty !")
    @Pattern(regexp = "//d+",message = "Gsm must contain only numbers ! ")
    @Column(name = "gsm_utilisateur",unique=true)
    private String gsm_user;


    @NotEmpty(message="adress user Empty !")
    @Column(name = "adresse_utilisateur")
    private String adresse_user;


    @Column(name = "role_utilisateur")
    @Enumerated(EnumType.STRING)
    private Role role;


    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();

    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

