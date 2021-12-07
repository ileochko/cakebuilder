package ru.sfedu.cakebuilder.models;


import com.opencsv.bean.CsvBindByName;

import java.io.Serializable;
import java.util.Objects;


public class Ingredient implements Serializable {
  @CsvBindByName
  private long id;
  @CsvBindByName
  private String ingredientName;
  @CsvBindByName
  private Integer price;
  @CsvBindByName
  private Integer numberOfIngredient;

  public Ingredient() {
  }

  public Ingredient(long id, String ingredientName, Integer price, Integer numberOfIngredient) {
    this.id = id;
    this.ingredientName = ingredientName;
    this.price = price;
    this.numberOfIngredient = numberOfIngredient;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getIngredientName() {
    return ingredientName;
  }

  public void setIngredientName(String ingredientName) {
    this.ingredientName = ingredientName;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  public Integer getNumberOfIngredient() {
    return numberOfIngredient;
  }

  public void setNumberOfIngredient(Integer numberOfIngredient) {
    this.numberOfIngredient = numberOfIngredient;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Ingredient)) return false;
    Ingredient that = (Ingredient) o;
    return getId() == that.getId() && getIngredientName().equals(that.getIngredientName()) && getPrice().equals(that.getPrice()) && getNumberOfIngredient().equals(that.getNumberOfIngredient());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getIngredientName(), getPrice(), getNumberOfIngredient());
  }

  @Override
  public String toString() {
    return "Ingredient{" +
            "id=" + id +
            ", ingredientName='" + ingredientName + '\'' +
            ", price=" + price +
            ", numberOfIngredient=" + numberOfIngredient +
            '}';
  }
}
