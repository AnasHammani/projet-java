package com.aliboucoding.jpa.services;

import com.aliboucoding.jpa.models.Pizza;
import com.aliboucoding.jpa.repositories.PizzaRepository;

import lombok.AllArgsConstructor;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service


/*Je l'ai rajouté moi, ds la vidéo il a du en parler.
ça permet d'éviter de créer sois meme un constructeur et initialiser les attributs
*/
@AllArgsConstructor
public class PizzaService {

    private final PizzaRepository pizzaRepository;

//    public PizzaService(PizzaRepository pizzaRepository) {
//        this.pizzaRepository = pizzaRepository;
//    }

    public List<Pizza> getAllPizzas() {
        return pizzaRepository.findAll(); // Utilise le repository pour obtenir la liste des pizzas
    }

    public Pizza getPizzaById(Integer id) {
        return pizzaRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Pizza ID not found"));

    }

    public void savePizza(Pizza pizza) {
        // Valider l'objet pizza avant de le sauvegarder
        //if(pizzaRepository.existsByNom_pizza(pizza.getNom_pizza())){

            //throw new IllegalArgumentException("Pizza already exists");
        //}

        try {
            pizzaRepository.save(pizza);
        }catch(DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Pizza already exists in the database");
        }


    }

}
