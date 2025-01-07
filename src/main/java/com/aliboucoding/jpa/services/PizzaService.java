package com.aliboucoding.jpa.services;

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


    public List<Pizza> getAllPizzas() {
        return pizzaRepository.findAll();
    }

    public Pizza getPizzaById(Integer id) {
        return pizzaRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Pizza ID not found"));

    }

    public void savePizza(Pizza pizza) {

        try {
            pizzaRepository.save(pizza);
        }catch(DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Pizza already exists in the database");
        }

    }

}
