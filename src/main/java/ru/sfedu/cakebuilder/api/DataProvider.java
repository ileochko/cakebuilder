package ru.sfedu.cakebuilder.api;

import ru.sfedu.cakebuilder.Answer;
import ru.sfedu.cakebuilder.models.*;

import java.util.List;
import java.util.Optional;

public interface DataProvider {
//Основные методы
    Answer<Integer> getIngredientPrice(String typeOfPrice);

    Answer<List<Ingredient>> getDetailedCheck();

    Answer<Integer> getProfit();

    Answer<Integer> summariseAllProfit();

    Answer<Integer> calculateCustomCakeCost(Cake cake) ;

    Answer<Integer> specialOrder(SpecialCake cake);

    Answer<Integer> getChiefTime(Boolean isNeedDecorate);

    Answer<Integer> getIngredientCost(CustomCake cake);
// Для конвертера
    <T extends Cake> Answer<Cake> getCakes(long id);
    Answer<Ingredient> getIngredients(long parseLong);
//CRUD

    Answer<Order> insertOrder(Order order);
    Answer<Void> updateOrder(Order order);
    Optional<Order> searchOrderById(long id);
    Answer<Void> deleteOrder(long id);

    Answer<Ingredient> insertIngredient(Ingredient ingredient);
    Answer<Void> updateIngredient(Ingredient ingredient);
    Optional<Ingredient> searchIngredientById(long id);
    Answer<Void> deleteIngredient(long id);

    Answer<Cake> insertCake(Cake element);
    Answer<Void> updateCake(Cake updElement);
    Optional<Cake> getCakeByID(long id);
    Answer<Void> deleteCake( long id);

    Answer<CustomCake> insertCustomCake(CustomCake element);
    Answer<SpecialCake> insertSpecialCake(SpecialCake element);

    Answer<Void> updateCustomCake(CustomCake updElement);
    Answer<Void> updateSpecialCake(SpecialCake updElement);

    Optional<CustomCake> getCustomCakeByID(long id);
    Optional<SpecialCake> getSpecialCakeByID(long id);
    Answer<Void> deleteCustomCake( long id);
    Answer<Void> deleteSpecialCake( long id);
}
