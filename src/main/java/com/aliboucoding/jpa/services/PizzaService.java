package com.aliboucoding.jpa.services;

import com.aliboucoding.jpa.Validator.PizzaValidator;
import com.aliboucoding.jpa.models.Pizza;
import com.aliboucoding.jpa.repositories.PizzaRepository;

import lombok.AllArgsConstructor;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class PizzaService {

    private final PizzaRepository pizzaRepository;

    private final PizzaValidator pizzaValidator;


    public List<Pizza> getAllPizzas() {
        return pizzaRepository.findAll();
    }

    public Pizza getPizzaById(Integer id) {
        return pizzaRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Pizza ID not found"));

    }

    public String savePizza(Pizza pizza) {

        var violations = pizzaValidator.validate(pizza);

        if (!violations.isEmpty()) {
            return String.join("\n", violations);
        }

            pizzaRepository.save(pizza);

        return "pizza added successfully";
    }

    public String updatePizza(Pizza pizza) {

        var violations = pizzaValidator.validate(pizza);
        if (!violations.isEmpty()) {
            return String.join("\n", violations);
        }

        Pizza existingPizza = pizzaRepository.findById(pizza.getId_pizza())
                .orElseThrow(()->new IllegalArgumentException("Pizza not found"));

        // Mettre à jour les champs
        existingPizza.setNom_pizza(pizza.getNom_pizza());
        existingPizza.setPrix_pizza(pizza.getPrix_pizza());
        existingPizza.setDescription_pizza(pizza.getDescription_pizza());
        existingPizza.setTaille_pizza(pizza.getTaille_pizza());
        existingPizza.setTemps_preparation(pizza.getTemps_preparation());
        existingPizza.setListe_ingredient(pizza.getListe_ingredient());

        // Sauvegarde dans la base de données
        pizzaRepository.save(existingPizza);

        return "pizza updated successfully";

    }

}
