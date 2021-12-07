package ru.sfedu.cakebuilder.models;


import com.opencsv.bean.CsvBindByName;
import ru.sfedu.cakebuilder.models.enums.CommonCakes;

import java.util.Objects;

public class SpecialCake extends Cake {
    @CsvBindByName
    private CommonCakes basicCake;

    public SpecialCake() {
    }

    public SpecialCake(long id, Integer price, Integer layer, Integer weight, Boolean needDecorate, CommonCakes basicCake) {
        super(id, price, layer, weight, needDecorate);
        this.basicCake = basicCake;
    }

    public CommonCakes getBasicCake() {
        return basicCake;
    }

    public void setBasicCake(CommonCakes basicCake) {
        this.basicCake = basicCake;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpecialCake)) return false;
        if (!super.equals(o)) return false;
        SpecialCake that = (SpecialCake) o;
        return getBasicCake() == that.getBasicCake();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getBasicCake());
    }

    @Override
    public String toString() {
        return "SpecialCake{" +
                "basicCake=" + basicCake +
                '}';
    }
}
