package com.codegym.model;

import org.springframework.web.multipart.MultipartFile;

public class ProductForm {
    private int id;
    private String name;
    private int price;
    private String description;
    private String producer;
    private MultipartFile img;

    public ProductForm() {
    }

    public ProductForm(int id, String name, int price, String description, String producer, MultipartFile img) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.producer = producer;
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

    public MultipartFile getImg() {
        return img;
    }

    public void setImg(MultipartFile img) {
        this.img = img;
    }
}
