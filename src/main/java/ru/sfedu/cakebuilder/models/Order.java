package ru.sfedu.cakebuilder.models;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import ru.sfedu.cakebuilder.utils.CakeListConverter;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Order implements Serializable {
    @CsvBindByName
    private long id;
    @CsvBindByName
    private String clientName;
    @CsvBindByName
    private String orderComment;
    @CsvCustomBindByName(converter = CakeListConverter.class)
    private List<Cake> cakes;

    public Order() {
    }

    public Order(long id, String clientName, String orderComment, List<Cake> cakes) {
        this.id = id;
        this.clientName = clientName;
        this.orderComment = orderComment;
        this.cakes = cakes;
    }

    public Order(long id, String clientName, List<Cake> cakes) {
        this.id = id;
        this.clientName = clientName;
        this.cakes = cakes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getOrderComment() {
        return orderComment;
    }

    public void setOrderComment(String orderComment) {
        this.orderComment = orderComment;
    }

    public List<Cake> getCakes() {
        return cakes;
    }

    public void setCakes(List<Cake> cakes) {
        this.cakes = cakes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return getId() == order.getId() && getClientName().equals(order.getClientName()) && getOrderComment().equals(order.getOrderComment()) && getCakes().equals(order.getCakes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getClientName(), getOrderComment(), getCakes());
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", clientName='" + clientName + '\'' +
                ", orderComment='" + orderComment + '\'' +
                ", cakes=" + cakes +
                '}';
    }
}
