package com.aliboucoding.jpa.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@Entity

public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pizza")
    private Integer id_pizza;


    @NotEmpty(message="nom vide !")
    @Size(min=3, message = "Le nom de la pizza doit avoir au moins 3 caractères")
    @Column(name = "nom_pizza", unique = true,nullable = false)
    private String nom_pizza;

    @NotNull(message="prix null !")
    @Min(value=0,message="le prix doit etre superieur à 0 !")
    @Column(name = "prix_pizza")
    private Double prix_pizza;

    @Size(min=10,message="La description doit contenir au moins 10 caractères !")
    @Column(name="description_pizza")
    private String description_pizza;

    @Pattern(
            regexp = "small|medium|large",
            message = "La taille doit être 'small', 'medium' ou 'large' !"
    )
    @NotEmpty(message="taille vide !")
    @Column(name="taille_pizza")
    private String taille_pizza;


    @Min(value=0,message="le temps de preparation doit etre superieur à 0 !")
    @Column(name="temps_preparation")
    private Integer temps_preparation;



    @NotEmpty(message="liste ingredient vide !")
    @Column(name="liste_ingredient")
    private String liste_ingredient;



}
