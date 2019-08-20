package com.example.lenovo.pfa_project.Models;

public class Plats {
    private String id;
    private String idRestaurant;
    private String intitule;
    private String prix;



    public Plats(String intitule, String prix) {
        //this.id = id;
        //this.idRestaurant = idRestaurant;
        this.intitule = intitule;
        this.prix = prix;
    }

    public String getId() {
        return id;
    }

    public String getIdRestaurant() {
        return idRestaurant;
    }

    public String getIntitule() {
        return intitule;
    }

    public String getPrix() {
        return prix;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIdRestaurant(String idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }
}
