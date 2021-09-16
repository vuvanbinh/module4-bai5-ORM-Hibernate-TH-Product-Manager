package com.codegym.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product {
    @Id
    private int id;
    private String name;
    private int price;
    private String description;
    private String producer;
    private String img;

    public Product() {
    }

    public Product(String name, int price, String description, String producer, String img) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.producer = producer;
        this.img = img;
    }

    public Product(int id, String name, int price, String description, String producer, String img) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.producer = producer;
        this.img = img;
    }

    public Product(int id, String name, int price, String description, String producer) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.producer = producer;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }
}
