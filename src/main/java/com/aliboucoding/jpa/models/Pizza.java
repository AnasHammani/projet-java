package com.aliboucoding.jpa.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data //pour les setters et getters
@NoArgsConstructor // constructeur sans argument
@Entity //mapper une classe à une table de base de données

public class Pizza {

    @Id
    /* Exemple de commentaire : GeneratedValue annotation
    permet de générer automatiquemet le prochain numéro lors de l'ajout
    */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /* si le nom de la colonne dans la BDD est idPizza alr on peut juste mettre l'annoation @Column
    Si on veut avoir des noms différents alr on rajoute (name="nom_du_champ_ds_bdd)
    */
    @Column(name = "id_pizza")
    private Integer id_pizza;


    @NotEmpty(message="nom vide ou null !")
    @Column(name = "nom_pizza", unique = true,nullable = false)
    private String nom_pizza;

    @NotNull(message="prix null !")
    @Min(value=0,message="le prix doit etre superieur à 0 !")
    @Column(name = "prix_pizza")
    private double prix_pizza;

    @NotNull(message="description null !")
    @NotEmpty(message="description vide !")
    @Column(name="description_pizza")
    private String description_pizza;

    @NotNull(message="taille null !")
    @NotEmpty(message="taille vide !")
    @Column(name="taille_pizza")
    private String taille_pizza;


    @NotNull(message="temps preparation null !")
    @Min(value=0,message="le temps de preparation doit etre superieur à 0 !")
    @Column(name="temps_preparation")
    private Integer temps_preparation;


    @NotNull(message="liste ingredient null !")
    @NotEmpty(message="liste ingredient vide !")
    @Column(name="liste_ingredient")
    private String liste_ingredient;



}
