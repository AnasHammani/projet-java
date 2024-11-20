package com.aliboucoding.jpa.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
public class Utilisateur {

    @Id

/* Exemple de commentaire : GeneratedValue annotation
    permet de générer automatiquemet le prochain numéro lors de l'ajout
    */
    @GeneratedValue(strategy = GenerationType.IDENTITY)

/* si le nom de la colonne dans la BDD est idPizza alr on peut juste mettre l'annoation @Column
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
    private String email_user;

    @NotNull(message="password user null !")
    @NotEmpty(message="password user Empty !")
    @Column(name = "mdp_utilisateur")
    private String password_user;

    @NotNull(message="phone number user null !")
    @NotEmpty(message="phone number user Empty !")
    @Column(name = "gsm_utilisateur",unique=true)
    private String gsm_user;

    @NotNull(message="adress user null !")
    @NotEmpty(message="adress user Empty !")
    @Column(name = "adresse_utilisateur")
    private String adresse_user;

    @NotNull(message="role user null !")
    @NotEmpty(message="role user Empty !")
    @Column(name = "role_utilisateur")
    private String role_user;

}

