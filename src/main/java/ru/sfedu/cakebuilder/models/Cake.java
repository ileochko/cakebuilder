package ru.sfedu.cakebuilder.models;


import com.opencsv.bean.CsvBindByName;

import java.io.Serializable;
import java.util.Objects;

public class Cake implements Serializable {
    @CsvBindByName
    private long id;
    @CsvBindByName
    private Integer price;
    @CsvBindByName
    private Integer layer;
    @CsvBindByName
    private Integer weight;
    @CsvBindByName
    private Boolean needDecorate;

    public Cake() {
    }

    public Cake(long id, Integer price, Integer layer, Integer weight, Boolean needDecorate) {
        this.id = id;
        this.price = price;
        this.layer = layer;
        this.weight = weight;
        this.needDecorate = needDecorate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getLayer() {
        return layer;
    }

    public void setLayer(Integer layer) {
        this.layer = layer;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Boolean getNeedDecorate() {
        return needDecorate;
    }

    public void setNeedDecorate(Boolean needDecorate) {
        this.needDecorate = needDecorate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cake)) return false;
        Cake cake = (Cake) o;
        return getId() == cake.getId() && getPrice().equals(cake.getPrice()) && getLayer().equals(cake.getLayer()) && getWeight().equals(cake.getWeight()) && getNeedDecorate().equals(cake.getNeedDecorate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPrice(), getLayer(), getWeight(), getNeedDecorate());
    }

    @Override
    public String toString() {
        return "Cake{" +
                "id=" + id +
                ", price=" + price +
                ", layer=" + layer +
                ", weight=" + weight +
                ", needDecorate=" + needDecorate +
                '}';
    }
}
