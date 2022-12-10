package com.example.aplicacion_cine.models;

public class Post {
    private String id;
    private String tittle;
    private String description;
    private String image1;
    private String image2;
    private String category;

    public Post() {
    }

    public Post(String id, String tittle, String description, String image1, String image2, String category) {
        this.id = id;
        this.tittle = tittle;
        this.description = description;
        this.image1 = image1;
        this.image2 = image2;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
