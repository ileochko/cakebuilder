package ru.sfedu.cakebuilder.models;


import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import ru.sfedu.cakebuilder.models.enums.Cream;
import ru.sfedu.cakebuilder.models.enums.Filling;
import ru.sfedu.cakebuilder.models.enums.SpongeCake;
import ru.sfedu.cakebuilder.utils.IngredientListConverter;

import java.util.List;
import java.util.Objects;


public class CustomCake extends Cake {
    @CsvBindByName
    private Cream cream;
    @CsvBindByName
    private SpongeCake spongeCake;
    @CsvBindByName
    private Filling filling;
    @CsvBindByName
    private String timeToCreate;
    @CsvCustomBindByName(converter = IngredientListConverter.class)
    private List<Ingredient> ingredients;

    public CustomCake() {
    }

    public CustomCake(long id, Integer price, Integer layer, Integer weight, Boolean needDecorate, Cream cream, SpongeCake spongeCake, Filling filling, String timeToCreate, List<Ingredient> ingredients) {
        super(id, price, layer, weight, needDecorate);
        this.cream = cream;
        this.spongeCake = spongeCake;
        this.filling = filling;
        this.timeToCreate = timeToCreate;
        this.ingredients = ingredients;
    }

    public Cream getCream() {
        return cream;
    }

    public void setCream(Cream cream) {
        this.cream = cream;
    }

    public SpongeCake getSpongeCake() {
        return spongeCake;
    }

    public void setSpongeCake(SpongeCake spongeCake) {
        this.spongeCake = spongeCake;
    }

    public Filling getFilling() {
        return filling;
    }

    public void setFilling(Filling filling) {
        this.filling = filling;
    }

    public String getTimeToCreate() {
        return timeToCreate;
    }

    public void setTimeToCreate(String timeToCreate) {
        this.timeToCreate = timeToCreate;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomCake)) return false;
        if (!super.equals(o)) return false;
        CustomCake that = (CustomCake) o;
        return getCream() == that.getCream() && getSpongeCake() == that.getSpongeCake() && getFilling() == that.getFilling() && getTimeToCreate().equals(that.getTimeToCreate()) && getIngredients().equals(that.getIngredients());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCream(), getSpongeCake(), getFilling(), getTimeToCreate(), getIngredients());
    }

    @Override
    public String toString() {
        return "CustomCake{" +
                "cream=" + cream +
                ", spongeCake=" + spongeCake +
                ", filling=" + filling +
                ", timeToCreate='" + timeToCreate + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }
}
