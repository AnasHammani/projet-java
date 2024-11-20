package com.aliboucoding.jpa.repositories;

import com.aliboucoding.jpa.models.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza,Integer> {

    //boolean existsByNom_pizza(String nom_pizza);
    // Méthode pour vérifier si une pizza avec ce nom existe

}
